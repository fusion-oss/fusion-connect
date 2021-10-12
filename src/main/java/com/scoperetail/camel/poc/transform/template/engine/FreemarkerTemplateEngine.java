package com.scoperetail.camel.poc.transform.template.engine;

import java.io.StringWriter;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.apache.camel.component.freemarker.FreemarkerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FreemarkerTemplateEngine implements TemplateEngine {
  private FreemarkerComponent freemarkerComponent;
  @Autowired private CamelContext camelContext;

  @PostConstruct
  private void init() {
    freemarkerComponent = camelContext.getComponent("freemarker", FreemarkerComponent.class);
    final Configuration configuration = freemarkerComponent.getConfiguration();
    configuration.setNumberFormat("computer");
    freemarkerComponent.setConfiguration(configuration);
  }

  @Override
  public String generateTextFromTemplate(
      final String event, final Map<String, Object> params, final String templatePath) {
    try {
      final Template template =
          freemarkerComponent.getConfiguration().getTemplate(StringUtils.cleanPath(templatePath));
      final StringWriter writer = new StringWriter();
      template.process(params, writer);
      final String text = writer.toString();
      log.trace(
          "Generated text for \nEvent: {} \nTemplate: {} \nText: {}", event, templatePath, text);
      return text;
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
