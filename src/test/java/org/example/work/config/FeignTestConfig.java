package org.example.work.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "https://products-service", configuration = RibbonTestConfig.class)
public class FeignTestConfig {
}
