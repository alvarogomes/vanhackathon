package com.vanhack.jobmatch.infra.filter;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vanhack.jobmatch.infra.common.Settings;


@WebFilter({"*.jsf"})
public class VanhackFilter
  implements Filter
{
  
	private InputStream carragarArquivoXml(String nomeArquivo)
	  {
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    InputStream stream = classLoader.getResourceAsStream(nomeArquivo);

	    if (stream == null) {
	      stream = VanhackFilter.class.getClassLoader()
	        .getResourceAsStream(nomeArquivo);
	    }
	    if (stream == null) {
	      stream = VanhackFilter.class
	        .getResourceAsStream(nomeArquivo);
	    }

	    return stream;
	  }
  private boolean isPaginaAutorizada(String acesso) throws IOException {
    boolean retorno = false;
    String nomeArquivo = "publicAccess.xml";

    InputStream input = carragarArquivoXml(nomeArquivo);
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(input);

      NodeList nl = doc.getElementsByTagName("page");

      for (int i = 0; i <= nl.getLength() - 1; i++) {
        Node pagina = nl.item(i);

        String paginaXml = pagina.getTextContent();
        if (paginaXml.equals(acesso)) {
          retorno = true;
          break;
        }
      }
    }
    catch (Exception localException)
    {
    }

    return retorno;
  }

  private boolean isSessaoValida(HttpServletRequest httpServletRequest, HttpSession session)
  {
    boolean retorno = true;

    return retorno;
  }

  private boolean isUsuarioLogado(HttpSession session) {
    return session.getAttribute(Settings.USER_LOGGED) != null;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    if (((request instanceof HttpServletRequest)) && ((response instanceof HttpServletResponse)))
    {
      HttpServletRequest httpServletRequest = (HttpServletRequest)request;
      HttpServletResponse httpServletResponse = (HttpServletResponse)response;

      httpServletResponse.setHeader("X-UA-Compatible", "IE=EmulateIE8");

      HttpSession session = httpServletRequest.getSession();

      String url = httpServletRequest.getServerName();

      String pagina = httpServletRequest.getRequestURI();
      pagina = pagina.replace(httpServletRequest.getContextPath(), "");
      pagina.replace("//", "/");
      int parametro = pagina.indexOf("?");
      if (parametro > 0) {
        pagina = pagina.substring(0, parametro);
      }
      
        if (pagina.length() > 1)
        {
          if (!isPaginaAutorizada(pagina))
          {
            if (!isSessaoValida(httpServletRequest, session))
            {
              String caminho = httpServletRequest.getContextPath() + "/views/useful/sessionExpired.jsf";
              httpServletResponse.sendRedirect(caminho);
              return;
            }

            if (!isUsuarioLogado(session))
            {
              String caminho = httpServletRequest.getContextPath() + "/views/useful/userNotLogged.jsf";
              httpServletResponse.sendRedirect(caminho);
              return;
            }
          }
        }

     

    }

    chain.doFilter(request, response);
  }

  public void init(FilterConfig arg0)
    throws ServletException
  {
  }

  public void destroy()
  {
  }
}