package com.br.utility;

import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.br.data.SelectData;
import com.br.data.UpdateData;
import com.sun.mail.imap.Utility;

import MForms.Utils.MNEHelper;
import MForms.Utils.MNEProtocol;
import MvxAPI.MvxSockJ;

public class CallMForm {

//    protected static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:21008/mne/servlet/MvxMCSvt"; // PRD
//    protected static int appPort = 16105; // PRD
    protected static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:22008/mne/servlet/MvxMCSvt"; // TST
    protected static int appPort = 16305; // TST
    protected static String appServer = "192.200.9.190";
//    protected static String m3Username = "PUR_1A1";
//    protected static String m3Password = "lawson@90";
    protected static MvxSockJ sock;
//    protected static int cono = 10;
//    protected static String divi = "101";
    static DecimalFormat df = new DecimalFormat("#");
    static DecimalFormat df2 = new DecimalFormat("#.##");
    static DecimalFormat df3 = new DecimalFormat("#.###");
    static DecimalFormat df4 = new DecimalFormat("#.####");

    public static String connectM3(String cono, String divi) {
	System.out.println("ConnectM3.");
	JSONObject mJsonObj = new JSONObject();

	boolean conn = false;

	String getUsernamePasswordM3[];
	try {
	    getUsernamePasswordM3 = SelectData.getUserM3(cono, divi, "PUR").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String getUsernameM3 = "WATTAN_SAE";
	String getPasswordM3 = "1234";

	MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
//	    try {
	try {
	    mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);
	    System.out.println(mne.getMsg());

	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(conn);

//		mJsonObj.put("result", "ok");
//		mJsonObj.put("message", "Login complete.");
//	    } catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		mJsonObj.put("result", "nok");
//		mJsonObj.put("message", e);
//	    }

	return mJsonObj.toString();

    }

    public static String connectM3_bac220621(String cono, String divi) throws JSONException {
	System.out.println("ConnectM3.");
	JSONObject mJsonObj = new JSONObject();

	boolean conn;
	try {

	    String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "PUR").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	    MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
	    conn = mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

	    if (conn == true) {
		System.out.println("Login complete.");
		mJsonObj.put("result", "ok");
		mJsonObj.put("message", "Login complete.");

	    } else {
		System.out.println("Can not login to M3 system.");
	    }

	    return mJsonObj.toString();

	} catch (Exception e) {
	    System.out.println(e);
	    mJsonObj.put("result", "nok");
	    mJsonObj.put("message", e);
	}

	return mJsonObj.toString();

    }

    public static String callMMS100(String cono, String divi, String date, String cost, String ref1, String ref2,
	    String ref3, String qty, String unit) {

	try {

	    String checked = "YES";
	    String txtResult = "";

	    try {

		String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "PUR").split(";");
		String getUsernameM3 = getUsernamePasswordM3[0].trim();
		String getPasswordM3 = getUsernamePasswordM3[1].trim();

		MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
		mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

		if (!mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3)) {
		    System.out.println(" Can not login to M3 system");
		}

		String MMS100ID = mne.runM3Pgm("MMS100");
		System.out.println("MMS100ID:" + MMS100ID);

		if ((MMS100ID).equals("")) {
		    System.out.println(" ไม่สามารถเปิดโปรแกรม MMS100 ได้");
		}

		int i = 0;
		while (!mne.panel.equals("MMS100/B1")) {
		    i++;
		    if (i >= 3) {
			mne.closeProgram(MMS100ID);
			System.exit(0);
		    }

		    mne.pressKey(MNEProtocol.KeyF13);
		    mne.setField("WWSPIC", "B-Browse");
		    mne.setField("WWDSEQ", "E12");
		    mne.pressKey(MNEProtocol.KeyEnter);
		    mne.pressKey(MNEProtocol.KeyF03);
		    mne.runM3Pgm("MMS100");

		}

		if (mne.panel.equals("MMS100/B1")) {
		    System.out.println(mne.panel);
		    System.out.println("Order type : " + ref2);
		    mne.setField("WWFACI", "1A1");
		    mne.setField("W1TRTP", ref2);
		    mne.setField("_PSEQ", "E1");
		    mne.selectOption("1");
		    System.out.println(mne.getMsg());

		    // ========= Begin order Type A71 ==================
		    if (mne.panel.equals("MMS100/E")) {
			System.out.println(mne.panel);
			System.out.println("Whs, Date, Costcenter : " + ref1 + " : " + " : " + date + " : " + cost);
			mne.setField("WGWHLO", ref1.trim());
			mne.setField("WGTRDT", date.trim());
			mne.setField("WGDEPT", cost.trim());
			mne.pressKey(MNEProtocol.KeyEnter);
			System.out.println(mne.getMsg());

			if (mne.getMsg() != null) {
			    String error = mne.getMsg();
			    mne.pressKey(MNEProtocol.KeyF03);
			    mne.closeProgram(MMS100ID);
			    return error;
			}

			int b = 0;
			while ((mne.panel.equals("MMS100/F")) && (b <= 3)) {
			    b++;
			    System.out.println(mne.panel);
			    mne.pressKey(MNEProtocol.KeyEnter);
			    System.out.println(mne.getMsg());
			}

			if (b > 3) {
			    mne.closeProgram(MMS100ID);
			    System.exit(0);
			}

			if (mne.panel.equals("User-defined Text")) {
			    System.out.println(mne.panel);
			    mne.pressKey(MNEProtocol.KeyEnter);
			    System.out.println(mne.getMsg());
			}

			// -------------------------------------------------------
			if (mne.panel.equals("MMS101/B1")) {
			    System.out.println(mne.panel);
			    System.out.println("item, qty, unit : " + ref3 + " : " + " : " + qty + " : " + unit);
			    float iQty = Float.valueOf(qty);
			    List<String> getListQtyInvenLot = null;// SelectDB2.RSInvenLot(cono, divi, ref1, ref3);
			    for (int ii = 0; ii < getListQtyInvenLot.size(); ii++) {

				if (iQty != 0) {
				    String[] CheckQtyInvenLot = getListQtyInvenLot.get(ii).split(" ; ");
				    String vLot = CheckQtyInvenLot[1].trim();
				    float vQty = Float.valueOf(CheckQtyInvenLot[2]);
				    System.out.println(vQty + " : " + vLot);

				    // if (iQty <= vQty) {
				    // System.out.println("Issue iQty : " +
				    // iQty);
				    // iQty -= iQty;
				    //
				    // } else {
				    // System.out.println("Issue vQty : " +
				    // vQty);
				    // iQty -= vQty;
				    //
				    // }
				    if (iQty <= vQty) {
					System.out.println("Issue iQty : " + iQty);
					mne.setField("WRITNO", ref3);
					mne.setField("WRTRQT", iQty);
					mne.setField("WRALUN", unit);
					txtResult = "Order No : " + mne.getFieldMap("MRTRNR").getValue();
					System.out.println("Order No : " + txtResult);
					mne.pressKey(MNEProtocol.KeyEnter);
					System.out.println(mne.getMsg());

					mne.setField("WRBANO", vLot);
					mne.pressKey(MNEProtocol.KeyEnter);
					iQty -= iQty;

				    } else {
					System.out.println("Issue vQty : " + vQty);
					mne.setField("WRITNO", ref3);
					mne.setField("WRTRQT", vQty);
					mne.setField("WRALUN", unit);
					mne.setField("WRBANO", vLot);
					txtResult = "Order No : " + mne.getFieldMap("MRTRNR").getValue();
					System.out.println("Order No : " + txtResult);
					mne.pressKey(MNEProtocol.KeyEnter);
					System.out.println(mne.getMsg());

					mne.setField("WRBANO", vLot);
					mne.pressKey(MNEProtocol.KeyEnter);
					iQty -= vQty;

				    }

				    // int d = 0;
				    // while ((!mne.panel.equals("MMS101/E")) &&
				    // (d <= 3)) {
				    // mne.pressKey(MNEProtocol.KeyEnter);
				    // System.out.println(mne.getMsg());
				    // d++;
				    // }
				    //
				    // if (mne.panel.equals("MMS101/E")) {
				    // mne.setField("FCS", "WRDEPT"); //FCS
				    // WRDEPT
				    // mne.setField("WRDEPT", cost.trim());
				    // //WRDEPT SDSAASDS
				    // mne.pressKey(MNEProtocol.KeyEnter);
				    // System.out.println(mne.getMsg());
				    // }
				}

			    }

			    if (iQty == 0) {
				System.out.println("iQty = 0 issue complete");
				checked = "YES";
				// txtResult = "Order No 000000000001";
			    } else {
				System.out.println("Item : " + ref3 + " not enough");
				checked = "NO";
			    }

			    if (mne.getMsg() == null) {
				checked = "YES";
			    } else {
				checked = "NO";
			    }
			    System.out.println("Status : " + checked);
			}

			int c = 0;
			while ((mne.panel.equals("MMS101/B1")) && (c <= 3)) {
			    c++;
			    mne.pressKey(MNEProtocol.KeyF03);
			}

			if (c <= 3) {
			    if ("YES".equals(checked)) {
				System.out.println("Post Data Completed.");
			    }
			    mne.closeProgram(MMS100ID);
			}

		    }

		    // ===== End Order Type A71 =========================
		    mne.closeProgram(MMS100ID);

		}

		// ---------------------------------------------------------
		mne.pressKey(MNEProtocol.KeyF03);
		mne.pressKey(MNEProtocol.KeyF03);
		mne.pressKey(MNEProtocol.KeyF03);
		mne.closeProgram(MMS100ID);
		System.out.println("mne.closeProgram(MMS100ID)");

		return txtResult;

	    } catch (Exception e) {
		if (sock != null) {
		    System.out.println("ERR: " + sock.mvxGetLastError());
		}
	    }

	} catch (Exception ex) {
	    Logger.getLogger(CallMForm.class.getName()).log(Level.SEVERE, null, ex);
	}

	return null;
    }

    public static String callOIS300(String cono, String divi, String orderno, String whs, String customerno,
	    String ordertype, String delidate, String status, String userid) {

	try {

	    JSONObject mJsonObj = new JSONObject();
	    String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "COM").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	    MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
	    mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

	    if (!mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3)) {
		System.out.println(" Can not login to M3 system");
	    }

	    String OIS300ID = mne.runM3Pgm("OIS300");
	    System.out.println("OIS300ID:" + OIS300ID);

	    if ((OIS300ID).equals("")) {
		System.out.println(" ไม่สามารถเปิดโปรแกรม OIS300 ได้");
	    }

	    if (mne.panel.equals("OIS300/B")) {
		System.out.println("OIS300/B");
		mne.setField("FCS", "W1OBKV");
		mne.setField("WWQTTP", "1");
		mne.setField("WWFACI", "1A1");
		mne.setField("_PSEQ", "EFGH");
		mne.pressKey("F14");

		if (mne.panel.equals("OIS100/A")) {
		    System.out.println("OIS100/A");
		    mne.setField("OACUNO", customerno);
		    mne.setField("OAORTP", ordertype);
		    mne.setField("WWMSEQ", "EG");
		    mne.setField("WARLDZ", delidate);
		    mne.setField("OAFACI", "1A1");
		    mne.pressKey(MNEProtocol.KeyEnter);
		    System.out.println(mne.getMsg());

//		    if (mne.getMsg() != null) {
//			mJsonObj.put("result", "nok");
//			mJsonObj.put("message", mne.getMsg());
//			return mJsonObj.toString();
//		    }

		    int a = 0;
		    while (((mne.panel.equals("OIS100/A")) && (a <= 5))) {
			mne.setField("WWMSEQ", "HGE5");
			mne.setField("FCS", "OAORTP");
			mne.setField("OACUNO", customerno);
			mne.setField("OAORTP", ordertype);
			mne.setField("WWMSEQ", "E5");
			mne.setField("WARLDZ", delidate);
			mne.setField("OAFACI", "1A1");
			mne.pressKey(MNEProtocol.KeyEnter);
			System.out.println(mne.getMsg());
			a++;

//			if (mne.getMsg() != null) {
//			    mJsonObj.put("result", "nok");
//			    mJsonObj.put("message", mne.getMsg());
//			    return mJsonObj.toString();
//			}

		    }

		    if (mne.panel.equals("OIS100/E")) {
			System.out.println("OIS100/E");
			mne.setField("WAORDT", delidate);
			mne.setField("WACUDT", delidate);
			mne.setField("OAWHLO", whs);
			mne.pressKey(MNEProtocol.KeyEnter);
			System.out.println(mne.getMsg());

			if (mne.panel.equals("OIS100/H")) {
			    System.out.println("OIS100/H");
//				mne.setField("OAPLTB", pricelist);// A2 หน้าร้าน
			    mne.setField("OAORSL", "10");// Status
//				mne.setField("OADISY", txtDiscNew.getText());// OADISY SH
			    mne.pressKey(MNEProtocol.KeyEnter);
			    System.out.println(mne.getMsg());

			    if (mne.getMsg() != null) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", mne.getMsg());
				return mJsonObj.toString();
			    }

			}

			if (mne.panel.equals("OIS100/G")) {
			    System.out.println("OIS100/G");
			    mne.setField("FCS", "OAOREF");// FCS OAOREF
			    mne.setField("OAOREF", "-");// OAOREF น้ำมันใช้แล้ว น้ำหนัก 900 KG.
			    mne.pressKey(MNEProtocol.KeyEnter);
			    System.out.println(mne.getMsg());

			    if (mne.getMsg() != null) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", mne.getMsg());
				return mJsonObj.toString();
			    }

			}

			System.out.println("OIS300 :" + mne.panel);

			// Add item line
			if (mne.panel.equals("OIS101/B1")) {
			    System.out.println("OIS101/B1");
			    mne.setField("OAINRC", customerno);
			    mne.setField("WALDED", delidate);

			    String getCONumber = mne.getFieldMap("OAORNO").getValue();
			    System.out.println("CO : " + getCONumber);

//			    txtCO.setText(mne.getFieldMap("OAORNO").getValue());

			    List<String> getListOrderDetail = SelectData.getRSOrderDatail(cono, divi, orderno);

			    for (int i = 0; i < getListOrderDetail.size(); i++) {
				String[] getDataOrderDetail = getListOrderDetail.get(i).split(";");
				System.out.println(getListOrderDetail.get(i));

				String xITEM = getDataOrderDetail[4].trim(); // model.getValueAt(i,0).toString();
				String xQTY = getDataOrderDetail[6].trim(); // model.getValueAt(i, 6).toString();
				String xUNIT = getDataOrderDetail[7].trim(); // .getValueAt(i, 7).toString();
//				String xPRICE = getDataOrderDetail[4].trim(); // model.getValueAt(i, 8).toString();

				mne.setField("WBQTTB", "1");
				mne.setField("WOPAVR", "SALE");// WOPAVR SALE
				mne.setField("OBWHLO", whs);// OBWHLO A11
				mne.setField("OBLTYP", "0");// OBLTYP 0
				mne.setField("WBITNO", xITEM);
				mne.setField("WBORQA", xQTY);
				mne.setField("OBALUN", xUNIT);
//                                mne.setField("WBSAPR", xUNIT);  //  set field unit price King Add
				mne.pressKey(MNEProtocol.KeyEnter);
				mne.pressKey(MNEProtocol.KeyEnter);
				System.out.println(mne.getMsg());

				if (mne.panel.equals("MMS165/B")) {

				    mne.pressKey("F17");
				    mne.pressKey(MNEProtocol.KeyEnter);
				    System.out.println(mne.getMsg());
				    mne.setField("CMDTP", "KEY");
				    mne.pressKey("1");
				    System.out.println(mne.getMsg());
				    mne.pressKey(MNEProtocol.KeyEnter);
				    System.out.println(mne.getMsg());

				    int b = 0;
				    while (((mne.panel.equals("MMS165/B")) && (b <= 5))) {
					b++;
					mne.pressKey(MNEProtocol.KeyEnter);
					System.out.println(mne.getMsg());
				    }

				}

				if (mne.getMsg() != null) {
				    mJsonObj.put("result", "nok");
				    mJsonObj.put("message", mne.getMsg());
				    return mJsonObj.toString();
				}
			    }

			    int c = 0;
			    while (((!mne.panel.equals("OIS300/B")) && (c <= 5))) {
				c++;
				mne.pressKey(MNEProtocol.KeyF03);
				mne.pressKey(MNEProtocol.KeyEnter);
				System.out.println(mne.getMsg());
				if (mne.getMsg() != null) {
				    mJsonObj.put("result", "nok");
				    mJsonObj.put("message", mne.getMsg());
				    return mJsonObj.toString();
				}
			    }

			    mJsonObj.put("result", "ok");
			    mJsonObj.put("message", "CO Number = " + getCONumber);
			    
			    UpdateData.updateStatusOrderHead(cono, divi, orderno, "92", userid);
			    UpdateData.updateCOOrderDetailSupport(cono, divi, orderno, getCONumber, "15");

			    mne.pressKey(MNEProtocol.KeyF03);
			    mne.pressKey(MNEProtocol.KeyF03);
			    mne.pressKey(MNEProtocol.KeyF03);

			}

		    }

		}

	    }

	    mne.closeProgram(OIS300ID);
	    return mJsonObj.toString();

	} catch (Exception ex) {
	    Logger.getLogger(CallMForm.class.getName()).log(Level.SEVERE, null, ex);
	}

	return null;

    }

    public static String callPPS200ChangePO(String cono, String divi, String pono, String orderdate, String delidate,
	    String payment) {

	try {

	    String GetMsg = null;
	    JSONObject mJsonObj = new JSONObject();

	    MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
	    GetMsg = mne.getMsg();
	    System.out.println("Alam : " + GetMsg);
	    if (GetMsg != null) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", GetMsg);
		return mJsonObj.toString();
	    }

	    String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "PUR").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	    getUsernameM3 = "MVXSECOFR";
	    getPasswordM3 = "lawson@90";
	    mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

	    if (!mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3)) {
		System.out.println(" Can not login to M3 system");
	    }

	    String PPS200ID = mne.runM3Pgm("PPS200");
	    System.out.println("PPS200_ID:" + PPS200ID);

	    if ((PPS200ID).equals("")) {
		System.out.println(" ไม่สามารถเปิดโปรแกรม PPS200 ได้");

	    }

	    System.out.println("mne.panel : " + mne.panel);

	    // ---------------------------------------------------------
	    if (mne.panel.equals("PPS200/B")) {
		System.out.println("OK PPS200/B");
		mne.setField("W1OBKV", pono); // W1OBKV 4816809
		mne.pressKey(MNEProtocol.KeyEnter);

		mne.setField("WWQTTP", "1"); // WWQTTP 1
		mne.setField("SELROWS", "R1"); // SELROWS R2
		mne.selectOption("2");
		GetMsg = mne.getMsg();
		System.out.println("Alam : " + GetMsg);

		if (GetMsg != null) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", GetMsg);
		    return mJsonObj.toString();
		}

		if (mne.panel.equals("PPS200/E")) {
		    System.out.println("OK PPS200/E");
		    mne.setField("WAPUDT", orderdate); // WAPUDT 210521
		    mne.setField("WADWDT", delidate); // WADWDT 210521
		    mne.setField("WATEPY", payment); // WATEPY PaymentTerm
		    mne.pressKey(MNEProtocol.KeyEnter);
		    GetMsg = mne.getMsg();
		    System.out.println("Alam : " + GetMsg);

		    int i = 0;
		    while ((!mne.panel.equals("PPS200/F")) && (i <= 3)) {
			mne.pressKey(MNEProtocol.KeyEnter);
			GetMsg = mne.getMsg();
			System.out.println("Alam : " + GetMsg);
			i++;
		    }

		    if (GetMsg != null) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", GetMsg);
			return mJsonObj.toString();
		    }

		    if (mne.panel.equals("PPS200/F")) {
			System.out.println("OK PPS200/F");
			mne.pressKey(MNEProtocol.KeyF03);
		    }

		    mJsonObj.put("result", "ok");
		    mJsonObj.put("message", "PO date change complete.");

		}

		mne.closeProgram(PPS200ID);
		return mJsonObj.toString();

	    }

	} catch (Exception ex) {
	    Logger.getLogger(CallMForm.class.getName()).log(Level.SEVERE, null, ex);

	}

	return null;

    }

    public static String callPPS250(String cono, String divi, String pono, String line, String date) {

	try {

	    String GetMsg = null;
	    JSONObject mJsonObj = new JSONObject();

	    MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
	    GetMsg = mne.getMsg();
	    System.out.println("Alam : " + GetMsg);
	    if (GetMsg != null) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", GetMsg);
		return mJsonObj.toString();
	    }

	    String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "PUR").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	    getUsernameM3 = "MVXSECOFR";
	    getPasswordM3 = "lawson@90";
	    mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

	    if (!mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3)) {
		System.out.println(" Can not login to M3 system");
	    }

	    String PPS250ID = mne.runM3Pgm("PPS250");
	    System.out.println("PPS250_ID:" + PPS250ID);

	    if ((PPS250ID).equals("")) {
		System.out.println(" ไม่สามารถเปิดโปรแกรม PPS250 ได้");

	    }

	    System.out.println("mne.panel : " + mne.panel);

	    // ---------------------------------------------------------
	    if (mne.panel.equals("PPS250/B1")) {
		System.out.println("OK PPS250/B1");
		mne.setField("WWPUNO", pono); // WWPUNO 4825917
		mne.setField("WWPNLI", line); // WWPNLI 20
		mne.pressKey(MNEProtocol.KeyEnter);

		mne.setField("WWQTTP", "1"); // WWQTTP 1
		mne.setField("SELROWS", "R1"); // SELROWS R2
		mne.selectOption("2");
		GetMsg = mne.getMsg();
		System.out.println("Alam : " + GetMsg);

		if (GetMsg != null) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", GetMsg);
		    return mJsonObj.toString();
		}

		if (mne.panel.equals("PPS250/E")) {
		    System.out.println("OK PPS200/E");
		    mne.setField("WBCODT", date); // WBCODT 220521
		    mne.pressKey(MNEProtocol.KeyEnter);
		    GetMsg = mne.getMsg();
		    System.out.println("Alam : " + GetMsg);

		    int i = 0;
		    while ((!mne.panel.equals("PPS250/B1")) && (i <= 3)) {
			mne.pressKey(MNEProtocol.KeyEnter);
			GetMsg = mne.getMsg();
			System.out.println("Alam : " + GetMsg);
			i++;
		    }

		    if (GetMsg != null) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", GetMsg);
			return mJsonObj.toString();
		    }

		    if (mne.panel.equals("PPS200/F")) {
			System.out.println("OK PPS200/F");
			mne.pressKey(MNEProtocol.KeyF03);
		    }

		    mJsonObj.put("result", "ok");
		    mJsonObj.put("message", "PO date change complete.");

		}

		mne.closeProgram(PPS250ID);
		return mJsonObj.toString();

	    }

	} catch (Exception ex) {
	    Logger.getLogger(CallMForm.class.getName()).log(Level.SEVERE, null, ex);

	}

	return null;

    }

    public static String callPPS350(String cono, String divi, String pono) {

	try {

	    String GetMsg = null;
	    JSONObject mJsonObj = new JSONObject();

	    MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
	    GetMsg = mne.getMsg();
	    System.out.println("Alam : " + GetMsg);
	    if (GetMsg != null) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", GetMsg);
		return mJsonObj.toString();
	    }

	    String getUsernamePasswordM3[] = SelectData.getUserM3(cono, divi, "PUR").split(";");
	    String getUsernameM3 = getUsernamePasswordM3[0].trim();
	    String getPasswordM3 = getUsernamePasswordM3[1].trim();

	    mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3);

	    if (!mne.logInToM3(Integer.valueOf(cono), divi, getUsernameM3, getPasswordM3)) {
		System.out.println(" Can not login to M3 system");
	    }

	    String PPS350ID = mne.runM3Pgm("PPS350");
	    System.out.println("PPS350ID:" + PPS350ID);

	    if ((PPS350ID).equals("")) {
		System.out.println(" ไม่สามารถเปิดโปรแกรม PPS200 ได้");

	    }

	    // ---------------------------------------------------------
	    if (mne.panel.equals("PPS350/A")) {
		System.out.println("OK PPS350/A");

		List<String> getListPODetail = null; // SelectData.getPOLine(cono, divi, pono);
		System.out.println(getListPODetail);

		for (int i = 0; i < getListPODetail.size(); i++) {

		    if (mne.panel.equals("PPS350/A")) {

			String[] getDataPODetail = getListPODetail.get(i).split(",");
			String poline = getDataPODetail[0].trim(); // poline

			System.out.println("poline: " + poline);

			mne.setField("WZPUNO", pono); // WZPUNO 9900081
			mne.setField("WZPNLI", poline); // WZPNLI 10
			mne.setField("WZPNLS", ""); // WZPNLS

			mne.pressKey(MNEProtocol.KeyEnter);
			GetMsg = mne.getMsg();
			System.out.println("Alam : " + GetMsg);

			if (GetMsg != null) {
			    mJsonObj.put("result", "nok");
			    mJsonObj.put("message", GetMsg);
			    return mJsonObj.toString();
			}

			if (mne.panel.equals("PPS350/E")) {
			    System.out.println("OK PPS350/E");
			    mne.pressKey(MNEProtocol.KeyEnter);
			    GetMsg = mne.getMsg();
			    System.out.println("Alam : " + GetMsg);

			}

		    }

		    mJsonObj.put("result", "ok");
		    mJsonObj.put("message", "Cancel Complete");

		}

		mne.pressKey(MNEProtocol.KeyF03);
		mne.closeProgram(PPS350ID);
		return mJsonObj.toString();

	    }

	} catch (Exception ex) {
	    Logger.getLogger(CallMForm.class.getName()).log(Level.SEVERE, null, ex);

	}

	return null;

    }

}
