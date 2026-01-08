package com.lxp.user.infrastructure.web.external.config;

import com.lxp.user.infrastructure.web.external.client.TagServiceFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {TagServiceFeignClient.class})
public class FeignConfig {
}
