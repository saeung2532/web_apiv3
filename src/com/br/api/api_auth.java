package com.br.api;

import java.sql.Connection;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.br.auth.JwtManager;
import com.br.data.SelectData;
import com.br.db.ConnectDB2;

@Path("/api_auth")
public class api_auth {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loginDB2(@Context HttpHeaders headers, String req) throws JSONException {
	System.out.println("req: " + req);
	JSONObject mJsonObj = new JSONObject();

	if (req != null) {

	    Connection conn = null;

	    mJsonObj = new JSONObject(req);
	    String username = mJsonObj.getString("username").toUpperCase();
	    String password = mJsonObj.getString("password");
	    String company = mJsonObj.getString("company");
	    String facility = mJsonObj.getString("facility");

	    String getCompany[] = company.split(" : ");
	    String getCono = getCompany[0];
	    String getDivi = getCompany[1];
	    String getCompanyName = getCompany[2];

	    String getFacility[] = facility.split(" : ");
	    String getFaci = getFacility[0];
	    String getFaciName = getFacility[1];

	    try {
		conn = ConnectDB2.doLogin(username, password);
		System.out.println("conn: " + conn);

		String userAuth = SelectData.getUserAuth(getCono, getDivi, username);
		System.out.println("userAuth: " + userAuth);

		if (userAuth != null) {
		    String getToken = JwtManager.createToken(company + " ; " + facility, username, userAuth);
		    System.out.println(
			    "Username: " + username + "\n" + "Password: " + password + "\n" + "getToken: " + getToken);
		    mJsonObj.put("result", "ok");
		    mJsonObj.put("token", getToken);
		    mJsonObj.put("message", "Login successfully");

		} else {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", "User not authorized.");

		}

	    } catch (Exception e) {
		System.out.println(e);
		String[] getException = e.toString().split(":");
		String getMessage = getException[2].trim();
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", getMessage);
	    }

	    return Response.ok(mJsonObj, MediaType.APPLICATION_JSON + ";charset=utf8").build();

	} else {
	    mJsonObj.put("result", "nok");
	    mJsonObj.put("message", "Invalid username");

	}
	System.out.println(mJsonObj);
	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }
    
    @POST
    @Path("/loginstaffcode")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loginStaffCode(@Context HttpHeaders headers, String req) throws JSONException {
	System.out.println("req: " + req);
	JSONObject mJsonObj = new JSONObject();

	if (req != null) {

	    Connection conn = null;

	    mJsonObj = new JSONObject(req);
	    String username = mJsonObj.getString("username").toUpperCase();
	    String password = mJsonObj.getString("password");
	    String company = mJsonObj.getString("company");

	    String getCompany[] = company.split(" : ");
	    String getCono = getCompany[0];
	    String getDivi = getCompany[1];
	    String getCompanyName = getCompany[2];

	    try {
		conn = ConnectDB2.doConnect();
		
		System.out.println("conn: " + conn);

		Boolean userEnable = SelectData.getUserEnable(getCono, getDivi, username, password);
		System.out.println("userCode: " + userEnable);

		if (userEnable) {
		    
		    String saleman = SelectData.getSalemanCode(getCono, getDivi, username);
		    
		    if (saleman != null) {
		    String getToken = JwtManager.createToken(company, username, saleman);
		    System.out.println("Username: " + username + "\n" + "Password: " + password + "\n" + "Saleman: " + saleman + "\n" + "getToken: " + getToken);
		    mJsonObj.put("result", "ok");
		    mJsonObj.put("token", getToken);
		    mJsonObj.put("message", "Login successfully");
		    
		    } else {
			    mJsonObj.put("result", "nok");
			    mJsonObj.put("message", "User not authorized.");

		    }

		} else {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", "Password incorrect.");

		}

	    } catch (Exception e) {
		System.out.println(e);
		String[] getException = e.toString().split(":");
		String getMessage = getException[2].trim();
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", getMessage);
	    }

	    return Response.ok(mJsonObj, MediaType.APPLICATION_JSON + ";charset=utf8").build();

	} else {
	    mJsonObj.put("result", "nok");
	    mJsonObj.put("message", "Invalid username");

	}
	System.out.println(mJsonObj);
	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

}
