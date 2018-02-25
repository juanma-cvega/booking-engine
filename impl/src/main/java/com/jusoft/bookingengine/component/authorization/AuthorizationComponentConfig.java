package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationComponentConfig {

  @Bean
  public AuthorizationComponent authorizationComponent() {
    return new AuthorizationComponentImpl();
  }
}
