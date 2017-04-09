package com.vanhack.jobmatch.infra.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Settings {

	private static String PROPERTIES_FILE="settings.properties";
	public static String URL_BACKEND = "";
	
	public static String USER_LOGGED = "userLoggedInTheSystem";
	public static String ERROR = "errorInTheSystem";
	
	public static String E_MAIL_SMTP = "emailSmtp";
	public static String E_MAIL_PORT = "emailPort";
	public static String E_MAIL_USERNAME = "emailUsername";
	public static String E_MAIL_PASSWORD = "emailPassword";
	
	
	static
	  {
	    try {
	      start();
	    }
	    catch (Exception localException)
	    {
	    }
	  }
	public static void start() throws IOException
	  {
	    try
	    {
	      Properties propriedades = new Properties();

	      ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	      InputStream stream = classLoader.getResourceAsStream(PROPERTIES_FILE);

	      if (stream == null) {
	        stream = Settings.class.getClassLoader()
	          .getResourceAsStream(PROPERTIES_FILE);
	      }

	      if (stream == null) {
	        stream = Settings.class.getResourceAsStream(
	        		PROPERTIES_FILE);
	      }

	      propriedades.load(stream);

	      URL_BACKEND = propriedades.getProperty("urlBackEnd");
	      
	      E_MAIL_SMTP = propriedades.getProperty("emailSmtp");
	      E_MAIL_PORT = propriedades.getProperty("emailPort");
	      E_MAIL_USERNAME = propriedades.getProperty("emailUsername");
	      E_MAIL_PASSWORD = propriedades.getProperty("emailPassword");
	      
	      stream.close();
	    }
	    catch (IOException ex) {
	      throw ex;
	    }
	  }
}
