package com.jusoft.controller.slot;

import com.jusoft.component.slot.Slot;

import static com.jusoft.util.TimeUtil.getTimeFrom;

public class SlotResourceFactory {

  public SlotResource createFrom(Slot slot) {
    return new SlotResource(slot.getId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
  }

}
