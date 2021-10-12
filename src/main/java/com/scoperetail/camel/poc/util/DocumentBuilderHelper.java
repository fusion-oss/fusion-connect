package com.scoperetail.camel.poc.util;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class DocumentBuilderHelper {
  private DocumentBuilder documentBuilder;

  @PostConstruct
  private void init() throws ParserConfigurationException {
    final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    documentBuilder = dbf.newDocumentBuilder();
  }
}
