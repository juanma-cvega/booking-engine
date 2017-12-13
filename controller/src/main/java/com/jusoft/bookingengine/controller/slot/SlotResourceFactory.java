package com.jusoft.bookingengine.controller.slot;

import com.jusoft.bookingengine.component.slot.api.SlotView;

import static com.jusoft.bookingengine.util.TimeUtil.getTimeFrom;

public class SlotResourceFactory {

  public SlotResource createFrom(SlotView slot) {
    return new SlotResource(slot.getId(), slot.getRoomId(), getTimeFrom(slot.getStartDate()), getTimeFrom(slot.getEndDate()));
  }

}
