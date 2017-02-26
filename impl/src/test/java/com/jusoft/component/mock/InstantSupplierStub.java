package com.jusoft.component.mock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class InstantSupplierStub implements Supplier<LocalDateTime> {

    private Clock clock;

    public InstantSupplierStub(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now(clock);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
