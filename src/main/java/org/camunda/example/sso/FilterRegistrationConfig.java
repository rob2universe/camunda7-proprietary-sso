package org.camunda.example.sso;

import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Collections;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 15)
public class FilterRegistrationConfig {

  @Bean
  public FilterRegistrationBean containerBasedAuthenticationFilter() {

    FilterRegistrationBean registration = new FilterRegistrationBean<ContainerBasedAuthenticationFilter>();
    registration.setFilter(new ContainerBasedAuthenticationFilter());
    registration.setInitParameters(Collections.singletonMap("authentication-provider", MyAuthProvider.class.getName()));
    registration.setOrder(101); // make sure the filter is registered after the Spring Security Filter Chain
    registration.addUrlPatterns("/camunda/app/*");
    return registration;
  }
}
