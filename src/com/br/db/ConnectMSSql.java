package com.br.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectMSSql {

    public static Connection doConnect() throws Exception {
	
	String jdbcClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
        String url = "jdbc:sqlserver://192.200.9.182:1433;database=BRLAS400;user=sa;password=sa";

	Class.forName(jdbcClassName);
	return DriverManager.getConnection(url,"sa","sa");
    }

    public static Connection doLogin(String user, String pass) throws Exception {

//		String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
//		String url = "jdbc:as400://192.200.9.190";

	String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
	String url = "jdbc:jtopenlite://192.200.9.190";

	Class.forName(jdbcClassName);
	return DriverManager.getConnection(url, user, pass);
    }

}
