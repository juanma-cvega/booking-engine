package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class SlotLifeCycleManagerView implements Serializable {

  private static final long serialVersionUID = 5053557935354511577L;

  private final long roomId;
  private final SlotsTimetable slotsTimetable;
  private final AuctionConfigInfo auctionConfigInfo;
  private final List<PreReservation> preReservations;
  private final Map<Long, ClassTimetable> classesConfig;
}
