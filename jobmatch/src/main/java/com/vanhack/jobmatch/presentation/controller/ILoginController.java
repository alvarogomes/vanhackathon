package com.vanhack.jobmatch.presentation.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import com.vanhack.jobmatch.infra.common.Settings;
import com.vanhack.jobmatch.infra.controller.VanhackController;
import com.vanhack.jobmatch.infra.rotines.StringUtils;
import com.vanhack.jobmatch.presentation.vo.LoginVO;
import com.vanhack.jobmatch.presentation.vo.ServiceReturnVO;
import com.vanhack.jobmatch.service.JobMatchService;

@Named("loginController")
@SessionScoped
public class ILoginController extends VanhackController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private LoginVO login;
	
	private String title;

	public LoginVO getLogin() {
	    return this.login;
	}

	public void setLogin(LoginVO login) {
	    this.login = login;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public void iniciar(ComponentSystemEvent event) throws Exception {
		if (!super.isPostBack()) {
			Settings.start();
			this.login = new LoginVO();
		}
	}
	
	public void executeLogin() {
		try {
			
			if (StringUtils.isEmpty(this.login.getLogin()) ||
					StringUtils.isEmpty(this.login.getPassword())) {
				super.insertWarningMessage("Put your username and password...");
				return;
			}
			
			ServiceReturnVO svo = new JobMatchService().login(this.login);
			
			if (svo.getCode().equals("2")) {
				super.insertWarningMessage(svo.getDescription());
			} else {
				this.login.setName(svo.getListProfessionals().get(0).getName());
				super.getSession().setAttribute(Settings.USER_LOGGED, svo.getListProfessionals().get(0));
				
				super.redirect("../login/main.jsf");
			}
			
		} catch (Exception ex) {
			super.redirectExceptionPage(ex);
		}
	}
	
	public void executeLogoff() {
		super.getSession().invalidate();
		super.redirect("../login/login.jsf");
	}
	
	public void menu()
	  {
	    this.title = getRequest().getParameter("title");
	    String pagina = getRequest().getParameter("page");
	    redirect(pagina);
	  }
}
