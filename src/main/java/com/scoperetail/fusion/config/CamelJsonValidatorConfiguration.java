package com.scoperetail.fusion.config;

import org.apache.camel.component.jsonvalidator.JsonSchemaLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

@Configuration
public class CamelJsonValidatorConfiguration {

  @Bean
  public JsonSchemaLoader customJsonSchemaLoader() {
    return (camelContext, schemaStream) ->
        JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaStream);
  }
}
