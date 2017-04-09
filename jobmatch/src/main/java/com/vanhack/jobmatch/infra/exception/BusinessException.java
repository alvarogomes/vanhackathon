package com.vanhack.jobmatch.infra.exception;
public class BusinessException extends Exception
{
  private static final long serialVersionUID = 1L;
  private String mensagem;

  public BusinessException(String s)
  {
    this.mensagem = s;
  }

  public String getMessage()
  {
    return this.mensagem;
  }
}