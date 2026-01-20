package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberManagerComponentConfig {

    @Autowired private MessagePublisher messagePublisher;

    @Bean
    public MemberManagerComponent memberComponent() {
        return new MemberManagerComponentImpl(memberFactory(), repository(), messagePublisher);
    }

    private MemberFactory memberFactory() {
        return new MemberFactory(idGenerator());
    }

    private Supplier<Long> idGenerator() {
        AtomicLong idGenerator = new AtomicLong(1);
        return idGenerator::getAndIncrement;
    }

    private MemberRepository repository() {
        return new MemberRepositoryInMemory(new ConcurrentHashMap<>());
    }
}
