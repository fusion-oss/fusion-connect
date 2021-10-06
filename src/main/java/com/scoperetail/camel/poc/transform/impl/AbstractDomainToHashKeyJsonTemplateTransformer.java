package com.scoperetail.camel.poc.transform.impl;

import java.util.Map;
import com.scoperetail.camel.poc.transform.AbstractTransformer;
import com.scoperetail.camel.poc.transform.template.engine.TemplateEngine;

public abstract class AbstractDomainToHashKeyJsonTemplateTransformer extends AbstractTransformer {

  public AbstractDomainToHashKeyJsonTemplateTransformer(final TemplateEngine templateEngine) {
    super(templateEngine);
  }

  @Override
  public String transform(
      final String event, final Map<String, Object> params, final String templateName) {
    return templateEngine.generateTextFromTemplate(event, params, templateName);
  }
}
