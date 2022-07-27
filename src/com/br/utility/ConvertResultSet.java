package com.br.utility;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ConvertResultSet {

	public static String convertResultSetToJson(ResultSet rsset) throws SQLException {

		JSONArray mJSonArr = new JSONArray();
		ResultSetMetaData metadata = rsset.getMetaData();
		int numColumns = metadata.getColumnCount();

		while (rsset.next()) {
			JSONObject mJsonObj = new JSONObject();
			for (int i = 1; i <= numColumns; ++i) {
				try {
					String column_name = metadata.getColumnName(i);
//					mJsonObj.put(column_name, rsset.getObject(column_name));
					mJsonObj.put(column_name, rsset.getString(column_name));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
//			System.out.println(mJsonObj);
			mJSonArr.put(mJsonObj);
		}
		return mJSonArr.toString();
	}

}
