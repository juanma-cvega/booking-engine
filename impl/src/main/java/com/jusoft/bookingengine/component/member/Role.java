package com.jusoft.bookingengine.component.member;

import java.util.function.Supplier;

public interface Role {

  Supplier<RuntimeException> roleException();
}
