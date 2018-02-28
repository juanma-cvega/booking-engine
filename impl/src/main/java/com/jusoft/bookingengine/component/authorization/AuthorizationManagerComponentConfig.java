package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class AuthorizationManagerComponentConfig {

  @Bean
  public AuthorizationManagerComponent authorizationManagerComponent() {
    return new AuthorizationManagerComponentImpl(clubRepository(), memberRepository(), clubFactory(), memberFactory());
  }

  private ClubFactory clubFactory() {
    return new ClubFactory();
  }

  private MemberFactory memberFactory() {
    return new MemberFactory();
  }

  private ClubRepository clubRepository() {
    return new ClubRepositoryInMemory(new ConcurrentHashMap<>(), new ReentrantLock());
  }

  private MemberRepository memberRepository() {
    return new MemberRepositoryInMemory(new ConcurrentHashMap<>(), new ReentrantLock());
  }
}
