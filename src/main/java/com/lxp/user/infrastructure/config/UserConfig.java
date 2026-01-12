package com.lxp.user.infrastructure.config;

import com.lxp.user.infrastructure.web.external.passport.config.KeyProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = {KeyProperties.class})
public class UserConfig {
}
