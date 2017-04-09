package com.vanhack.jobmatch.infra.model;

public class PageButton
{
  private String titulo;
  private String caminho;
  private boolean novaPagina;
  private String icone;

  public PageButton(String titulo, String caminho)
  {
    this.titulo = titulo;
    this.caminho = caminho;
    this.novaPagina = false;
    this.icone = "fa-check";
  }

  public PageButton(String titulo, String caminho, boolean novaPagina)
  {
    this.titulo = titulo;
    this.caminho = caminho;
    this.novaPagina = novaPagina;
    this.icone = "fa-check";
  }

  public PageButton(String titulo, String caminho, String icone, boolean novaPagina)
  {
    this.titulo = titulo;
    this.caminho = caminho;
    this.novaPagina = novaPagina;
    this.icone = icone;
  }

  public String getIcone() {
    return this.icone;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getCaminho() {
    return this.caminho;
  }

  public boolean isNovaPagina() {
    return this.novaPagina;
  }
}