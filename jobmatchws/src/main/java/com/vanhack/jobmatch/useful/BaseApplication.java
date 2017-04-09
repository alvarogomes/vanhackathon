package com.vanhack.jobmatch.useful;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.vanhack.jobmatch.controller.rest.JobSearchService;
import com.vanhack.jobmatch.controller.rest.LoginService;
import com.vanhack.jobmatch.controller.rest.ProfessionalService;

@ApplicationPath("/service")
public class BaseApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	
	public BaseApplication() {
		singletons.add(new ProfessionalService());
		singletons.add(new LoginService());
		singletons.add(new JobSearchService());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}