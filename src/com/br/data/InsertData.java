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

public class InsertData {

    static DecimalFormat df0 = new DecimalFormat("0");
    static DecimalFormat df2 = new DecimalFormat("#.##");
    static DecimalFormat df3 = new DecimalFormat("#.###");
    static DecimalFormat df4 = new DecimalFormat("#.####");
    
    public static String addOrderHead(String cono, String divi, String customerno, String orderdate, String delidate, String round, String pricelist, String ordertype, String whs, String status, String type, String remark,String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String getYear = delidate.substring(2, 4);
		String getOrderNumber = SelectData.getMaxOrderNumber(cono, divi, getYear);
		if (Integer.valueOf(getOrderNumber) == 0) {// Create new ordernumber
		    getOrderNumber = getYear + "00000001";

		}
		
		String saleSupport = SelectData.getSaleSuportNo(cono, divi, userid);
//			System.out.println(getOrderNumber + " : " + getOrderNumber);

		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERHEAD \n" + 
			"(THCONO, THDIVI, THORNO, THORTY, THWHLO, THCOCE, THORDA, THDEDA, THCUNO, THROUN, THPRIC, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, THSTAS, THENDA, THENTI, THENUS) \n" + 
			"VALUES('"+cono+"' \n" + 
			", '"+divi+"' \n" + 
			", '"+getOrderNumber+"' \n" + 
			", '"+ordertype+"' \n" + 
			", '"+whs+"' \n" + 
			", '' \n" + 
			", '"+orderdate+"' \n" + 
			", '"+delidate+"' \n" + 
			", '"+customerno+"' \n" + 
			", '"+round+"' \n" + 
			", '"+pricelist+"' \n" + 
			", '' \n" + 
			", '' \n" + 
			", '' \n" + 
			", '' \n" + 
			", '"+userid+"' \n" + 
			", '"+saleSupport+"' \n" + 
			", '"+remark+"' \n" + 
			", '' \n" + 
			", '"+status+"' \n" + 
			", CURRENT DATE \n" + 
			", CURRENT TIME \n" + 
			", '"+userid+"')";
			 System.out.println("addOrderHead\n" + query);
		stmt.execute(query);
		
		if(type.equals("create")) {
		    addItemDetailHistory(cono, divi, getOrderNumber, customerno, userid);
		}else {
		    addItemDetailCustomer(cono, divi, getOrderNumber, customerno, userid);
		}

		mJsonObj.put("result", "ok");
  		mJsonObj.put("message", getOrderNumber);
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

    public static String addItemDetail(String cono, String divi, String orderno, String customerno, String line, String itemno, String desc1, String desc2
	    , String qty, String unit, String remark, String status, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		
		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERDETAIL \n" + 
			"(TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n" + 
			"VALUES('"+cono+"' \n" + 
			", '"+divi+"' \n" + 
			", '"+orderno+"' \n" + 
			", '' \n" + 
			", '' \n" + 
			", (SELECT COALESCE(MAX(TDLINE),0) + 1 \n" + 
			"FROM BRLDTA0100.M3_TAKEORDERDETAIL \n" + 
			"WHERE TDCONO = '"+cono+"' \n" + 
			"AND TDDIVI = '"+divi+"' \n" + 
			"AND TDORNO = '"+orderno+"') \n" + 
			", '"+itemno+"' \n" + 
			", '"+qty+"' \n" + 
			", '"+unit+"' \n" + 
			", '"+remark+"' \n" + 
			", '' \n" + 
			", '"+status+"' \n" + 
			", CURRENT DATE \n" + 
			", CURRENT TIME \n" + 
			", '"+userid+"')";
		System.out.println("addItemDetail\n" + query);
		stmt.execute(query);

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
    
    public static String addItemDetailSupport(String cono, String divi, String orderno, String customerno, String line, String itemno, String desc1, String desc2
	    , String qty, String unit, String remark, String status, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		
		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
			"(TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n" + 
			"VALUES('"+cono+"' \n" + 
			", '"+divi+"' \n" + 
			", '"+orderno+"' \n" + 
			", '' \n" + 
			", '' \n" + 
			", (SELECT COALESCE(MAX(TDLINE),0) + 1 \n" + 
			"FROM BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
			"WHERE TDCONO = '"+cono+"' \n" + 
			"AND TDDIVI = '"+divi+"' \n" + 
			"AND TDORNO = '"+orderno+"') \n" + 
			", '"+itemno+"' \n" + 
			", '"+qty+"' \n" + 
			", '"+unit+"' \n" + 
			", '"+remark+"' \n" + 
			", '' \n" + 
			", '"+status+"' \n" + 
			", CURRENT DATE \n" + 
			", CURRENT TIME \n" + 
			", '"+userid+"')";
		System.out.println("addItemDetail\n" + query);
		stmt.execute(query);

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

    public static String addItemDetailToSupport(String cono, String divi, String orderno) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		
		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
			"(TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS ) \n" + 
			"SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS  \n" + 
			"FROM BRLDTA0100.M3_TAKEORDERDETAIL \n" + 
			"WHERE TDCONO = '"+cono+"' \n" + 
			"AND TDDIVI = '"+divi+"' \n" + 
			"AND TDORNO = '"+orderno+"'  \n" + 
			"AND TDIQTY > 0";
		System.out.println("addItemDetailSupport\n" + query);
		stmt.execute(query);

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

    
    public static String addItemDetailHistory(String cono, String divi, String orderno, String customerno, String userid) throws Exception {

	JSONObject mJsonObj = new JSONObject();
	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		
		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
			+ "SELECT '"+cono+"', '"+divi+"', '"+orderno+"','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, UNIT, '', '', '00', CURRENT DATE, CURRENT TIME, '"+userid+"' \n" + // MAX(QTY) AS QTYUNIT
			"FROM  \n" + 
			"(SELECT A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OACONO,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,COALESCE(A.QTY,0) AS QTY,COALESCE(INVQTY,0) AS INVQTY,COALESCE(INVCAWE,0) AS INVCAWE,A.UNIT \n" + 
			"FROM \n" + 
			"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN UNIT,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n" + 
			"FROM M3FDBPRD.OOHEAD,M3FDBPRD.OOLINE,M3FDBPRD.OCUSMA,M3FDBPRD.MITMAS,M3FDBPRD.CSYTAB,M3FDBPRD.ODHEAD \n" + 
			"WHERE OACONO = OBCONO \n" + 
			"AND OAORNO = OBORNO \n" + 
			"AND OACONO = '"+cono+"' \n" + 
			"AND OADIVI = '"+divi+"' \n" + 
			"AND OACUNO = OKCUNO \n" + 
			"AND OACONO = OKCONO \n" + 
			"AND OACONO = MMCONO \n" + 
			"AND OBITNO = MMITNO \n" + 
			"AND CTCONO = OACONO \n" + 
			"AND OACONO = UACONO \n" + 
			"AND OAORNO = UAORNO \n" + 
			"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" + 
			"AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - 14 DAYS AND CURRENT_DATE \n" + 
			"AND OACUNO = '"+customerno+"' \n" + 
			"AND CTSTKY = OASMCD \n" + 
			"AND CTSTCO ='SMCD') A  \n" + 
			"LEFT JOIN \n" + 
			"(SELECT SUM(MTTRQT) AS INVQTY,SUM(MTCAWE) AS INVCAWE,MTRIDN,MTCONO,MTITNO \n" + 
			"FROM M3FDBPRD.MITTRA \n" + 
			"GROUP BY MTRIDN,MTCONO,MTITNO) B \n" + 
			"ON A.OACONO = B.MTCONO \n" + 
			"AND A.OBITNO = B.MTITNO \n" + 
			"AND A.OAORNO = B.MTRIDN) AS detail \n" + 
			"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, UNIT";
		System.out.println("addItemDetailHistory\n" + query);
		stmt.execute(query);

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

    
    public static String addItemDetailCustomer(String cono, String divi, String orderno, String customerno, String userid) throws Exception {

   	JSONObject mJsonObj = new JSONObject();
   	Connection conn = ConnectDB2.doConnect();
   	try {
   	    if (conn != null) {

   		Statement stmt = conn.createStatement();
   		
   		String query = "INSERT INTO BRLDTA0100.M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
   			+ "SELECT '"+cono+"', '"+divi+"', '"+orderno+"','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, UNIT, '', '', '00', CURRENT DATE, CURRENT TIME, '"+userid+"' \n" + // MAX(QTY) AS QTYUNIT
   			"FROM  \n" + 
   			"(SELECT A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OACONO,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,COALESCE(A.QTY,0) AS QTY,COALESCE(INVQTY,0) AS INVQTY,COALESCE(INVCAWE,0) AS INVCAWE,A.UNIT \n" + 
   			"FROM \n" + 
   			"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN UNIT,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n" + 
   			"FROM M3FDBPRD.OOHEAD,M3FDBPRD.OOLINE,M3FDBPRD.OCUSMA,M3FDBPRD.MITMAS,M3FDBPRD.CSYTAB,M3FDBPRD.ODHEAD \n" + 
   			"WHERE OACONO = OBCONO \n" + 
   			"AND OAORNO = OBORNO \n" + 
   			"AND OACONO = '"+cono+"' \n" + 
   			"AND OADIVI = '"+divi+"' \n" + 
   			"AND OACUNO = OKCUNO \n" + 
   			"AND OACONO = OKCONO \n" + 
   			"AND OACONO = MMCONO \n" + 
   			"AND OBITNO = MMITNO \n" + 
   			"AND CTCONO = OACONO \n" + 
   			"AND OACONO = UACONO \n" + 
   			"AND OAORNO = UAORNO \n" + 
   			"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" + 
//   			"AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - 7 DAYS AND CURRENT_DATE \n" + 
   			"AND OACUNO = '"+customerno+"' \n" + 
   			"AND OAORNO = (SELECT MAX(OAORNO) \n" + 
   			"FROM M3FDBPRD.OOHEAD,M3FDBPRD.OOLINE,M3FDBPRD.OCUSMA,M3FDBPRD.MITMAS,M3FDBPRD.CSYTAB,M3FDBPRD.ODHEAD \n" + 
   			"WHERE OACONO = OBCONO \n" + 
   			"AND OAORNO = OBORNO \n" + 
   			"AND OACONO = '"+cono+"' \n" + 
   			"AND OADIVI = '"+divi+"' \n" + 
   			"AND OACUNO = OKCUNO \n" + 
   			"AND OACONO = OKCONO \n" + 
   			"AND OACONO = MMCONO \n" + 
   			"AND OBITNO = MMITNO \n" + 
   			"AND CTCONO = OACONO \n" + 
   			"AND OACONO = UACONO \n" + 
   			"AND OAORNO = UAORNO \n" + 
   			"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" + 
   			"AND OACUNO = '"+customerno+"' \n" + 
   			"AND CTSTKY = OASMCD \n" + 
   			"AND CTSTCO ='SMCD') \n" + 
   			"AND CTSTKY = OASMCD \n" + 
   			"AND CTSTCO ='SMCD') A  \n" + 
   			"LEFT JOIN \n" + 
   			"(SELECT SUM(MTTRQT) AS INVQTY,SUM(MTCAWE) AS INVCAWE,MTRIDN,MTCONO,MTITNO \n" + 
   			"FROM M3FDBPRD.MITTRA \n" + 
   			"GROUP BY MTRIDN,MTCONO,MTITNO) B \n" + 
   			"ON A.OACONO = B.MTCONO \n" + 
   			"AND A.OBITNO = B.MTITNO \n" + 
   			"AND A.OAORNO = B.MTRIDN) AS detail \n" + 
   			"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, UNIT";
   		System.out.println("addItemDetailCustomer\n" + query);
   		stmt.execute(query);

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
    
    public static String addUser(String cono, String divi, String salesup, String saleman, String channel) throws Exception {

   	JSONObject mJsonObj = new JSONObject();
   	Connection conn = ConnectDB2.doConnect();
   	try {
   	    if (conn != null) {

   		Statement stmt = conn.createStatement();
   		
   		String query = "INSERT INTO BRLDTA0100.SAL_SUPSALE \n" + 
   			"(S_CONO, S_SUPP, S_SALE, S_CHAN) \n" + 
   			"VALUES ('"+cono+"' \n" + 
   			", '"+salesup+"' \n" + 
   			", '"+saleman+"' \n" + 
   			", '"+channel+"')";
   		System.out.println("addGroupingUser\n" + query);
   		stmt.execute(query);

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
