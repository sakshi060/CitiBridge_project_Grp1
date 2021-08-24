package com.citi.trade.recommendation.util;

import java.util.Base64;

public class PasswordUtil {
	public static String decodeString(String encodedPassword) {

		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password.reverse();
		return password.toString();
	}
}
