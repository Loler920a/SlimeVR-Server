package dev.slimevr.gui;

import com.jme3.math.FastMath;
import dev.slimevr.VRServer;
import dev.slimevr.gui.swing.EJBagNoStretch;
import dev.slimevr.vr.trackers.TrackerFilteringTypes;
import dev.slimevr.vr.trackers.TrackerFilteringValues;
import io.eiren.util.StringUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


public class TrackersFiltersGUI extends EJBagNoStretch {

	private final VRServer server;
	private final JLabel amountLabel;
	private final JLabel ticksLabel;
	TrackerFilteringTypes filterType;
	int filterAmount;
	int filterTicks;

	public TrackersFiltersGUI(VRServer server, VRServerGUI gui) {

		super(false, true);
		this.server = server;

		int row = 0;

		setAlignmentY(TOP_ALIGNMENT);
		add(Box.createVerticalStrut(10));
		filterType = TrackerFilteringTypes
			.getByStringValue(
				server.config
					.getString(
						TrackerFilteringTypes.CONFIG_KEY,
						TrackerFilteringTypes.NONE.configVal
					)
			);

		JComboBox<String> filterSelect;
		add(filterSelect = new JComboBox<>(), s(c(0, row, 2), 4, 1));

		for (TrackerFilteringTypes f : TrackerFilteringTypes.values()) {
			filterSelect.addItem(f.name());
		}
		filterSelect.setSelectedItem(filterType.toString());

		filterSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = TrackerFilteringTypes
					.valueOf(filterSelect.getSelectedItem().toString());
				server.updateTrackersFilters(filterType, filterAmount, filterTicks);
			}
		});
		add(Box.createVerticalStrut(40));
		row++;

		filterAmount = (int) FastMath
			.clamp(
				server.config
					.getInt(
						TrackerFilteringValues.AMOUNT.configKey,
						TrackerFilteringValues.AMOUNT.defaultValue
					),
				0,
				100
			);

		add(new JLabel("Amount"), c(0, row, 2));
		add(new AdjButton("+", 0, false), c(1, row, 2));
		add(
			amountLabel = new JLabel(StringUtils.prettyNumber(filterAmount) + "%"),
			c(2, row, 2)
		);
		add(new AdjButton("-", 0, true), c(3, row, 2));
		row++;
		filterTicks = (int) FastMath
			.clamp(
				server.config
					.getInt(
						TrackerFilteringValues.BUFFER.configKey,
						TrackerFilteringValues.BUFFER.defaultValue
					),
				0,
				80
			);

		add(new JLabel("Buffer"), c(0, row, 2));
		add(new AdjButton("+", 1, false), c(1, row, 2));
		add(ticksLabel = new JLabel(StringUtils.prettyNumber(filterTicks)), c(2, row, 2));
		add(new AdjButton("-", 1, true), c(3, row, 2));
	}

	void adjustValues(int cat, boolean neg) {
		if (cat == 0) {
			if (neg) {
				filterAmount = (int) FastMath.clamp(filterAmount - 10, 0, 100);
			} else {
				filterAmount = (int) FastMath.clamp(filterAmount + 10, 0, 100);
			}
			amountLabel.setText((StringUtils.prettyNumber(filterAmount)) + "%");
		} else if (cat == 1) {
			if (neg) {
				filterTicks = (int) FastMath.clamp(filterTicks - 1, 0, 80);
			} else {
				filterTicks = (int) FastMath.clamp(filterTicks + 1, 0, 80);
			}
			ticksLabel.setText((StringUtils.prettyNumber(filterTicks)));
		}

		server.updateTrackersFilters(filterType, filterAmount, filterTicks);
	}

	private class AdjButton extends JButton {

		public AdjButton(String text, int category, boolean neg) {
			super(text);
			addMouseListener(new MouseInputAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					adjustValues(category, neg);
				}
			});
		}
	}
}
