package com.scoperetail.fusion.route.header;

import static com.scoperetail.fusion.common.Format.JSON;
import static com.scoperetail.fusion.common.Format.XML;
import static com.scoperetail.fusion.util.Constant.DOLLAR_SIGN;
import static com.scoperetail.fusion.util.Constant.FORWARD_SLASH;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.scoperetail.fusion.common.JsonUtils;
import com.scoperetail.fusion.config.Event;
import com.scoperetail.fusion.transform.impl.DomainToFtlTemplateTransformer;
import com.scoperetail.fusion.util.DocumentBuilderHelper;

public class ComputeHeader {
  private static final String FTL_EXTENSION = ".ftl";

  @Autowired private DomainToFtlTemplateTransformer domainToFtlTemplateTransformer;
  @Autowired private DocumentBuilderHelper documentBuilderHelper;

  public void process(final Message message, final Exchange exchange) throws Exception {
    final Event eventConfig = exchange.getProperty("event", Event.class);
    final String format = eventConfig.getSpec().get("format");
    final String payload = message.getBody(String.class);
    final Object document = getDocument(format, payload);
    final Map<String, Object> eventObject = new HashMap<>();
    for (final Entry<String, Object> entry : eventConfig.getHeaders().entrySet()) {
      final String headerKey = entry.getKey();
      final Object headerValue = entry.getValue();
      Object computedValue = null;
      if (headerValue.toString().startsWith(DOLLAR_SIGN)) {
        computedValue = ((DocumentContext) document).read(headerValue.toString());
      } else if (headerValue.toString().startsWith(FORWARD_SLASH)) {
        final XPath xPath = XPathFactory.newInstance().newXPath();
        computedValue = xPath.compile(headerValue.toString()).evaluate(document);
      } else if (headerValue.toString().endsWith(FTL_EXTENSION)) {
        computedValue =
            computeValueUsingFtl(payload, eventConfig, format, eventObject, headerValue);
      } else {
        computedValue = headerValue;
      }
      exchange.setProperty(headerKey, computedValue);
    }
  }

  private Object computeValueUsingFtl(
      final String payload,
      final Event eventConfig,
      final String format,
      final Map<String, Object> eventObject,
      final Object headerValue)
      throws Exception {
    if (eventObject.isEmpty()) {
      if (JSON.name().equalsIgnoreCase(format)) {
        eventObject.putAll(
            JsonUtils.unmarshal(Optional.ofNullable(payload), Map.class.getCanonicalName()));
      } else if (XML.name().equalsIgnoreCase(format)) {
        final XmlMapper xmlMapper = new XmlMapper();
        eventObject.putAll(xmlMapper.readValue(payload, Map.class));
      }
    }
    return domainToFtlTemplateTransformer.transform(
        eventConfig.getEventType(), eventObject, headerValue.toString());
  }

  private Object getDocument(final String format, final String payload)
      throws SAXException, IOException {
    Object document = null;
    if (JSON.name().equalsIgnoreCase(format)) {
      document = JsonPath.parse(payload);
    } else if (XML.name().equalsIgnoreCase(format)) {
      final InputStream is = new ByteArrayInputStream(payload.getBytes());
      document = documentBuilderHelper.getDocumentBuilder().parse(is);
    }
    return document;
  }
}