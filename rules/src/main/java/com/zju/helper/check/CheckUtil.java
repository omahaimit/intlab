package com.zju.helper.check;

import java.util.Set;

public class CheckUtil {

	public static final String toString(Set<String> set) {

		boolean isFirst = true;
		StringBuilder builder = new StringBuilder();
		if (set != null) {
			for (String s : set) {
				if (isFirst) {
					builder.append(s);
					isFirst = false;
				} else {
					builder.append(",");
					builder.append(s);
				}
			}
		}
		return builder.toString();
	}
}
