package com.scoperetail.camel.poc.route.header;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.scoperetail.camel.poc.common.Format;
import com.scoperetail.camel.poc.common.JsonUtils;
import com.scoperetail.camel.poc.config.Event;
import com.scoperetail.camel.poc.transform.impl.DomainToFtlTemplateTransformer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComputeHeader {

  private static final String UNDERSCORE = "_";
  @Autowired private DomainToFtlTemplateTransformer domainToFtlTemplateTransformer;

  public void process(final Message message, final Exchange exchange) {
    final Event eventConfig = exchange.getProperty("event", Event.class);
    // TODO: If eventConfig is not found/empty/null throw exception and stop
    final String format = eventConfig.getSpec().get("format");
    if (Format.JSON.name().equalsIgnoreCase(format)) {
      final DocumentContext documentContext = JsonPath.parse(message.getBody().toString());
      eventConfig
          .getHeader()
          .forEach(
              (k, v) -> {
                Object value = v;
                if (v.startsWith("$")) {
                  value = documentContext.read(v);
                } else if (k.equals("configLookupKey") && v.endsWith(".ftl")) {
                  try {
                    final Map propertyValueMap =
                        JsonUtils.unmarshal(
                            Optional.ofNullable(message.getBody(String.class)),
                            Map.class.getCanonicalName());
                    value = getConfigLookupKey(propertyValueMap, eventConfig, v);
                  } catch (final IOException e) {
                    e.printStackTrace();
                  }
                }
                exchange.setProperty(k, value);
              });
    } else if (Format.XML.name().equalsIgnoreCase(format)) {
      final String payload = message.getBody().toString();
      try {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        final InputStream is = new ByteArrayInputStream(payload.getBytes());
        final Document document = documentBuilder.parse(is);
        final XPath xPath = XPathFactory.newInstance().newXPath();
        eventConfig
            .getHeader()
            .forEach(
                (k, v) -> {
                  Object value = v;
                  if (v.startsWith("/")) {
                    try {
                      value = xPath.compile(v).evaluate(document);
                    } catch (final XPathExpressionException e) {
                      e.printStackTrace();
                    }
                  } else if (k.equals("configLookupKey") && v.endsWith(".ftl")) {
                    final XmlMapper xmlMapper = new XmlMapper();
                    try {
                      final Map propertyValueMap =
                          xmlMapper.readValue(message.getBody(String.class), Map.class);
                      value = getConfigLookupKey(propertyValueMap, eventConfig, v);
                    } catch (final JsonProcessingException e) {
                      e.printStackTrace();
                    }
                  }
                  exchange.setProperty(k, value);
                });
      } catch (final ParserConfigurationException e) {
        e.printStackTrace();
      } catch (final SAXException e) {
        e.printStackTrace();
      } catch (final IOException e) {
        e.printStackTrace();
      }
    }
  }

  private Object getConfigLookupKey(
      final Map propertyValueMap, final Event eventConfig, final String value) {
    String configLookupKey = value;
    try {
      final Map<String, Object> map = new HashMap<>();
      map.put("BODY", propertyValueMap);
      configLookupKey =
          StringUtils.join(
              eventConfig.getEventType(),
              UNDERSCORE,
              domainToFtlTemplateTransformer.transform(eventConfig.getEventType(), map, value));
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return configLookupKey;
  }
}
