package com.br.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.br.auth.JwtManager;
import com.br.data.DeleteData;
import com.br.data.InsertData;
import com.br.data.SelectData;
import com.br.data.UpdateData;
import com.br.utility.CallMForm;
import com.br.utility.ConvertDate;
import com.br.utility.ConvertStringtoObject;
import com.sun.jersey.multipart.FormDataParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Path("/api_data")
public class api_data {

    @GET
    @Path("/company")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getCompany(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);

		try {

		    return Response.ok(SelectData.getCompany(), MediaType.APPLICATION_JSON + ";charset=utf8").build();

//				return Response.ok("Welcome BRL", MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/companywithtoken")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getCompanyWithToken(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);
		String company = validate.getBody().get("role", String.class);

		try {

		    return Response.ok(SelectData.getCompany(), MediaType.APPLICATION_JSON + ";charset=utf8").build();

//				return Response.ok("Welcome BRL", MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/facility/{cono}/{divi}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getFacility(@Context HttpHeaders headers, @PathParam("cono") String cono,
	    @PathParam("divi") String divi, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);
	System.out.println("cono: " + cono);
	System.out.println("divi: " + divi);

//		Jws<Claims> validate = null;
//
//		if (getToken != null) {

//		wonumber = "0001100780";
	try {

	    // jwt verify token
//			validate = JwtManager.parseToken(getToken);
//			System.out.println("validate: " + validate);
//			String username = validate.getBody().get("aud", String.class);
//			String company = validate.getBody().get("role", String.class);

	    try {
		return Response.ok(SelectData.getFacility("10", "101"), MediaType.APPLICATION_JSON + ";charset=utf8")
			.build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

//		} else {
//
//			mJsonObj.put("auth", "false");
//			mJsonObj.put("message", "No token provided");
//		}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/warehouse")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWarehouse(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);
		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");

		String company = getSubject[0];
		String facility = getSubject[1];

		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		String getFacility[] = facility.split(" : ");
		String getFaci = getFacility[0];
		String getFaciName = getFacility[1];

		try {
//		    return null;
		    return Response
			    .ok(SelectData.getWarehouse(getCono, getFaci), MediaType.APPLICATION_JSON + ";charset=utf8")
			    .build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/wohead/{warehouse}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWOHead(@Context HttpHeaders headers, @PathParam("warehouse") String warehouse, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);
		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");

		String company = getSubject[0];
		String facility = getSubject[1];

		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		String getFacility[] = facility.split(" : ");
		String getFaci = getFacility[0];
		String getFaciName = getFacility[1];

		try {
		    return Response.ok(SelectData.getWOHead(getCono, getFaci, warehouse),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/woheadmachine/{machine}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWOHeadMachine(@Context HttpHeaders headers, @PathParam("machine") String machine, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

//		Jws<Claims> validate = null;
//
//		if (getToken != null) {

//		wonumber = "0001100780";
	try {

	    // jwt verify token
//			validate = JwtManager.parseToken(getToken);
//			System.out.println("validate: " + validate);
//			String username = validate.getBody().get("aud", String.class);
//			String company = validate.getBody().get("role", String.class);

	    try {
		return Response
			.ok(SelectData.getWOHeadMachine("10", "101", "1A1", "A91", machine, "20", "90", "20", "90"),
				MediaType.APPLICATION_JSON + ";charset=utf8")
			.build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

//		} else {
//
//			mJsonObj.put("auth", "false");
//			mJsonObj.put("message", "No token provided");
//		}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/woheadfaci/{faci}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWOHeadFaci(@Context HttpHeaders headers, @PathParam("faci") String faci, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);
		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");

		String company = getSubject[0];
		String facility = getSubject[1];

		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		String getFacility[] = facility.split(" : ");
		String getFaci = getFacility[0];
		String getFaciName = getFacility[1];

		try {
		    return Response.ok(SelectData.getWOHeadFaci(getCono, getDivi, faci),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/wodetail/{wonumber}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getWODetail(@Context HttpHeaders headers, @PathParam("wonumber") String wonumber, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);
		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");

		String company = getSubject[0];
		String facility = getSubject[1];

		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		String getFacility[] = facility.split(" : ");
		String getFaci = getFacility[0];
		String getFaciName = getFacility[1];

		try {
		    return Response.ok(SelectData.getWODetail(getCono, getDivi, getFaci, wonumber, "M3FDBPRD", "20",
			    "90", "20", "90"), MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/wodetail")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//		@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addWODetail(@Context HttpHeaders headers, @FormDataParam("values") String values,
	    @FormDataParam("device") String device) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//	System.out.println(vResult);

//	Jws<Claims> validate = null;
//
//	if (getToken != null) {

	try {

	    // jwt verify token
//		validate = JwtManager.parseToken(getToken);
//		System.out.println("validate: " + validate);
//		String username = validate.getBody().get("aud", String.class);
//		String company = validate.getBody().get("sub", String.class);
//		String getCompany[] = company.split(" : ");
//		String getCono = getCompany[0];
//		String getDivi = getCompany[1];
//		String getCompanyName = getCompany[2];
//					String getCono = "10";
//					String getDivi = "101";
//					vReques tor = "M3SRVICT";

	    try {
		return Response.ok(UpdateData.addPRHead(values, device), MediaType.APPLICATION_JSON + ";charset=utf8")
			.build();

//		    return Response.ok(
//			    postdata.addPRHead(getCono, getDivi, vPRNumber, convertDate.convertDateToDecimal(vDate),
//				    vWarehouse, vDepartment, vMonth, vPlanUnPlan, vBU, vCAPNo, vRequestor, vRemark,
//				    vApprove1, vApprove2, vApprove3, vApprove4, vStatus),
//			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

//	} else {
//
//	    mJsonObj.put("auth", "false");
//	    mJsonObj.put("message", "No token provided");
//	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/customer")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getCustomer(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getCustomer(getCono, getDivi, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/ordernumbersaleman/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrderNumberSaleman(@Context HttpHeaders headers, @PathParam("status") String status, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getOrderNumberSaleman(getCono, getDivi, userID, status),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/ordernumbersupport/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrderNumberSupport(@Context HttpHeaders headers, @PathParam("status") String status, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getOrderNumberSupport(getCono, getDivi, userID, status, auth),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/orderhead/{orderno}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrderHead(@Context HttpHeaders headers, @PathParam("orderno") String orderno, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getOrderHead(getCono, getDivi, orderno),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/orderdetail/{orderno}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrderDetail(@Context HttpHeaders headers, @PathParam("orderno") String orderno, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getOrderDetail(getCono, getDivi, orderno),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/orderdetailsupport/{orderno}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrderDetailSupport(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getOrderDetailSupport(getCono, getDivi, orderno),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/item/{whs}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getItem(@Context HttpHeaders headers, @PathParam("whs") String whs, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response
			    .ok(SelectData.getItem(getCono, getDivi, whs), MediaType.APPLICATION_JSON + ";charset=utf8")
			    .build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/itemunit/{item}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getItemUnit(@Context HttpHeaders headers, @PathParam("item") String item, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getItemUnit(getCono, getDivi, item),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/monitoringhead")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMonitorHead(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getMonitoringHead(getCono, getDivi, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/monitoringdetail/{orderno}/{conumber}/{invnumber}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMonitorDetail(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("conumber") String conumber, @PathParam("invnumber") String invnumber, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getMonitoringDetail(getCono, getDivi, orderno, conumber, invnumber),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getUser(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response
			    .ok(SelectData.getUser(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
			    .build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/groupinguser/{salesup}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getGroupingUser(@Context HttpHeaders headers, @PathParam("salesup") String salesup, String req)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(SelectData.getGroupingUser(getCono, getDivi, salesup),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/pricelist")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPriceList(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response
			    .ok(SelectData.getPriceList(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
			    .build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/rental")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRental(@Context HttpHeaders headers, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

//		Jws<Claims> validate = null;

//		if (getToken != null) {

	try {

	    // jwt verify token
//				validate = JwtManager.parseToken(getToken);
//				System.out.println("validate: " + validate);

	    try {

		return Response.ok(SelectData.getRental(), MediaType.APPLICATION_JSON + ";charset=utf8").build();

//				return Response.ok("Welcome BRL", MediaType.APPLICATION_JSON + ";charset=utf8").build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

//		} else {
//
//			mJsonObj.put("auth", "false");
//			mJsonObj.put("message", "No token provided");
//		}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/orderhead")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addOrderHead(@Context HttpHeaders headers, @FormDataParam("vOrderNumber") String vOrderNumber,
	    @FormDataParam("vCustomerNo") String vCustomerNo, @FormDataParam("vOrderDate") String vOrderDate,
	    @FormDataParam("vDeliveryDate") String vDeliveryDate, @FormDataParam("vRound") String vRound,
	    @FormDataParam("vPricelist") String vPricelist, @FormDataParam("vOrderType") String vOrderType,
	    @FormDataParam("vWarehouse") String vWarehouse, @FormDataParam("vStatus") String vStatus,
	    @FormDataParam("vType") String vType, @FormDataParam("vRemark") String vRemark) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(
			    InsertData.addOrderHead(getCono, getDivi, vCustomerNo, vOrderDate, vDeliveryDate, vRound,
				    vPricelist, vOrderType, vWarehouse, vStatus, vType, vRemark, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/itemdetail")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addItemDetail(@Context HttpHeaders headers, @FormDataParam("vOrderNumber") String vOrderNumber,
	    @FormDataParam("vCustomerNo") String vCustomerNo, @FormDataParam("vItemLine") String vItemLine,
	    @FormDataParam("vItemNo") String vItemNo, @FormDataParam("vItemDesc1") String vItemDesc1,
	    @FormDataParam("vItemDesc2") String vItemDesc2, @FormDataParam("vQty") String vQty,
	    @FormDataParam("vUnit") String vUnit, @FormDataParam("vRemark") String vRemark,
	    @FormDataParam("vStatus") String vStatus) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(
			    InsertData.addItemDetail(getCono, getDivi, vOrderNumber, vCustomerNo, vItemLine, vItemNo,
				    vItemDesc1, vItemDesc2, vQty, vUnit, vRemark, vStatus, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/itemdetailsupport")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addItemDetailSupport(@Context HttpHeaders headers,
	    @FormDataParam("vOrderNumber") String vOrderNumber, @FormDataParam("vCustomerNo") String vCustomerNo,
	    @FormDataParam("vItemLine") String vItemLine, @FormDataParam("vItemNo") String vItemNo,
	    @FormDataParam("vItemDesc1") String vItemDesc1, @FormDataParam("vItemDesc2") String vItemDesc2,
	    @FormDataParam("vQty") String vQty, @FormDataParam("vUnit") String vUnit,
	    @FormDataParam("vRemark") String vRemark, @FormDataParam("vStatus") String vStatus) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(
			    InsertData.addItemDetailSupport(getCono, getDivi, vOrderNumber, vCustomerNo, vItemLine,
				    vItemNo, vItemDesc1, vItemDesc2, vQty, vUnit, vRemark, vStatus, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/user")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addUser(@Context HttpHeaders headers, @FormDataParam("vSalesup") String vSalesup,
	    @FormDataParam("vSaleman") String vSaleman, @FormDataParam("vChannel") String vChannel)
	    throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(InsertData.addUser(getCono, getDivi, vSalesup, vSaleman, vChannel),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/orderhead")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateOrderHead(@Context HttpHeaders headers, @FormDataParam("vOrderNumber") String vOrderNumber,
	    @FormDataParam("vCustomerNo") String vCustomerNo, @FormDataParam("vOrderDate") String vOrderDate,
	    @FormDataParam("vDeliveryDate") String vDeliveryDate, @FormDataParam("vRound") String vRound,
	    @FormDataParam("vPricelist") String vPricelist, @FormDataParam("vOrderType") String vOrderType,
	    @FormDataParam("vWarehouse") String vWarehouse, @FormDataParam("vStatus") String vStatus,
	    @FormDataParam("vType") String vType, @FormDataParam("vRemark") String vRemark) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(
			    UpdateData.updateOrderHead(getCono, getDivi, vOrderNumber, vOrderDate, vDeliveryDate,
				    vRound, vPricelist, vOrderType, vWarehouse, vStatus, vType, vRemark, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/statusorderhead/{orderno}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateStatusOrderHead(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("status") String status, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(UpdateData.updateStatusOrderHead(getCono, getDivi, orderno, status, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/statusorderdetail/{orderno}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateStatusOrderDetail(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("status") String status, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(UpdateData.updateStatusOrderDetail(getCono, getDivi, orderno, status),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/itemdetail/{orderno}/{line}/{qty}/{unit}/{remark}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateItemDetail(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("line") String line, @PathParam("qty") String qty, @PathParam("unit") String unit,
	    @PathParam("remark") String remark, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(
			    UpdateData.updateItemDetail(getCono, getDivi, orderno, line, qty, unit,
				    ConvertStringtoObject.convertNullToObject(remark), userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/itemdetailsupport/{orderno}/{line}/{qty}/{unit}/{remark}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateItemDetailSupport(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("line") String line, @PathParam("qty") String qty, @PathParam("unit") String unit,
	    @PathParam("remark") String remark, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(
			    UpdateData.updateItemDetailSupport(getCono, getDivi, orderno, line, qty, unit,
				    ConvertStringtoObject.convertNullToObject(remark), userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @PUT
    @Path("/user/{salesup}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateUser(@Context HttpHeaders headers, @PathParam("salesup") String salesup,
	    @PathParam("status") String status, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {
		    return Response.ok(UpdateData.updateUser(getCono, getDivi, salesup, status),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @DELETE
    @Path("/orderdetail/{orderno}/{line}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteItemDetail(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("line") String line) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(DeleteData.deleteItemDetail(getCono, getDivi, orderno, line),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @DELETE
    @Path("/orderdetailsupport/{orderno}/{line}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteItemDetailSupport(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
	    @PathParam("line") String line) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(DeleteData.deleteItemDetailSupport(getCono, getDivi, orderno, line),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @DELETE
    @Path("/user/{salesup}/{saleman}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteUser(@Context HttpHeaders headers, @PathParam("salesup") String salesup,
	    @PathParam("saleman") String saleman) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(DeleteData.deleteUser(getCono, getDivi, salesup, saleman),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/callmformois300/{orderno}/{whs}/{customerno}/{ordertype}/{delidate}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response callMFormOIS300(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
	    @PathParam("orderno") String orderno, @PathParam("whs") String whs,
	    @PathParam("customerno") String customerno, @PathParam("ordertype") String ordertype,
	    @PathParam("delidate") String delidate, @PathParam("status") String status) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");
//		System.out.println("getToken: " + getToken);

	Jws<Claims> validate = null;

	if (getToken != null) {

	    try {

		// jwt verify token
		validate = JwtManager.parseToken(getToken);
		System.out.println("validate: " + validate);
		String username = validate.getBody().get("aud", String.class);

		String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
		String userID = getUser[1];
		String group = getUser[2];
		String auth = getUser[3];

		String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
		String company = getSubject[0];
		String getCompany[] = company.split(" : ");
		String getCono = getCompany[0];
		String getDivi = getCompany[1];
		String getCompanyName = getCompany[2];

		try {

		    return Response.ok(
			    CallMForm.callOIS300(getCono, getDivi, orderno, whs, customerno, ordertype,
				    ConvertDate.convertDateToDateM3(delidate), status, userID),
			    MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
		    mJsonObj.put("result", "nok");
		    mJsonObj.put("message", e);
		    e.printStackTrace();
		}

	    } catch (Exception e) {
		mJsonObj.put("auth", "false");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} else {

	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", "No token provided");
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @POST
    @Path("/callmformois300wotoken/{cono}/{divi}/{orderno}/{whs}/{customerno}/{ordertype}/{delidate}/{status}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response callMFormOIS300WithOutToken(@Context HttpServletRequest httpServletRequest,
	    @PathParam("cono") String cono, @PathParam("divi") String divi, @PathParam("orderno") String orderno,
	    @PathParam("whs") String whs, @PathParam("customerno") String customerno,
	    @PathParam("ordertype") String ordertype, @PathParam("delidate") String delidate,
	    @PathParam("status") String status) throws JSONException {

	JSONObject mJsonObj = new JSONObject();

	try {

	    try {

		return Response.ok(
			CallMForm.callOIS300(cono, divi, orderno, whs, customerno, ordertype,
				ConvertDate.convertDateToDateM3(delidate), status, "test"),
			MediaType.APPLICATION_JSON + ";charset=utf8").build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

    @GET
    @Path("/duck/{device}/{date}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDuck(@Context HttpHeaders headers, @PathParam("device") String device,
	    @PathParam("date") String date, String req) throws JSONException {

	JSONObject mJsonObj = new JSONObject();
	String getToken = headers.getRequestHeaders().getFirst("x-access-token");

	try {

	    try {

		return Response.ok(SelectData.getDuckV2(device, date), MediaType.APPLICATION_JSON + ";charset=utf8")
			.build();

	    } catch (Exception e) {
		mJsonObj.put("result", "nok");
		mJsonObj.put("message", e);
		e.printStackTrace();
	    }

	} catch (Exception e) {
	    mJsonObj.put("auth", "false");
	    mJsonObj.put("message", e);
	    e.printStackTrace();
	}

	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

    }

}
