package com.scoperetail.camel.poc.transform.template.engine;

/*-
 * *****
 * fusion-core
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

import static java.io.File.separator;
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
  private static final String FTL = "ftl";
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
      final String event, final Map<String, Object> params, final String templateName) {
    final String path = event + separator + TEMPLATES + separator + FTL + separator + templateName;
    try {
      final Template template =
          freemarkerComponent.getConfiguration().getTemplate(StringUtils.cleanPath(path));
      final StringWriter writer = new StringWriter();
      template.process(params, writer);
      final String text = writer.toString();
      log.trace(
          "Generated text for \nEvent: {} \nTemplate: {} \nText: {}", event, templateName, text);
      return text;
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}