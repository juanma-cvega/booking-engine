package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;

import java.time.ZonedDateTime;

public interface SlotLifeCycleManagerComponent {

  SlotLifeCycleManagerView createFrom(long roomId, SlotValidationInfo slotValidationInfo);

  SlotLifeCycleManagerView find(long roomId);

  void replaceSlotValidationWith(long roomId, SlotValidationInfo slotValidationInfo);

  void modifyAuctionConfigFor(long roomId, AuctionConfigInfo auctionConfigInfo);

  void addClassConfigTo(long roomId, ClassConfig classConfig);

  void removeClassConfigFrom(long roomId, long classId);

  void addPreReservation(long roomId, PreReservation preReservation);

  void removePreReservation(long roomId, ZonedDateTime slotStartTime);

  void findNextSlotStateFor(long slotId, long roomId, ZonedDateTime slotStartTime);

}
