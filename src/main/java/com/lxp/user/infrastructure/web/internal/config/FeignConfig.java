package com.lxp.user.infrastructure.web.internal.config;

import com.lxp.user.infrastructure.web.internal.client.TagServiceFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {TagServiceFeignClient.class})
public class FeignConfig {
}
