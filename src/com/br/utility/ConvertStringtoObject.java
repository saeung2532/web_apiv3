package com.br.utility;

public class ConvertStringtoObject {

	public static String convertNullToObject(String str) {
//		System.out.println("str:" + str);

		if (str.equals("null")) {
			return null;
		} else {
			return str;
		}

	}

}
