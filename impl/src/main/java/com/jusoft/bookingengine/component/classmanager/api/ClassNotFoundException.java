package com.jusoft.bookingengine.component.classmanager.api;

import lombok.Getter;

@Getter
public class ClassNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -9034392509818240582L;

    private static final String MESSAGE = "Class %s not found";

    private final long classId;

    public ClassNotFoundException(long classId) {
        super(String.format(MESSAGE, classId));
        this.classId = classId;
    }
}
