package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationManagerComponentConfig {

    @Autowired private Clock clock;

    @Bean
    public AuthorizationManagerComponent authorizationManagerComponent() {
        return new AuthorizationManagerComponentImpl(
                clubRepository(), memberRepository(), clubFactory(), memberFactory(), clock);
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
