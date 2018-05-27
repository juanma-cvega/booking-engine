package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.SystemLocalTime;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidSlotsForRoomException extends RuntimeException {

  private static final long serialVersionUID = -5408243740112723480L;

  private static final String MESSAGE = "Slots start time %s are invalid for room %s";

  private final long roomId;
  private final List<SystemLocalTime> invalidSlotsStartTime;

  public InvalidSlotsForRoomException(long roomId, List<SystemLocalTime> invalidSlotsStartTime) {
    super(String.format(MESSAGE, invalidSlotsStartTime, roomId));
    this.roomId = roomId;
    this.invalidSlotsStartTime = invalidSlotsStartTime;
  }
}
