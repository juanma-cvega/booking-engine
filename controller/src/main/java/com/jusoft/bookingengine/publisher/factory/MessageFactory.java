package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import com.jusoft.bookingengine.publisher.Message;

public interface MessageFactory<T extends Message, R extends InfrastructureMessage> {

  R createFrom(T message);
}
