package dev.slimevr.vr.trackers;

import java.util.HashMap;
import java.util.Map;


public enum TrackerFilteringValues {

	AMOUNT("Amount", "amount", 30),
	BUFFER("Buffer", "buffer", 2);

	public static final TrackerFilteringValues[] values = values();
	public static final String CONFIG_PREFIX = "filtering.";

	private static final Map<String, TrackerFilteringValues> byStringVal = new HashMap<>();

	private static final Map<Number, TrackerFilteringValues> byIdVal = new HashMap<>();

	static {
		for (TrackerFilteringValues configVal : values()) {
			byStringVal.put(configVal.stringVal.toLowerCase(), configVal);
		}
	}
	public final String stringVal;
	public final String configKey;
	public final int defaultValue;

	TrackerFilteringValues(
		String stringVal,
		String configKey,
		int defaultValue
	) {
		this.stringVal = stringVal;
		this.configKey = CONFIG_PREFIX + configKey;
		this.defaultValue = defaultValue;
	}

	public static TrackerFilteringValues getByStringValue(String stringVal) {
		return stringVal == null ? null : byStringVal.get(stringVal.toLowerCase());
	}
}
