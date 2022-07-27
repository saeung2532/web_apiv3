package com.br.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.br.db.ConnectDB2;
import com.br.db.ConnectMSSql;
import com.br.db.ConnectPostgesSql;
import com.br.utility.ConvertResultSet;

public class SelectData {

    public static String getUserAuth(String cono, String divi, String username) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT US_DEPT \n" 
			+ "FROM BRLDTA0100.STAFFAUTH a, BRLDTA0100.STAFFLIST b \n"
			+ "WHERE US_CONO = '" + cono + "' \n" 
			+ "AND US_DIVI = '" + divi + "' \n" 
			+ "AND US_LOGIN = '" + username + "' \n" 
			+ "AND b.ST_N6L3 = a.US_LOGIN \n" 
			+ "AND b.ST_STS = '20' \n"
			+ "GROUP BY US_DEPT";
//		System.out.println("getUserAuth\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {
		    return mRes.getString("US_DEPT").trim();
		}

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

    public static String getSalemanCode(String cono, String divi, String username) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT JUUSID,CASE WHEN SUBSTRING(JUTX40,5,1) = ' ' AND SUBSTRING(JUTX40,6,1) <> ' ' THEN SUBSTRING(JUTX40,6) ELSE JUTX40 END AS SMNAME,JULSID,JUFRF6,JUFRF7,CAST(JUFRF8 AS DECIMAL(2,0)) AS JUFRF8 \n"
			+ "FROM M3FDBPRD.CMNUSR \n" + "WHERE JULSID = '" + username + "' ";
//		System.out.println("getUserAuth\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {
		    return mRes.getString("JUUSID").trim() + " ; " + mRes.getString("JUFRF6").trim() + " ; "
			    + mRes.getString("JUFRF7").trim() + " ; " + mRes.getString("JUFRF8").trim();
		}

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

    public static Boolean getUserEnable(String cono, String divi, String username, String password) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT COUNT(*) AS COUNT \n" + "FROM BRLDTA0100.STAFFLIST \n" + "WHERE ST_CONO = '"
			+ cono + "' \n" + "AND ST_CODE = '" + username + "'\n" + "AND SUBSTRING(ST_CODE,4) = '"
			+ password + "'";
//   		System.out.println("getUserCode\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {

		    if (mRes.getInt("COUNT") > 0) {
			return true;
		    }

		}

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

	return false;

    }

    public static String getCompany() throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI, CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) AS COMPANY\n"
			+ "FROM M3FDBPRD.CMNDIV\n" + "WHERE CCDIVI != ''\n" + "ORDER BY CCCONO";
//				System.out.println("SelectCompany\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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
    
    
    public static String getDuck() throws Exception {

	Connection conn = ConnectPostgesSql.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "select * \n"
			+ "from pmquarter \n"
			+ "where pmdwo = '0004400028'";
				System.out.println("getDuck\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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
    
    public static String getDuckV2(String device, String date) throws Exception {

	Connection conn = ConnectPostgesSql.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "select * from \n"
			+ "( \n"
			+ "select pdate,topic,device,max(grade_a) grade_a , max(grade_b) grade_b from \n"
			+ "( \n"
			+ "select  substring(info->>'entrydate',1,8) pdate,info->>'topic' topic,info->>'device' device,info->>'Grade_A' grade_a ,info->>'Grade_B' grade_b \n"
			+ "from bkrdb.public.orders \n"
			+ "where info->>'Grade_A' <> 'read' \n"
			+ ") a \n"
			+ "group by pdate,topic,device \n"
			+ ") a \n"
			+ "where topic || device = '"+device+"' \n"
			+ "and pdate = '"+date+"'";
				System.out.println("getDuck\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getCompanyWithToken() throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY CCCONO) AS ID, CCCONO, CCDIVI, CCCONM, TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) AS COMPANY\n"
			+ "FROM M3FDBPRD.CMNDIV\n" + "WHERE CCDIVI != ''\n" + "ORDER BY CCCONO";
//				System.out.println("SelectCompany\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getFacility(String cono, String divi) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY CFFACI) AS ID,CFFACI,CFFACN, TRIM(CFFACI) || ' : ' || TRIM(CFFACN) AS FACILITY \n"
			+ "FROM M3FDBPRD.CFACIL \n" + "WHERE CFCONO = '" + cono + "'\n" + "AND CFDIVI = '" + divi
			+ "' \n" + "ORDER BY CFFACI";
//				System.out.println("getFacility\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getWarehouse(String cono, String fac) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY MWWHLO) AS ID, MWWHLO, MWWHNM, TRIM(MWWHLO) || ' : ' || TRIM(MWWHNM)  AS WAREHOUSE \n"
			+ "FROM M3FDBPRD.MITWHL \n" + "WHERE MWCONO = '" + cono + "' \n" + "AND MWFACI = '" + fac
			+ "' \n" + "ORDER BY MWWHLO";
		System.out.println("getWarehouse\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getPRHead01(String cono, String divi) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT * \n" + "FROM BRLDTA0100.M3_PRHEAD01 \n" + "WHERE HD_IBCONO = '" + cono + "' \n"
			+ "AND HD_IBDIVI = '" + divi + "'";
//				System.out.println("SelectCompany\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getPRNumber(String cono, String divi, String username) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER() AS ID,HD_IBPLPN \n" + "FROM BRLDTA0100.M3_PRHEAD01 mp  \n"
			+ "WHERE HD_IBCONO = '" + cono + "' \n" + "AND HD_IBDIVI = '" + divi + "' \n"
			+ "AND HD_IBPURC = '" + username + "' \n" + "ORDER BY HD_IBPLPN DESC";
//				System.out.println("getPRNumber\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getWOHead(String cono, String divi, String warehouse) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE, TRIM(PMHPRD) AS PMHPRD, TRIM(LIINSN) AS LIINSN, TRIM(PMHLOT) AS PMHLOT, PMHSTP, TRIM(PMHSER) AS PMHSER, TRIM(PMHWO) AS PMHWO, PMHSTMAT, PMHSTEMP, PMHUSER  \n"
			+ "FROM BRLDTA0100.M3_PMHEAD a, M3FDBPRD.MILOIN b \n" + "WHERE a.PMHCONO = '" + cono + "' \n"
			+ "AND a.PMHWHS = '" + warehouse + "' \n" + "AND b.LICONO = a.PMHCONO \n"
			+ "AND b.LIITNO = a.PMHPRD \n" + "AND a.PMHWO NOT IN (SELECT PMDWO \n"
			+ "FROM BRLDTA0100.M3_PMDETAIL \n" + "WHERE PMDCONO = '" + cono + "' \n" + "AND PMDWHS = '"
			+ warehouse + "' \n" + "GROUP BY PMDWO) \n" + "AND a.PMHSTMAT BETWEEN '40' AND '60' \n"
			+ "AND a.PMHSTEMP BETWEEN '40' AND '60' \n"
			+ "ORDER BY PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE DESC, PMHWO";
		System.out.println("getWOHead\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getWOHeadMachine(String cono, String divi, String faci, String whs, String machine,
	    String status1, String status2, String status3, String status4) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE, TRIM(PMHPRD) AS PMHPRD, TRIM(LIINSN) AS LIINSN, TRIM(PMHLOT) AS PMHLOT, PMHSTP, TRIM(PMHSER) AS PMHSER, TRIM(PMHWO) AS PMHWO, PMHSTMAT, PMHSTEMP, PMHUSER  \n"
			+ "FROM BRLDTA0100.M3_PMHEAD a, M3FDBPRD.MILOIN b \n" + "WHERE a.PMHCONO = '" + cono + "' \n"
			+ "AND a.PMHWHS = '" + whs + "' \n" + "AND b.LIINSN = '" + machine + "' \n"
			+ "AND b.LICONO = a.PMHCONO \n" + "AND b.LIITNO = a.PMHPRD \n"
			+ "AND a.PMHWO NOT IN (SELECT PMDWO \n" + "FROM BRLDTA0100.M3_PMDETAIL \n" + "WHERE PMDCONO = '"
			+ cono + "' \n" + "AND PMDWHS = '" + whs + "' \n" + "GROUP BY PMDWO) \n"
			+ "AND a.PMHSTMAT BETWEEN '" + status1 + "' AND '" + status2 + "' \n"
			+ "AND a.PMHSTEMP BETWEEN '" + status3 + "' AND '" + status4 + "' \n"
			+ "ORDER BY PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE DESC, PMHWO";
//				System.out.println("getWOHeadMachine\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getWOHeadFaci(String cono, String divi, String faci) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE, TRIM(PMHPRD) AS PMHPRD, TRIM(LIINSN) AS LIINSN, TRIM(PMHLOT) AS PMHLOT, PMHSTP, TRIM(PMHSER) AS PMHSER, TRIM(PMHWO) AS PMHWO, PMHSTMAT, PMHSTEMP, PMHUSER  \n"
			+ "FROM BRLDTA0100.M3_PMHEAD a, M3FDBPRD.MILOIN b \n" + "WHERE a.PMHCONO = '10' \n"
			+ "AND a.LPHMFACI = '" + faci + "' \n" + "AND b.LICONO = a.PMHCONO \n"
			+ "AND b.LIITNO = a.PMHPRD \n" + "AND a.PMHWO NOT IN (SELECT PMDWO \n"
			+ "FROM BRLDTA0100.M3_PMDETAIL \n" + "WHERE PMDCONO = '10' \n" +
//					"AND PMDWHS = 'A91' \n" + 
			"GROUP BY PMDWO) \n" + "AND a.PMHSTMAT BETWEEN '40' AND '60' \n"
			+ "AND a.PMHSTEMP BETWEEN '40' AND '60' \n"
			+ "ORDER BY PMHCONO, PMHDIVI, LPHMFACI, PMHWHS, PMHDATE, PMHWO";
		System.out.println("getWOHeadFaci\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getWODetail(String cono, String divi, String faci, String wono, String db2ib, String status1,
	    String status2, String status3, String status4) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT ROW_NUMBER() OVER(ORDER BY M7MWNO) AS ID,CCCONO, CCDIVI, TRIM(CCCONM) AS CCCONM, M7FACI, QHWHLO, QHFIDT, TRIM(M7MWNO) AS M7MWNO, M7ACTS, M7SPOS, TRIM(M7PRNO) AS M7PRNO, TRIM(M7INSI) AS M7INSI, CASE WHEN C.LIINSN IS NULL THEN '' ELSE TRIM(C.LIINSN) END AS LIINSN, PJSPOS \n"
			+ ",TRIM(PJTX15) AS PJTX15, TRIM(PJTX40) AS PJTX40, TRIM(STD) AS STD, TRIM(M7RVAL) AS M7RVAL, '' AS REM, PJRUOM, TRIM(PJRSIY) AS PJRSIY,VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYYMMDD') AS ENTDATE,'' AS USER, '00' AS STS \n"
			+ "FROM \n"
			+ "(SELECT M7CONO, M7FACI, M7ACTS, M7SPOS, M7PRNO, M7MWNO, M7INSI, M7RVAL, M7RPDT \n" + "FROM "
			+ db2ib + ".MMQIRS \n" + "WHERE M7CONO = '" + cono + "' \n" + "AND M7FACI = '" + faci + "' \n"
			+ "ORDER BY M7CONO,M7FACI,M7MWNO,M7ACTS,M7SPOS) AS A \n" + "LEFT JOIN \n"
			+ "(SELECT PJSPOS, PJINSI, PJTX15, PJTX40, PJRSIY, PJRUOM, TRIM(PJTXT1) || ' ' || TRIM(PJOPE1) || ' ' || TRIM(PJRBFR) || ' ' || TRIM(PJOPE2) || ' ' || TRIM(PJRBTO) AS STD \n"
			+ "FROM " + db2ib + ".MPDIIL \n" + "WHERE PJCONO = '" + cono + "') AS B \n"
			+ "ON B.PJINSI = A.M7INSI AND B.PJSPOS = A.M7SPOS \n" + "LEFT JOIN \n"
			+ "(SELECT LIITNO,LIINSN \n" + "FROM " + db2ib + ".MILOIN \n" + "WHERE LICONO = '" + cono
			+ "' \n" + "AND LISTAT = '20' \n" + "AND LIINSN <> '') AS C \n" + "ON C.LIITNO = A.M7PRNO \n"
			+ "LEFT JOIN \n" + "(SELECT QHPRNO, QHMWNO, QHWHST, QHWHHS , QHWHLO , QHFIDT \n" + "FROM "
			+ db2ib + ".MMOHED \n" + "WHERE QHCONO = '" + cono + "' \n" + "AND QHDIVI = '" + divi + "' \n"
			+ "AND QHFACI = '" + faci + "') AS D \n" + "ON D.QHPRNO = A.M7PRNO AND D.QHMWNO = A.M7MWNO \n"
			+ "LEFT JOIN \n" + "(SELECT CCCONO, CCDIVI, CCCONM \n" + "FROM M3FDBPRD.CMNDIV c  \n"
			+ "WHERE CCDIVI != '' \n" + "ORDER BY CCCONO,CCDIVI) AS E \n" + "ON E.CCCONO = a.M7CONO \n"
			+ "WHERE M7MWNO = '" + wono + "' \n" + "AND M7SPOS BETWEEN 1 AND 1 \n" + "AND QHWHST BETWEEN '"
			+ status1 + "' AND '" + status2 + "' \n" + "AND QHWHHS BETWEEN '" + status3 + "' AND '"
			+ status4 + "'";
		System.out.println("getWODetail\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getUserM3(String cono, String divi, String dept) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT UA_USER, UA_PASS \n" + "FROM BRLDTA0100.M3_USERADDON \n" + "WHERE UA_CONO = '"
			+ cono + "' \n" + "AND UA_DIVI = '" + divi + "' \n" + "AND UA_DEPT = '" + dept + "'";
//			System.out.println("getUserM3\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {
		    return mRes.getString("UA_USER").trim() + " ; " + mRes.getString("UA_PASS").trim();
		}

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

    public static String getCustomer(String cono, String divi, String saleman) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT ROW_NUMBER() OVER(ORDER BY OKCFC3,A.OKCONO,A.OKCUNO) AS ID,A.OKCONO,A.OKCFC3 CHANEL,CH.CTTX15 NCHANEL,A.OKCUCL GROUPC,TRIM(G.CTTX15) AS NGROUP,CASE WHEN OKCUNM LIKE '%�Թʴ%' THEN 'YES' ELSE 'NO' END AS DCASH, \n"
			+ "A.OKCUNO CUST,A.OKCUNM,A.OKCUNO || ' : ' || A.OKCUNM AS CUSTOMER,A.OKPYNO PAYER,A.OKCUA1 ADDR1,A.OKCUA2 ADDR2,A.OKCUA3 ADDR3,A.OKCUA4 ADDR4,A.OKPHNO TEL,A.OKECAR AREA,R.CTTX15 NAREA, \n"
			+ "A.OKSMCD SALESMAN,S.CTTX15 NSALES,A.OKCRLM CREDIT1,A.OKCRL2 CREDIT2,A.OKCRL3 CREDIT3,A.OKTEPY PAYMENT,T.CTTX15 NPAYMENT,A.OKSTAT STATUS,TRIM(A.OKPLTB) AS PRICELIST, \n"
			+ "A.OKDISY DISC,A.OKCFC3 CHANEL,CH.CTTX15 NCHANEL,A.OKRGDT CREAT_DATE,A.OKCHID USERUPDATE,A.OKLMDT AS LAST_DATE \n"
			+ "FROM \n"
			+ "(SELECT OKCONO,OKCUNO,OKCUNM, OKPYNO,OKCFC3,OKCUCL,OKCUA1,OKCUA2,OKCUA3,OKCUA4,OKPHNO,OKECAR,OKCFC9,OKSMCD,OKCRLM,OKCRL2,OKCRL3,OKTEPY,OKPYCD, OKPLTB,OKDISY,OKLMDT,OKCHID,OKRGDT,OKSTAT \n"
			+ "FROM M3FDBPRD.OCUSMA \n" 
			+ "WHERE OKCONO = '" + cono + "' \n" 
			+ "AND OKSTAT = '20' \n"
			+ "AND OKSMCD = '" + saleman + "' \n"
			+ "AND (OKCUCL BETWEEN '00' AND '13' OR OKCUCL BETWEEN '0A' AND '0Z' OR OKCUCL BETWEEN '1A' AND '1Z')) A \n"
			+ "LEFT JOIN M3FDBPRD.CSYTAB AS G ON G.CTSTKY = A.OKCUCL AND G.CTCONO = A.OKCONO AND G.CTSTCO ='CUCL' \n"
			+ "LEFT JOIN M3FDBPRD.CSYTAB AS R ON R.CTSTKY = A.OKECAR AND R.CTCONO = A.OKCONO AND R.CTSTCO ='ECAR' \n"
			+ "LEFT JOIN M3FDBPRD.CSYTAB AS T ON T.CTSTKY = A.OKTEPY AND T.CTCONO = A.OKCONO AND T.CTSTCO ='TEPY' \n"
			+ "LEFT JOIN M3FDBPRD.CSYTAB AS CH ON CH.CTSTKY = A.OKCFC3 AND CH.CTCONO = A.OKCONO AND CH.CTSTCO ='CFC3' \n"
			+ "LEFT JOIN M3FDBPRD.CSYTAB AS S ON S.CTSTKY = A.OKSMCD AND S.CTCONO = A.OKCONO AND S.CTSTCO ='SMCD' \n"
			+ "ORDER BY OKCFC3,A.OKCONO,A.OKCUNO";
		System.out.println("getCustomer\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getOrderNumberSaleman(String cono, String divi, String saleman, String status)
	    throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT ROW_NUMBER() OVER(ORDER BY THORNO) AS ID,THCONO,THDIVI,THORNO,TRIM(THORNO) || ' : ' || TRIM(OKCUNM) AS ORDERNUMBER \n"
			+ "FROM BRLDTA0100.M3_TAKEORDERHEAD a, M3FDBPRD.OCUSMA b \n" 
			+ "WHERE THCONO = '" + cono + "' \n" 
			+ "AND THDIVI = '" + divi + "' \n" 
			+ "AND THSTAS = '" + status + "' \n"
			+ "AND b.OKCONO = THCONO \n" 
			+ "AND b.OKCUNO = THCUNO \n" 
			+ "AND b.OKSTAT = '20' \n"
			+ "AND THSANO = '" + saleman + "' \n" 
			+ "ORDER BY THORNO DESC";
		System.out.println("getOrderNumber\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getOrderNumberSupport(String cono, String divi, String salesup, String status, String auth)
	    throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		ResultSet mRes = null;
		if (auth.equals("1")) {
		    String query = "SELECT ROW_NUMBER() OVER(ORDER BY THORNO) AS ID,THCONO,THDIVI,THORNO,TRIM(THORNO) || ' : ' || TRIM(OKCUNM) AS ORDERNUMBER \n"
			    + "FROM BRLDTA0100.M3_TAKEORDERHEAD a, M3FDBPRD.OCUSMA b \n" 
			    + "WHERE THCONO = '" + cono + "' \n" 
			    + "AND THDIVI = '" + divi + "' \n" 
			    + "AND THSTAS = '" + status + "' \n"
			    + "AND b.OKCONO = THCONO \n" 
			    + "AND b.OKCUNO = THCUNO \n" 
			    + "AND b.OKSTAT = '20' \n"
			    + "ORDER BY THORNO DESC";
		    System.out.println("getOrderNumber\n" + query);
		    mRes = stmt.executeQuery(query);

		} else {
		    String query = "SELECT ROW_NUMBER() OVER(ORDER BY THORNO) AS ID,THCONO,THDIVI,THORNO,TRIM(THORNO) || ' : ' || TRIM(OKCUNM) AS ORDERNUMBER \n"
			    + "FROM BRLDTA0100.M3_TAKEORDERHEAD a, M3FDBPRD.OCUSMA b \n" 
			    + "WHERE THCONO = '" + cono + "' \n" 
			    + "AND THDIVI = '" + divi + "' \n" 
			    + "AND THSTAS = '" + status + "' \n"
			    + "AND b.OKCONO = THCONO \n" 
			    + "AND b.OKCUNO = THCUNO \n" 
			    + "AND b.OKSTAT = '20' \n"
			    + "AND THSASU = '" + salesup + "'\n"
			    + "ORDER BY THORNO DESC";
		    System.out.println("getOrderNumber\n" + query);
		    mRes = stmt.executeQuery(query);
		}

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getOrderHead(String cono, String divi, String orderno) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT THCONO, THDIVI, THORNO, THORTY, THWHLO, THCOCE, CHAR(THORDA, ISO) AS THORDA, CHAR(THDEDA, ISO) AS THDEDA, THCUNO, TRIM(OKCUNM) AS OKCUNM, TRIM(THCUNO) || ' : ' || TRIM(OKCUNM) AS CUSTOMER, CASE WHEN OKCUNM LIKE '%�Թʴ%' THEN 'YES' ELSE 'NO' END AS DCASH, THROUN, THPRIC, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, THSTAS, THENDA, THENTI \n"
			+ "FROM BRLDTA0100.M3_TAKEORDERHEAD a, M3FDBPRD.OCUSMA b \n" 
			+ "WHERE THCONO = '" + cono + "' \n" 
			+ "AND THDIVI = '" + divi + "' \n" 
			+ "AND b.OKCONO = THCONO \n"
			+ "AND b.OKCUNO = THCUNO \n" 
			+ "AND b.OKSTAT = '20' \n" 
			+ "AND THORNO = '" + orderno + "'";
		System.out.println("getOrderHead\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getOrderDetail(String cono, String divi, String orderno) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, MMITDS, CAST(TDIQTY AS DECIMAL(15,2)) AS TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI \n"
			+ "FROM BRLDTA0100.M3_TAKEORDERDETAIL a, M3FDBPRD.MITMAS b \n" 
			+ "WHERE TDCONO = '" + cono + "' \n" 
			+ "AND TDDIVI = '" + divi + "' \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND b.MMCONO = TDCONO \n" 
			+ "AND b.MMITNO = TDITNO \n"
			+ "ORDER BY TDORNO, TDLINE";
//			System.out.println("getOrderDetail\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getOrderDetailSupport(String cono, String divi, String orderno) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, MMITDS, CAST(TDIQTY AS DECIMAL(15,2)) AS TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI \n"
			+ "FROM BRLDTA0100.M3_TAKEORDERDETAILSUPPORT a, M3FDBPRD.MITMAS b \n" 
			+ "WHERE TDCONO = '" + cono + "' \n" 
			+ "AND TDDIVI = '" + divi + "' \n" 
			+ "AND TDORNO = '" + orderno + "' \n"
			+ "AND TDSTAS = '10' \n"
			+ "AND b.MMCONO = TDCONO \n" 
			+ "AND b.MMITNO = TDITNO \n"
			+ "ORDER BY TDORNO, TDLINE";
//			System.out.println("getOrderDetail\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    
    public static String getMonitoringHead(String cono, String divi, String saleman) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT THCONO, THDIVI, THORNO, COALESCE(TDCONU, '') AS TDCONU, COALESCE(CAST('00' || UAIVNO AS VARCHAR(10)), '') AS UAIVNO, THORTY, THWHLO, THCOCE, THORDA, CHAR(THDEDA, ISO) AS THDEDA, THCUNO, THROUN, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, CASE WHEN UAIVNO IS NULL THEN THSTAS ELSE '97' END AS THSTAS, THENDA, THENTI \n" + 
			"FROM \n" + 
			"(SELECT THCONO, THDIVI, THORNO, THORTY, THWHLO, THCOCE, THORDA, THDEDA, THCUNO, THROUN, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, THSTAS, THENDA, THENTI \n" + 
			"FROM BRLDTA0100.M3_TAKEORDERHEAD \n" + 
			"WHERE THCONO = '"+cono+"' \n" + 
			"AND THDIVI = '"+divi+"' \n" +
			"AND THSANO = '"+saleman+"' \n"+
			"--AND THORNO = '0009665691' \n" + 
			") a \n" + 
			"LEFT JOIN \n" + 
			"(SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO \n" + 
			"FROM BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
			"GROUP BY TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO) AS b \n" + 
			"ON b.TDCONO = THCONO \n" + 
			"AND b.TDDIVI = THDIVI \n" + 
			"AND b.TDORNO = THORNO \n" + 
			"LEFT JOIN \n" + 
			"(SELECT UACONO, UADIVI, UAORNO, UAIVNO, UAYEA4 \n" + 
			"FROM M3FDBPRD.ODHEAD \n" + 
			"GROUP BY UACONO, UADIVI, UAORNO, UAIVNO, UAYEA4) AS c \n" + 
			"ON c.UACONO = THCONO \n" + 
			"AND c.UADIVI = THDIVI \n" + 
			"AND c.UAORNO = TDCONU \n" + 
			"AND c.UAYEA4 = YEAR(THDEDA) \n" + 
			"ORDER BY THCONO, THDIVI, THORNO DESC";
//			System.out.println("getMonitorHead\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getMonitoringDetail(String cono, String divi, String orderno, String conumber, String invnumber) throws Exception {

   	Connection conn = ConnectDB2.doConnect();
   	try {
   	    if (conn != null) {

   		Statement stmt = conn.createStatement();

   		String query = "SELECT ROW_NUMBER() OVER() AS LINE, TTITNO, MMFUDS, MMUNMS AS UNIT, TAKENO_QTY, SUPPNO_QTY, CONO_QTY, INVNO_QTY, DIFF \n" + 
   			"FROM  \n" + 
   			"(SELECT NO, TTCONO, TTDIVI, TTORNO, TTITNO, MMFUDS, CASE WHEN TDUNIT IS NOT NULL THEN TDUNIT ELSE MMUNMS END AS MMUNMS, COALESCE(TDIQTY,0) AS TAKENO_QTY, COALESCE(TSIQTY,0) AS SUPPNO_QTY, COALESCE(OBIVQA,0) AS CONO_QTY, COALESCE(UBIVQA,0) AS INVNO_QTY, COALESCE(TDIQTY,0) - COALESCE(UBIVQA,0) AS DIFF \n" + 
   			"FROM  \n" + 
   			"( \n" + 
   			"SELECT 1 AS NO, TDCONO AS TTCONO, TDDIVI AS TTDIVI, TDORNO AS TTORNO, TRIM(TDITNO) AS TTITNO \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERDETAIL  \n" + 
   			"WHERE TDCONO = '"+cono+"' \n" + 
   			"AND TDDIVI = '"+divi+"' \n" + 
   			"AND TDORNO = '"+orderno+"' \n" + 
   			"AND TDSTAS = '10' \n" + 
   			"UNION ALL \n" + 
   			"SELECT 2 AS NO, TDCONO AS TTCONO, TDDIVI AS TTDIVI, TDORNO AS TTORNO, TRIM(TDITNO) AS TTITNO \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERDETAILSUPPORT  \n" + 
   			"WHERE TDCONO = '"+cono+"' \n" + 
   			"AND TDDIVI = '"+divi+"' \n" + 
   			"AND TDORNO = '"+orderno+"' \n" + 
   			"UNION ALL \n" + 
   			"SELECT 3 AS NO, OBCONO AS TTCONO, OBDIVI AS TTDIVI, OBORNO AS TTORNO, TRIM(OBITNO) AS TTITNO \n" + 
   			"FROM M3FDBPRD.OOLINE \n" + 
   			"WHERE OBCONO = '"+cono+"' \n" + 
   			"AND OBDIVI = '"+divi+"' \n" + 
   			"AND OBORNO = '"+conumber+"' \n" + 
   			"UNION ALL \n" + 
   			"SELECT 4 AS NO, UBCONO AS TTCONO, UBDIVI AS TTDIVI, COALESCE(CAST('00' || UBIVNO AS VARCHAR(10)), '') AS TTORNO, TRIM(UBITNO) AS TTITNO \n" + 
   			"FROM M3FDBPRD.ODLINE \n" + 
   			"WHERE UBCONO = '"+cono+"' \n" + 
   			"AND UBDIVI = '"+divi+"' \n" + 
   			"AND UBYEA4 = (SELECT YEAR(THDEDA) AS YEAR \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERHEAD \n" + 
   			"WHERE THCONO = '"+cono+"' \n" + 
   			"AND THDIVI = '"+divi+"' \n" + 
   			"AND THORNO = '"+orderno+"') \n" + 
   			"AND UBIVNO = '"+invnumber+"' \n" + 
   			") AS a \n" + 
   			"LEFT JOIN  \n" + 
   			"(SELECT TDCONO, TDDIVI, TDORNO, TDITNO, TDIQTY, TDUNIT \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERDETAIL  \n" + 
   			"WHERE TDCONO = '"+cono+"' \n" + 
   			"AND TDDIVI = '"+divi+"' \n" + 
   			"AND TDORNO = '"+orderno+"' \n" + 
   			"AND TDSTAS = '10' \n" + 
   			"ORDER BY TDLINE) AS b \n" + 
   			"ON b.TDCONO = a.TTCONO \n" + 
   			"AND b.TDDIVI = a.TTDIVI \n" + 
   			"--AND b.TDORNO = a.TTORNO \n" + 
   			"AND b.TDITNO = a.TTITNO \n" + 
   			"LEFT JOIN  \n" + 
   			"(SELECT TDCONO AS TSCONO, TDDIVI AS TSDIVI, TDORNO AS TSORNO, TDITNO AS TSITNO, TDIQTY AS TSIQTY, TDUNIT AS TSUNIT \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERDETAILSUPPORT \n" + 
   			"WHERE TDCONO = '"+cono+"' \n" + 
   			"AND TDDIVI = '"+divi+"' \n" + 
   			"AND TDORNO = '"+orderno+"' \n" + 
   			"ORDER BY TDLINE) AS c \n" + 
   			"ON c.TSCONO = a.TTCONO \n" + 
   			"AND c.TSDIVI = a.TTDIVI \n" + 
   			"--AND c.TSORNO = a.TTORNO \n" + 
   			"AND c.TSITNO = a.TTITNO \n" + 
   			"LEFT JOIN  \n" + 
   			"(SELECT OBCONO, OBDIVI, OBORNO, OBITNO, OBTEDS, OBIVQA * 1 AS OBIVQA \n" + 
   			"FROM M3FDBPRD.OOLINE \n" + 
   			"WHERE OBCONO = '"+cono+"' \n" + 
   			"AND OBDIVI = '"+divi+"' \n" + 
   			"AND OBORNO = '"+conumber+"') AS d \n" + 
   			"ON d.OBCONO = a.TTCONO \n" + 
   			"AND d.OBDIVI = a.TTDIVI \n" + 
   			"--AND d.OBORNO = a.TTORNO \n" + 
   			"AND d.OBITNO = a.TTITNO \n" + 
   			"LEFT JOIN  \n" + 
   			"(SELECT UBCONO, UBDIVI, UBIVNO, UBITNO, UBIVQA * 1 AS UBIVQA \n" + 
   			"FROM M3FDBPRD.ODLINE \n" + 
   			"WHERE UBCONO = '"+cono+"' \n" + 
   			"AND UBDIVI = '"+divi+"' \n" + 
   			"AND UBYEA4 = (SELECT YEAR(THDEDA) AS YEAR \n" + 
   			"FROM BRLDTA0100.M3_TAKEORDERHEAD \n" + 
   			"WHERE THCONO = '"+cono+"' \n" + 
   			"AND THDIVI = '"+divi+"' \n" + 
   			"AND THORNO = '"+orderno+"') \n" + 
   			"AND UBIVNO = '"+invnumber+"') AS e \n" + 
   			"ON e.UBCONO = a.TTCONO \n" + 
   			"AND e.UBDIVI = a.TTDIVI \n" + 
   			"--AND e.UBIVNO = a.TTORNO \n" + 
   			"AND e.UBITNO = a.TTITNO \n" + 
   			"LEFT JOIN  \n" + 
   			"(SELECT MMCONO, MMITNO, TRIM(MMFUDS) AS MMFUDS, CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMUNMS \n" + 
   			"FROM M3FDBPRD.MITMAS) AS f \n" + 
   			"ON f.MMCONO = a.TTCONO \n" + 
   			"AND f.MMITNO = a.TTITNO) AS aa \n" + 
   			"GROUP BY TTITNO, MMFUDS, TAKENO_QTY, SUPPNO_QTY, CONO_QTY, INVNO_QTY, MMUNMS, DIFF";
   			System.out.println("getMonitoringDetailHistory\n" + query);
   		ResultSet mRes = stmt.executeQuery(query);

   		return ConvertResultSet.convertResultSetToJson(mRes);

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

    
    public static String getItem(String cono, String divi, String whs) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT MMCONO, TRIM(MMITNO) AS MMITNO, TRIM(MMITDS) AS MMITDS, TRIM(MMFUDS) AS MMFUDS, TRIM(MMITNO) || ' : ' || TRIM(MMFUDS) AS ITEM, CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMUNMS, MMPUPR, MMVTCP, TRIM(MMCUCD) AS MMCUCD, TRIM(MBORTY) AS MBORTY \n"
			+ "FROM M3FDBPRD.MITMAS a, M3FDBPRD.MITBAL b \n" 
			+ "WHERE a.MMCONO = '" + cono + "' \n"
			+ "AND a.MMSTAT = '20' \n" 
			+ "AND a.MMITTY = 'FG' \n"
			+ "AND SUBSTRING(a.MMITNO,0,2) BETWEEN 'A' AND 'Z' \n" 
			+ "AND b.MBCONO = a.MMCONO \n"
			+ "AND b.MBITNO = a.MMITNO \n" 
			+ "AND b.MBWHLO = '" + whs + "' ";
		// System.out.println("getItem\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getItemUnit(String cono, String divi, String item) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();
		String query = "SELECT ROW_NUMBER() OVER(ORDER BY MMUNMS) AS ID, MMUNMS \n" + "FROM \n"
			+ "(SELECT MMUNMS \n" + "FROM M3FDBPRD.MITMAS \n" + "WHERE MMCONO = '" + cono + "' \n"
			+ "AND MMITNO = '" + item + "' \n" + "UNION ALL \n" + "SELECT MUALUN AS MMUNMS \n"
			+ "FROM M3FDBPRD.MITAUN \n" + "WHERE MUCONO = '" + cono + "' \n" + "AND MUITNO = '" + item
			+ "' \n" + "GROUP BY MUALUN) AS A";
		// System.out.println("getItemUnit\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getMaxOrderNumber(String cono, String divi, String year) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT CASE WHEN MAX(THORNO) > 0 THEN MAX(THORNO) + 1 ELSE 0 END AS THORNO \n"
			+ "FROM BRLDTA0100.M3_TAKEORDERHEAD mp \n" + "WHERE THCONO = '" + cono + "' \n"
			+ "AND THDIVI = '" + divi + "' \n" + "AND SUBSTRING(THORNO,0,3) = '" + year + "'";
		System.out.println("getMaxOrderNumber\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {
		    return mRes.getString("THORNO").trim();
		}

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

    public static String getSaleSuportNo(String cono, String divi, String saleman) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT S_CONO, S_SUPP, S_SALE, S_CHAN, SP_USER \n"
			+ "FROM BRLDTA0100.SAL_SUPSALE a, BRLDTA0100.SAL_SUPPORT b \n" 
			+ "WHERE S_CONO = '" + cono + "' \n" 
			+ "AND S_SALE = '" + saleman + "' \n" 
			+ "AND b.SP_CONO = S_CONO \n"
			+ "AND b.SP_ID = S_SUPP";
		System.out.println("getSaleSuportNo\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		while (mRes.next()) {
		    return mRes.getString("S_SUPP").trim();
		}

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

    public static String getSalemanNo(String cono, String divi) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT ROW_NUMBER() OVER(ORDER BY JUUSID) AS ID, TRIM(JUUSID) AS S_ID, TRIM(JUTX40) AS S_NAME, TRIM(JUUSID) || ' : ' || TRIM(JUTX40) AS SALEMAN \n"
			+ "FROM M3FDBPRD.CMNUSR \n" + "WHERE JULSID != '' \n" + "AND JUFRF7 = 'SALEMAN' \n"
			+ "AND JUUSTA = '20'";
//			System.out.println("getSalemanNo\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getUser(String cono, String divi) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT JUUSID, JULSID, JUTX40, TRIM(JUFRF6) AS JUFRF6, JUFRF7, CAST(JUFRF8 AS DECIMAL(2,0)) AS JUFRF8 \n"
			+ "FROM M3FDBPRD.CMNUSR \n" + "WHERE JULSID != '' \n" + "AND JUFRF6 != '' \n"
			+ "AND JUFRF7 != '' \n" + "AND JUUSTA = '20' \n" + "ORDER BY JUFRF7 DESC, JUFRF6 ";
//			System.out.println("getUserManagement\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static String getGroupingUser(String cono, String divi, String username) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT JUUSID, JULSID, JUTX40, TRIM(JUFRF6) AS JUFRF6, JUFRF7, JUFRF8, S_ID, S_NAME \n"
			+ "FROM \n" + "(SELECT JUUSID, JULSID, JUTX40, JUFRF6, JUFRF7, JUFRF8, S_SALE \n"
			+ "FROM M3FDBPRD.CMNUSR a, BRLDTA0100.SAL_SUPSALE b \n" + "WHERE JULSID != '' \n"
			+ "AND JUFRF6 = '" + username + "' \n" + "AND JUFRF7 = 'SALESUP' \n" + "AND JUUSTA = '20' \n"
			+ "AND b.S_SUPP = JUFRF6) AS a \n" + "LEFT JOIN \n"
			+ "(SELECT JUUSID AS S_ID, JUTX40 AS S_NAME \n" + "FROM M3FDBPRD.CMNUSR \n"
			+ "WHERE JULSID != '' \n" + "AND JUFRF7 = 'SALEMAN' \n" + "AND JUUSTA = '20' ) AS b \n"
			+ "ON b.S_ID = a.S_SALE \n" + "ORDER BY JUFRF6, S_ID";
//			System.out.println("getGroupingUser\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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
    
    public static String getPriceList(String cono, String divi) throws Exception {

	Connection conn = ConnectDB2.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "SELECT ROW_NUMBER() OVER(ORDER BY OJPRRF) AS ID, OJPRRF \n" + 
			"FROM M3FDBPRD.OPRICH \n" + 
			"WHERE OJCONO = 10 \n" + 
			"--AND OJCUNO = 'TH01010477' \n" + 
			"AND OJPRRF != 'E1' \n" + 
			"AND OJPRRF != '' \n" + 
			"GROUP BY OJPRRF \n" + 
			"ORDER BY OJPRRF";
//			System.out.println("getGroupingUser\n" + query);
		ResultSet mRes = stmt.executeQuery(query);

		return ConvertResultSet.convertResultSetToJson(mRes);

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

    public static List<String> getRSOrderDatail(String cono, String divi, String orderno) {

	List<String> getList = new ArrayList<String>();
	Connection conn = null;
	try {
	    conn = ConnectDB2.doConnect();
	    Statement stmt = conn.createStatement();
	    String query = "SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, MMITDS, CAST(TDIQTY AS DECIMAL(15,2)) AS TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI  \n"
		    + "FROM BRLDTA0100.M3_TAKEORDERDETAIL a, M3FDBPRD.MITMAS b \n" 
		    + "WHERE TDCONO = '" + cono + "' \n"
		    + "AND TDDIVI = '" + divi + "' \n" 
		    + "AND TDORNO = '" + orderno + "' \n"
		    + "AND TDSTAS = '10' \n"
		    + "AND b.MMCONO = TDCONO \n" 
		    + "AND b.MMITNO = TDITNO";
	    System.out.println("getRSPRDetailGenPO\n" + query);
	    ResultSet mRes = stmt.executeQuery(query);

	    while (mRes.next()) {
		getList.add(mRes.getString("TDORNO").trim() + " ; " + mRes.getString("TDCONU").trim() + " ; "
			+ mRes.getString("TDIVNO").trim() + " ; " + mRes.getString("TDLINE").trim() + " ; "
			+ mRes.getString("TDITNO").trim() + " ; " + mRes.getString("MMITDS").trim() + " ; "
			+ mRes.getString("TDIQTY").trim() + " ; " + mRes.getString("TDUNIT").trim() + " ; "
			+ mRes.getString("TDREM1").trim() + " ; " + mRes.getString("TDREM2").trim() + " ; "
			+ mRes.getString("TDSTAS").trim() + " ; " + mRes.getString("TDENDA").trim() + " ; "
			+ mRes.getString("TDENTI").trim());
	    }
	    return getList;

	} catch (Exception ex) {
	    System.out.println(ex.toString());
	} finally {
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException ex) {
		    System.out.println(ex.toString());
		}
	    }
	}
	return null;
    }
  
    
    public static String getRental() throws Exception {

	
	JSONArray mJSonArr = new JSONArray();
	Connection conn = ConnectMSSql.doConnect();
	try {
	    if (conn != null) {

		Statement stmt = conn.createStatement();

		String query = "use BRLAS400 \n"
			+ "select * from T_RENTAL \n"
			+ "where R_CONO = '10' \n";
//			+ "and R_WHLO = 'A01'";
//			System.out.println("getGroupingUser\n" + query);
		ResultSet mRes = stmt.executeQuery(query);
		
		ResultSetMetaData metadata = mRes.getMetaData();
		int numColumns = metadata.getColumnCount();
		while (mRes.next()) {
//		    System.out.println(mRes.getString(1));
			JSONObject mJsonObj = new JSONObject();
			for (int i = 1; i <= numColumns; ++i) {
			    
			    
				try {
					String column_name = metadata.getColumnName(i);
					String row_data = mRes.getString(column_name).trim();
					
					
//					mJsonObj.put(column_name, rsset.getObject(column_name));
					mJsonObj.put(column_name, row_data);
					
					
					if(row_data.equals("A01")) {
					    mJsonObj.put("TEST", "AAAAA");
					}
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
				
				
			}
			System.out.println(mJsonObj);
			mJSonArr.put(mJsonObj);
		}	
		
		
		
		return mJSonArr.toString();
//		return ConvertResultSet.convertResultSetToJson(mRes);

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
    
    
    public static List<String> getRSRental() {

	List<String> getList = new ArrayList<String>();
	Connection conn = null;
	try {
	    conn = ConnectMSSql.doConnect();
	    Statement stmt = conn.createStatement();
	    String query = "SELECT 15 as day, '2021/12/01' as recdate, '2022/01/31' as enddate, DATEDIFF(day, '2021/12/01', '2022/01/31') AS diff";
	    System.out.println("getRSRental\n" + query);
	    ResultSet mRes = stmt.executeQuery(query);

	    while (mRes.next()) {
		getList.add(mRes.getString("TDITNO").trim());
	    }
	    return getList;

	} catch (Exception ex) {
	    System.out.println(ex.toString());
	} finally {
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException ex) {
		    System.out.println(ex.toString());
		}
	    }
	}
	return null;
    }
    
    
}
