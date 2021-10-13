package com.scoperetail.fusion.transform.impl;

import org.springframework.stereotype.Component;
import com.scoperetail.fusion.transform.template.engine.FreemarkerTemplateEngine;

@Component
public class DomainToHashKeyJsonFtlTemplateTransformer
    extends AbstractDomainToHashKeyJsonTemplateTransformer {
  public DomainToHashKeyJsonFtlTemplateTransformer(final FreemarkerTemplateEngine templateEngine) {
    super(templateEngine);
  }
}
