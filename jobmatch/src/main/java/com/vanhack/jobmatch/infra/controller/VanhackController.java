package com.vanhack.jobmatch.infra.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vanhack.jobmatch.infra.common.Settings;
import com.vanhack.jobmatch.infra.exception.BusinessException;
import com.vanhack.jobmatch.infra.model.PageButton;

public class VanhackController {

	public void noAction() {
	  }
	  protected HttpSession getSession() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession)context.getExternalContext()
	      .getSession(false);
	    return session;
	  }

	  protected HttpServletRequest getRequest() {
	    ExternalContext context = FacesContext.getCurrentInstance()
	      .getExternalContext();
	    HttpServletRequest request = (HttpServletRequest)context.getRequest();

	    return request;
	  }

	  protected boolean isPostBack() {
	    return FacesContext.getCurrentInstance().isPostback();
	  }
	  protected HttpServletResponse getResponse() {
	    ExternalContext context = FacesContext.getCurrentInstance()
	      .getExternalContext();
	    HttpServletResponse request = (HttpServletResponse)context
	      .getResponse();

	    return request;
	  }

	  protected String getURLApp()
	  {
	    String url = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + 
	      getRequest().getServerPort() + getRequest().getContextPath();

	    return url;
	  }

	  protected void successfulPage(String mensagem, Collection<PageButton> colecao) {
	    getSession().setAttribute("mensagemSucesso", mensagem);
	    getSession().setAttribute("colecaoBotoesSucesso", colecao);

	    redirect("../useful/successfulPage.jsf");
	  }

	  protected void redirectExceptionPage(Throwable excecao) {
	    StringBuffer mensagem = new StringBuffer("");
	    
	    if ((excecao instanceof BusinessException)) {
	      String msg = ((BusinessException)excecao).getMessage();
	      
	      mensagem = new StringBuffer(msg);
	    } else {
	      mensagem = new StringBuffer("Error in the system");
	    }

	    
	      if (!(excecao instanceof BusinessException)) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        excecao.printStackTrace(new PrintStream(baos));

	        String trace = baos.toString().replace("\n", "<br>");

	        mensagem.append("<br><br>");
	        mensagem.append(trace);
	      }

	    System.out.println(mensagem.toString());
	    getSession().setAttribute(Settings.ERROR, mensagem.toString());
	    redirect("../useful/error.jsf");
	  }

	  protected void redirect(String caminho)
	  {
	    try {
	      FacesContext faces = FacesContext.getCurrentInstance();
	      ExternalContext context = faces.getExternalContext();

	      context.redirect(caminho);
	      faces.responseComplete();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }

	  protected void insertInformationMessage(String mensagem) {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	  }

	  protected void insertWarningMessage(String mensagem) {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", mensagem));
	  }

	  protected void insertErrorMessage(String mensagem) {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensagem));
	  }
}
