package com.neriudon.example.code;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class IntCode implements Code<Integer> {

	public static final IntCode INSTANCE = IntCodeHolder.INSTANCE;

	private final Map<Integer, String> codeMap = createCodeMap();

	private IntCode() {
	}

	@Override
	public Map<Integer, String> asMap() {
		return codeMap;
	}

	private Map<Integer, String> createCodeMap() {
		Map<Integer, String> map = new LinkedHashMap<>();
		map.put(4, "foo");
		map.put(5, "bar");
		map.put(6, "hage");
		return Collections.unmodifiableMap(map);
	}

	private static class IntCodeHolder {
		private static final IntCode INSTANCE = new IntCode();
	}

}
