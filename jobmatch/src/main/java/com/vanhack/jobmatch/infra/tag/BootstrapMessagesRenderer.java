package com.vanhack.jobmatch.infra.tag;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.AttributeManager.Key;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.MessagesRenderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;
import javax.faces.render.FacesRenderer;

@FacesRenderer(componentFamily="javax.faces.Messages", rendererType="javax.faces.Messages")
public class BootstrapMessagesRenderer extends MessagesRenderer
{
  private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.MESSAGESMESSAGES);

  public void encodeBegin(FacesContext context, UIComponent component) throws IOException
  {
    super.encodeBegin(context, component);
  }

  public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException
  {
    rendererParamsNotNull(context, component);
    if (!shouldEncode(component)) return;

    boolean mustRender = shouldWriteIdAttribute(component);
    UIMessages messages = (UIMessages)component;
    final ResponseWriter writer = context.getResponseWriter();

    context.setResponseWriter(new ResponseWriterWrapper()
    {
      public ResponseWriter getWrapped()
      {
        return writer;
      }

      public void writeText(Object text, String component)
        throws IOException
      {
        String string = String.valueOf(text);
        super.write(string);
      }
    });
    String clientId = ((UIMessages)component).getFor();
    if ((clientId == null) && (messages.isGlobalOnly())) {
      clientId = "";
    }

    Iterator messageIt = getMessageIter(context, clientId, component);
    if (!messageIt.hasNext()) {
      if (mustRender) {
        if ("javax_faces_developmentstage_messages".equals(component.getId())) {
          return;
        }
        writer.startElement("div", component);
        writeIdAttributeIfNecessary(context, writer, component);
        writer.endElement("div");
      }
      return;
    }

    writeIdAttributeIfNecessary(context, writer, component);
    RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);

    Map msgs = new HashMap();
    msgs.put(FacesMessage.SEVERITY_INFO, new ArrayList());
    msgs.put(FacesMessage.SEVERITY_WARN, new ArrayList());
    msgs.put(FacesMessage.SEVERITY_ERROR, new ArrayList());
    msgs.put(FacesMessage.SEVERITY_FATAL, new ArrayList());

    while (messageIt.hasNext()) {
      FacesMessage curMessage = (FacesMessage)messageIt.next();
      if ((!curMessage.isRendered()) || (messages.isRedisplay()))
      {
        ((List)msgs.get(curMessage.getSeverity())).add(curMessage);
      }
    }
    List severityMessages = (List)msgs.get(FacesMessage.SEVERITY_FATAL);
    if (!severityMessages.isEmpty()) {
      encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_FATAL, severityMessages);
    }

    severityMessages = (List)msgs.get(FacesMessage.SEVERITY_ERROR);
    if (!severityMessages.isEmpty()) {
      encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_ERROR, severityMessages);
    }

    severityMessages = (List)msgs.get(FacesMessage.SEVERITY_WARN);
    if (!severityMessages.isEmpty()) {
      encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_WARN, severityMessages);
    }

    severityMessages = (List)msgs.get(FacesMessage.SEVERITY_INFO);
    if (!severityMessages.isEmpty())
      encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_INFO, severityMessages);
  }

  private void encodeSeverityMessages(FacesContext facesContext, UIMessages uiMessages, FacesMessage.Severity severity, List<FacesMessage> messages)
    throws IOException
  {
    ResponseWriter writer = facesContext.getResponseWriter();
    String alertSeverityClass = "";

    if (FacesMessage.SEVERITY_INFO.equals(severity))
      alertSeverityClass = "alert-info";
    else if (FacesMessage.SEVERITY_WARN.equals(severity))
      alertSeverityClass = "alert-warning";
    else if (FacesMessage.SEVERITY_ERROR.equals(severity))
      alertSeverityClass = "alert-danger";
    else if (FacesMessage.SEVERITY_FATAL.equals(severity)) {
      alertSeverityClass = "alert-danger";
    }

    writer.startElement("div", null);
    writer.writeAttribute("class", "alert " + alertSeverityClass, "alert " + alertSeverityClass);
    writer.writeAttribute("role", "alert", "alert");
    writer.startElement("a", uiMessages);
    writer.writeAttribute("class", "close", "class");
    writer.writeAttribute("data-dismiss", "alert", "data-dismiss");
    writer.writeAttribute("href", "#", "href");
    writer.write("&times;");
    writer.endElement("a");

    writer.startElement("ul", null);

    for (FacesMessage msg : messages) {
      String summary = msg.getSummary() != null ? msg.getSummary() : "";
      String detail = msg.getDetail() != null ? msg.getDetail() : summary;

      writer.startElement("li", uiMessages);

      writer.startElement("div", null);

      if (uiMessages.isShowSummary()) {
        writer.startElement("strong", uiMessages);
        writer.writeText(summary, uiMessages, null);
        writer.endElement("strong");
      }

      if (uiMessages.isShowDetail()) {
        writer.startElement("i", null);
        writer.writeAttribute("style", "font-size: 22px", "font-size: 22px");
        writer.writeAttribute("class", 
          "fa fa-exclamation-triangle animated wobble", "fa fa-exclamation-triangle animated wobble");
        writer.endElement("i");
        writer.writeText(" " + detail, null);
      }
      writer.endElement("div");
      writer.endElement("li");
      msg.rendered();
    }
    writer.endElement("ul");
    writer.endElement("div");
  }
}