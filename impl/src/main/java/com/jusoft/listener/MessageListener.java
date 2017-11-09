package com.jusoft.listener;

import com.jusoft.component.shared.Message;

public interface MessageListener<T extends Message> {

    void consume(T message);
}
