package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.auction.api.AuctionView;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationComponent;
import com.jusoft.bookingengine.component.authorization.api.SlotCoordinates;
import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotType;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBidderToAuctionUseCase {

  private final SlotManagerComponent slotManagerComponent;
  private final AuctionManagerComponent auctionManagerComponent;
  private final RoomManagerComponent roomManagerComponent;
  private final BuildingManagerComponent buildingManagerComponent;
  private final ClubManagerComponent clubManagerComponent;
  private final AuthorizationComponent authorizationComponent;

  public void addBidderTo(long auctionId, long userId) {
    AuctionView auction = auctionManagerComponent.find(auctionId);
    SlotView slot = slotManagerComponent.find(auction.getReferenceId());
    RoomView room = roomManagerComponent.find(slot.getRoomId());
    BuildingView building = buildingManagerComponent.find(room.getBuildingId());
    ClubView club = clubManagerComponent.find(building.getClubId());
    authorizationComponent.canBid(SlotCoordinates.of(
      userId,
      club.getId(),
      building.getId(),
      room.getId(),
      SlotType.NORMAL), () -> auctionManagerComponent.addBidderTo(auctionId, userId));
  }
}
