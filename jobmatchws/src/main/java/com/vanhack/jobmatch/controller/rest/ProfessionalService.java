package com.vanhack.jobmatch.controller.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vanhack.jobmatch.controller.vo.ServiceReturnVO;
import com.vanhack.jobmatch.dao.ProfessionalDao;

@Path("/professional")
public class ProfessionalService {

	@GET
	@Path("/detail/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO detail(@PathParam("param") String codigo) {
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		try {
			r = new ProfessionalDao().detail(codigo);
		} catch (Exception ex) {
			r.setCode("2");
			r.setDescription(ex.getMessage());
		}
		return r;
	}
	
	@GET
	@Path("/search/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO search(@PathParam("param") String description) {
		
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		try {
			r = new ProfessionalDao().search(description);
		} catch (Exception ex) {
			r.setCode("2");
			r.setDescription(ex.getMessage());
		}
		return r;
	}
	
	@GET
	@Path("/detectskills/{codes}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO detectSkill(@PathParam("codes") String codigos) {
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		r = new ProfessionalDao().detectSkill(codigos);
		return r;
	}
	
	
	@GET
	@Path("/professionalcvs/{codes}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO professionalCVS(@PathParam("codes") String codigos) {
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		try {
			r = new ProfessionalDao().searchCVs(codigos);
		} catch (Exception ex) {
			r.setCode("2");
			r.setDescription(ex.getMessage());
		}
		return r;
	}
	
	
}
