package com.scoperetail.camel.poc.route.event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class XmlEventMatcher {

  // TODO use throws
  public boolean match(final String payload, final String expression) {
    final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder documentBuilder;
      documentBuilder = dbf.newDocumentBuilder();
      final InputStream is = new ByteArrayInputStream(payload.getBytes());
      final Document document = documentBuilder.parse(is);
      final XPath xPath = XPathFactory.newInstance().newXPath();
      final Object object = xPath.compile(expression).evaluate(document);
      return Objects.nonNull(object);
    } catch (final ParserConfigurationException e) {
      e.printStackTrace();
    } catch (final SAXException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    } catch (final XPathExpressionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
