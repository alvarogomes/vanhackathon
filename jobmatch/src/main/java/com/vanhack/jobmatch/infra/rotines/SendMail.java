package com.vanhack.jobmatch.infra.rotines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.vanhack.jobmatch.infra.common.Settings;


public class SendMail
{

  private Properties properties;

  private String from;
  private String to;
  private String subject;
  private String message;
  private HashMap<String, byte[]> files;
  
  public SendMail()
  {

    this.properties = System.getProperties();
    this.properties.setProperty("mail.smtp.host", Settings.E_MAIL_SMTP);
    this.properties.setProperty("mail.smtp.auth", "true");
    this.properties.setProperty("mail.transport.protocol", "smtp");
    this.properties.setProperty("mail.smtp.starttls.enable","true");
    this.properties.setProperty("mail.smtp.port", Settings.E_MAIL_PORT);
    this.properties.setProperty("mail.smtp.user", Settings.E_MAIL_USERNAME);
    this.properties.setProperty("mail.smtp.password", Settings.E_MAIL_PASSWORD);
    this.files = new HashMap<String, byte[]>();
  }

  public SendMail from(String from) {
	  this.from = from;
	  
	  return this;
  }
  
  public SendMail to(String to) {
	  this.to = to;
	  
	  return this;
  }

  public SendMail subject(String subject) {
	  this.subject = subject;
	  
	  return this;
  }
  
  public SendMail message(String message) {
	  
	  this.message = message;
	  return this;
  }

  public SendMail attachment(String name, byte[] file) {
	  this.files.put(name, file);
	  return this;
  }

  public SendMail attachments(HashMap<String, byte[]> f) {
	  this.files = f;
	  return this;
  }
  
  public void send() throws Exception
  {
    Session session = Session.getDefaultInstance(this.properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
        		Settings.E_MAIL_USERNAME, Settings.E_MAIL_PASSWORD);
      }
    });
    try
    {
      
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(this.from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
      message.setSubject(this.subject);

      Multipart multipart = new MimeMultipart("related");
      

      MimeBodyPart htmlPart = new MimeBodyPart();
      //htmlPart.setContent(this.message, "text/html");
      htmlPart.setText(this.message, "UTF-8", "html");
      multipart.addBodyPart(htmlPart);
      
      if (this.files.size() > 0) {
	      
	      for (Iterator<String> it = this.files.keySet().iterator(); it.hasNext(); ) {
	    	  String key = (String)it.next();
	    	  
	    	  MimeBodyPart messageBodyPart = new MimeBodyPart();
	    	  byte[] file = this.files.get(key);
	          String fileName = key;
	          DataSource source = new ByteArrayDataSource(file, "application/pdf");
	          messageBodyPart.setDataHandler(new DataHandler(source));
	          messageBodyPart.setFileName(fileName);
	          multipart.addBodyPart(messageBodyPart);
	      }
	     
      }
      message.setContent(multipart);
      
      Transport.send(message);
    }
    catch (MessagingException mex) {
      throw mex;
    }
  }
}