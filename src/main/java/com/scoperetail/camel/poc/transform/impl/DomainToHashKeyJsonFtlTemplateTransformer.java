package com.scoperetail.camel.poc.transform.impl;

import org.springframework.stereotype.Component;
import com.scoperetail.camel.poc.transform.template.engine.FreemarkerTemplateEngine;

@Component
public class DomainToHashKeyJsonFtlTemplateTransformer
    extends AbstractDomainToHashKeyJsonTemplateTransformer {
  public DomainToHashKeyJsonFtlTemplateTransformer(final FreemarkerTemplateEngine templateEngine) {
    super(templateEngine);
  }
}
