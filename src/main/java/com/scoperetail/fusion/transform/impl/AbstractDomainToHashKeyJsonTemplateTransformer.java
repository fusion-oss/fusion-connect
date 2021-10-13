package com.scoperetail.fusion.transform.impl;

import java.util.Map;
import com.scoperetail.fusion.transform.AbstractTransformer;
import com.scoperetail.fusion.transform.template.engine.TemplateEngine;

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
