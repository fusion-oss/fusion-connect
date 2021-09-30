package com.scoperetail.camel.poc;

import com.scoperetail.camel.poc.config.FusionConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class FusionApplicationTests {

  @Autowired
  FusionConfig fusionConfig;

  @Test
  void contextLoads() {
    log.info("config:{}", fusionConfig);
  }
}
