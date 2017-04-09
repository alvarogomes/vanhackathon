package com.vanhack.jobmatch.controller.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vanhack.jobmatch.controller.vo.ServiceReturnVO;
import com.vanhack.jobmatch.dao.ProfessionalDao;

@Path("/login")
public class LoginService {

	
	@GET
	@Path("/auth/{user}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO auth(@PathParam("user") String user, @PathParam("password") String passw) {
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		try {
			r = new ProfessionalDao().login(user, passw);
		} catch (Exception ex) {
			r.setCode("2");
			r.setDescription(ex.getMessage());
		}
		return r;
	}
}
