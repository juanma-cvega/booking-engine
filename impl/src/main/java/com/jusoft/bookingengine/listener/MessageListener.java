package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.shared.Message;

public interface MessageListener<T extends Message> {

  void consume(T message);
}
