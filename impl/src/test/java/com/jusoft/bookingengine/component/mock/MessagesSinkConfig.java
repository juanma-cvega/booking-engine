// package com.jusoft.bookingengine.component.mock;
//
// import com.jusoft.bookingengine.publisher.Message;
// import com.jusoft.bookingengine.component.shared.MessagePublisher;
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanPostProcessor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
//
// @Configuration
// public class MessagesSinkConfig implements BeanPostProcessor {
//
//  public Map<Class<? extends Message>, List<Message>> messagesSent = new ConcurrentHashMap<>();
//
//  @Bean
//  public MessagesSink messagesSink() {
//    return new MessagesSink(messagesSent);
//  }
//
//  @Override
//  public Object postProcessBeforeInitialization(Object bean, String beanName) throws
// BeansException {
//    if (bean instanceof MessagePublisher) {
//      return new ProxiedMessagePublisher(messagesSent, (MessagePublisher) bean);
//    }
//    return bean;
//  }
//
//  @Override
//  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
// {
//    return bean;
//  }
// }
