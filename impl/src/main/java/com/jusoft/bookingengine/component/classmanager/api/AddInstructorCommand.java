package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Command;

public record AddInstructorCommand(long classId, long instructorId) implements Command {}
