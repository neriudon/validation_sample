package com.neriudon.example.code;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringCode implements Code<String> {

  public static final StringCode INSTANCE = StringCodeHolder.INSTANCE;

  private final Map<String, String> codeMap = createCodeMap();

  private StringCode() {}

  @Override
  public Map<String, String> asMap() {
    return codeMap;
  }

  private Map<String, String> createCodeMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("1", "foo");
    map.put("2", "bar");
    map.put("3", "hage");
    return Collections.unmodifiableMap(map);
  }

  private static class StringCodeHolder {
    private static final StringCode INSTANCE = new StringCode();
  }
}
