package com.vanhack.jobmatch.controller.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.vanhack.jobmatch.controller.vo.ServiceReturnVO;
import com.vanhack.jobmatch.dao.JobSearchDao;

@Path("/job")
public class JobSearchService {

	@GET
	@Path("/detail/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO detail(@PathParam("param") String codigo) {
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		try {
			r = new JobSearchDao().detail(codigo);
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
			r = new JobSearchDao().search(description);
		} catch (Exception ex) {
			r.setCode("2");
			r.setDescription(ex.getMessage());
		}
		return r;
	}
	
	
	@POST
	@Path("/index/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceReturnVO index(@PathParam("param") String code) {
		
		ServiceReturnVO r = new ServiceReturnVO("1", "Success");
		r = new JobSearchDao().index(code);
		
		return r;
	}
	
}
