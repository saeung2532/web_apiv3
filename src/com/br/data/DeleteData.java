package com.br.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.codehaus.jettison.json.JSONObject;

import com.br.db.ConnectDB2;

public class DeleteData {

	public static String deleteItemDetail(String cono, String divi, String orderno, String line) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "DELETE BRLDTA0100.M3_TAKEORDERDETAIL \n" + 
					"WHERE TDCONO = '"+cono+"' \n" + 
					"AND TDDIVI = '"+divi+"' \n" + 
					"AND TDORNO = '"+orderno+"' \n" + 
					"AND TDLINE = '"+line+"'";
				System.out.println("deleteOrderLineDetail\n" + query);
				stmt.execute(query);

//				 mJsonObj.put("getPRNumber", getPRNumber);
				return mJsonObj.toString();

			} else {
				System.out.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}
	
	public static String deleteItemDetailSupport(String cono, String divi, String orderno, String line) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "DELETE BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
					"WHERE TDCONO = '"+cono+"' \n" + 
					"AND TDDIVI = '"+divi+"' \n" + 
					"AND TDORNO = '"+orderno+"' \n" + 
					"AND TDLINE = '"+line+"'";
				System.out.println("deleteOrderLineDetail\n" + query);
				stmt.execute(query);

//				 mJsonObj.put("getPRNumber", getPRNumber);
				return mJsonObj.toString();

			} else {
				System.out.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}
	
	public static String deleteUser(String cono, String divi, String salesup, String saleman) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "DELETE BRLDTA0100.SAL_SUPSALE \n" + 
					"WHERE S_CONO = '"+cono+"' \n" + 
					"AND S_SUPP = '"+salesup+"' \n" + 
					"AND S_SALE = '"+saleman+"'";
				System.out.println("deleteAuthUser\n" + query);
				stmt.execute(query);

//				 mJsonObj.put("getPRNumber", getPRNumber);
				return mJsonObj.toString();

			} else {
				System.out.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}
	

}
