package com.tecgesco.wmscerrado;

import java.util.HashMap;
import java.util.Map;

public class Tools {

	public Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<>();
		if (query == null) {
			return result;
		}
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}
}
