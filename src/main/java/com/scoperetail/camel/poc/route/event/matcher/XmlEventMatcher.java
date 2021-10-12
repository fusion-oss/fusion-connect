package com.scoperetail.camel.poc.route.event.matcher;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import com.scoperetail.camel.poc.util.DocumentBuilderHelper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class XmlEventMatcher implements EventMatcher {
  @Autowired private DocumentBuilderHelper documentBuilderHelper;

  @Override
  public boolean match(final String eventType, final String expression, final String payload) {
    boolean isMatched = false;
    try {
      final InputStream is = new ByteArrayInputStream(payload.getBytes());
      final Document document = documentBuilderHelper.getDocumentBuilder().parse(is);
      final XPath xPath = XPathFactory.newInstance().newXPath();
      final Object object = xPath.compile(expression).evaluate(document);
      isMatched = eventType.equalsIgnoreCase(object.toString());
    } catch (final Exception e) {
      log.error(
          "Unable to match eventType: {} expression: {} due to exception: {}",
          eventType,
          expression,
          e.getMessage());
    }
    return isMatched;
  }
}
