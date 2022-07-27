package com.br.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectPostgesSql {

    public static Connection doConnect() throws Exception {
	
	String jdbcClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://192.200.9.208:5432/bkrdb";

	Class.forName(jdbcClassName);
	return DriverManager.getConnection(url,"postgres","ictiicctt");
    }

    public static Connection doLogin(String user, String pass) throws Exception {

	String jdbcClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://192.200.20.208:5432/bkrdb";

	Class.forName(jdbcClassName);
	return DriverManager.getConnection(url, user, pass);
    }

}
