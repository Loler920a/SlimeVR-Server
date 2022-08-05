package dev.slimevr.vr.trackers;

import java.util.HashMap;
import java.util.Map;


public enum TrackerFilteringTypes {

	NONE(0, "none"),
	SMOOTHING(1, "smoothing"),
	PREDICTION(2, "prediction");

	public static final TrackerFilteringTypes[] values = values();
	public static final String CONFIG_KEY = "filtering.type";

	private static final Map<String, TrackerFilteringTypes> byStringVal = new HashMap<>();

	private static final Map<Number, TrackerFilteringTypes> byIdVal = new HashMap<>();

	static {
		for (TrackerFilteringTypes configVal : values()) {
			byIdVal.put(configVal.id, configVal);
			byStringVal.put(configVal.configVal.toLowerCase(), configVal);
		}
	}

	public final int id;
	public final String configVal;

	TrackerFilteringTypes(
		int id,
		String configVal
	) {
		this.id = id;
		this.configVal = configVal;
	}

	public static TrackerFilteringTypes getByStringValue(String stringVal) {
		return stringVal == null ? null : byStringVal.get(stringVal.toLowerCase());
	}

	public static TrackerFilteringTypes getById(int id) {
		return byIdVal.get(id);
	}
}
