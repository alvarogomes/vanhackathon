package com.vanhack.jobmatch.infra.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.vanhack.jobmatch.infra.common.Settings;
import com.vanhack.jobmatch.infra.model.PageButton;

@Named("util")
@ApplicationScoped
public class UsefulController extends VanhackController
  implements Serializable
{
  private static final long serialVersionUID = 1L;


  public String getMensagemErro() {
    return (String)super.getSession().getAttribute(Settings.ERROR);
  }

  public Collection<PageButton> getBotoes() {
    Collection colecao = (Collection)getSession().getAttribute("colecaoBotoesSucesso");
    return colecao;
  }

  public String getMensagemSucesso() {
    String mensagem = (String)getSession().getAttribute("mensagemSucesso");
    return mensagem;
  }
}