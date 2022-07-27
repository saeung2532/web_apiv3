package com.br.utility;

public class ConvertDate {

    public static String convertDateToDecimal(String date) {
//		System.out.println(date);
//		System.out.println(date.substring(0,4) + date.substring(5,7) + date.substring(8,10));
	return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
    }

    public static String convertDecimalToDateM3(String date) {
//		System.out.println(date);
	System.out.println(date.substring(6, 8) + date.substring(4, 6) + date.substring(2, 4));
	return date.substring(6, 8) + date.substring(4, 6) + date.substring(2, 4);
    }

    public static String convertDateToDateM3(String date) {
//		System.out.println(date);
//		System.out.println(date.substring(0,4) + date.substring(5,7) + date.substring(8,10));
	return date.substring(8, 10) + date.substring(5, 7) + date.substring(2, 4);
    }

}
