package com.jusoft.bookingengine.controller.slot;

import com.jusoft.bookingengine.component.slot.Slot;

import static com.jusoft.bookingengine.util.TimeUtil.getTimeFrom;

public class SlotResourceFactory {

  public SlotResource createFrom(Slot slot) {
    return new SlotResource(slot.getId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
  }

}
