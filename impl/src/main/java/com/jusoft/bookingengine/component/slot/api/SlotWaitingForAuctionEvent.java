package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotWaitingForAuctionEvent implements Event {

  private final long slotId;
}
