package com.neriudon.example.code;

import java.util.Map;

public interface Code<T> {

	/**
	 * check the code contained the map.
	 * 
	 * @param code
	 * @return true exists <br>
	 *         false not exist
	 */
	default boolean exists(T code) {
		return asMap().containsKey(code);
	}

	/**
	 * return map mapped codes to names.
	 * 
	 * @return
	 */
	Map<T, String> asMap();
}
