package com.br.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.br.db.ConnectDB2;

public class UpdateData {

    static DecimalFormat df0 = new DecimalFormat("0");
    static DecimalFormat df2 = new DecimalFormat("#.##");
    static DecimalFormat df3 = new DecimalFormat("#.###");
    static DecimalFormat df4 = new DecimalFormat("#.####");

    public static String addPRHead(String values, String device) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();

	try {
	    if (conn != null) {

		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("INSERT INTO BRLDTA0100.M3_PMDETAIL \n"
			+ "(PMDCONO, PMDDIVI, PMDFACI, PMDWHS, PMDDATE, PMDWO, PMDACTS, PMDSEQ, PMDITEMCODE, PMDMCCODE, PMDINSID, PMDDESC1, PMDDESC2, PMDVALUE, PMDREMK, PMDUOM, PMDINPTYPE, PMDENTDATE, PMDUSER, PMDSTS) \n"
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		JSONArray arr = new JSONArray(values);
		for (int i = 0; i < arr.length(); i++) {
		    JSONObject obj = arr.getJSONObject(i);
		    System.out.println(obj);
		    // {"M7SPOS":"1","M7PRNO":"RF04M0010010","M7MWNO":"0001100781","M7INSI":"RF-00M12001","LIINSN":"CR-RF-01","M7RVAL":"","M7RPDT":"20210118","PJSPOS":"1","PJTX15":"เปลี่ยนถ่ายน้ำม","PJTX40":"เปลี่ยนถ่ายน้ำมัน
		    // COMPRESSOR","STD":"ปฏิบัติ","PJRSIY":"VISUAL","PJRUOM":"UNT","QHWHST":"90","QHWHHS":"90","REM":""}
		    pstmt.setString(1, obj.getString("CCCONO"));
		    pstmt.setString(2, obj.getString("CCDIVI"));
		    pstmt.setString(3, obj.getString("M7FACI"));
		    pstmt.setString(4, obj.getString("QHWHLO"));
		    pstmt.setString(5, obj.getString("QHFIDT"));
		    pstmt.setString(6, obj.getString("M7MWNO"));
		    pstmt.setString(7, obj.getString("M7ACTS"));
		    pstmt.setString(8, obj.getString("M7SPOS"));
		    pstmt.setString(9, obj.getString("M7PRNO"));
		    pstmt.setString(10, obj.getString("M7INSI"));
		    pstmt.setString(11, obj.getString("LIINSN"));
		    pstmt.setString(12, obj.getString("PJTX15"));
		    pstmt.setString(13, obj.getString("PJTX40"));
		    pstmt.setString(14, obj.getString("M7RVAL"));
		    pstmt.setString(15, obj.getString("REM"));
		    pstmt.setString(16, obj.getString("PJRUOM"));
		    pstmt.setString(17, obj.getString("PJRSIY"));
		    pstmt.setString(18, obj.getString("ENTDATE"));
		    pstmt.setString(19, device);
		    pstmt.setString(20, "00");
		    pstmt.executeUpdate();
//		System.out.println("addPRHead\n" + pstmt);
		}

		pstmt.close();

		mJsonObj.put("result", "ok");
		mJsonObj.put("message", "Insert compelete.");
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

    public static String updateOrderHead(String cono, String divi, String orderno, String orderdate, String delidate,
	    String round, String pricelist, String ordertype, String whs, String status, String type, String remark, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERHEAD \n" 
			+ "SET THORDA = '" + orderdate + "' \n"
			+ ", THDEDA = '" + delidate + "' \n" 
			+ ", THROUN = '" + round + "' \n" 
			+ ", THPRIC = '" + pricelist + "' \n" 
			+ ", THORTY = '" + ordertype + "' \n" 
			+ ", THWHLO = '" + whs + "' \n" 
			+ ", THREM1 = '" + remark + "' \n" 
			+ ", THSTAS = '" + status + "' \n"
			+ ", THENDA = CURRENT DATE \n"
			+ ", THENTI = CURRENT TIME \n"
			+ ", THENUS = '" + userid + "' \n"
			+ "WHERE THCONO = '" + cono + "' \n" 
			+ "AND THDIVI = '" + divi + "' \n" 
			+ "AND THORNO = '"
			+ orderno + "'";
		System.out.println("updateOrderHead\n" + query);
		stmt.execute(query);

		mJsonObj.put("result", "ok");
		mJsonObj.put("message", type + " complete.");
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

    public static String updateStatusOrderHead(String cono, String divi, String orderno, String status, String userid)
	    throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERHEAD \n" 
			+ "SET THSTAS = '" + status + "' \n"
			+ ", THENDA = CURRENT DATE \n"
			+ ", THENTI = CURRENT TIME \n"
			+ ", THENUS = '" + userid + "' \n"
			+ "WHERE THCONO = '" + cono + "' \n" 
			+ "AND THDIVI = '" + divi + "' \n" 
			+ "AND THORNO = '" + orderno + "'";
		System.out.println("updateOrderHead\n" + query);
		stmt.execute(query);
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
    
    public static String updateStatusOrderDetail(String cono, String divi, String orderno, String status)
	    throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAIL \n" 
			+ "SET TDSTAS = '"+status+"' \n"
			+ "WHERE TDCONO = '" + cono + "' \n" 
			+ "AND TDDIVI = '" + divi + "' \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDIQTY > 0";
		System.out.println("updateStatusOrderDetail\n" + query);
		stmt.execute(query);
		
		if (status.equals("10")) {
		    InsertData.addItemDetailToSupport(cono, divi, orderno);
		}
		
		
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

    public static String updateItemDetail(String cono, String divi, String orderno, String line, String qty,
	    String unit, String remark, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		if (remark == null) {
		    remark = "";
		}

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAIL \n" 
			+ "SET TDIQTY = '" + qty + "' \n"
			+ ", TDUNIT = '" + unit + "' \n" 
			+ ", TDREM1 = '" + remark + "' \n" 
			+ ", TDENDA = CURRENT DATE \n"
			+ ", TDENTI = CURRENT TIME \n"
			+ ", TDENUS = '" + userid + "' \n"
			+ "WHERE TDCONO = '" + cono + "' \n" 
			+ "AND TDDIVI = '" + divi + "' \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDLINE = '" + line + "'";
		System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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
    
    public static String updateItemDetailSupport(String cono, String divi, String orderno, String line, String qty,
	    String unit, String remark, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		if (remark == null) {
		    remark = "";
		}

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" 
			+ "SET TDIQTY = '" + qty + "' \n"
			+ ", TDUNIT = '" + unit + "' \n" 
			+ ", TDREM1 = '" + remark + "' \n" 
			+ ", TDENDA = CURRENT DATE \n"
			+ ", TDENTI = CURRENT TIME \n"
			+ ", TDENUS = '" + userid + "' \n"
			+ "WHERE TDCONO = '" + cono + "' \n" 
			+ "AND TDDIVI = '" + divi + "' \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDLINE = '" + line + "'";
		System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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

    public static String updateCOOrderDetail(String cono, String divi, String orderno, String conumber, String status)
	    throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAIL \n" 
			+ "SET TDCONU = '" + conumber + "' \n"
			+ ", TDSTAS = '" + status + "' \n" 
			+ "WHERE TDCONO = '" + cono + "'  \n" 
			+ "AND TDDIVI = '" + divi + "'  \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDSTAS = '10'";
		System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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

    public static String updateCOOrderDetailSupport(String cono, String divi, String orderno, String conumber, String status)
	    throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" 
			+ "SET TDCONU = '" + conumber + "' \n"
			+ ", TDSTAS = '" + status + "' \n" 
			+ "WHERE TDCONO = '" + cono + "'  \n" 
			+ "AND TDDIVI = '" + divi + "'  \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDSTAS = '10'";
		System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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

    
    public static String updateInvoiceItemDetail(String cono, String divi, String orderno, String invnumber,
	    String status) throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE BRLDTA0100.M3_TAKEORDERDETAIL \n" 
			+ "SET TDIVNO = '" + invnumber + "' \n"
			+ ", TDSTAS = '" + status + "' \n" 
			+ "WHERE TDCONO = '" + cono + "'  \n" 
			+ "AND TDDIVI = '" + divi + "'  \n" 
			+ "AND TDORNO = '" + orderno + "' ";
		System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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

    public static String updateUser(String cono, String divi, String username, String status) throws Exception {

	JSONObject mJsonObj = new JSONObject();

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "UPDATE M3FDBPRD.CMNUSR \n" 
			+ "SET JUFRF8 = '"+status+"' \n" 
			+ "WHERE JUFRF6 = '"+username+"' \n"
			+ "AND JUFRF7 = 'SALESUP' ";
//  			System.out.println("updateOrderDatail\n" + query);
		stmt.execute(query);
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
