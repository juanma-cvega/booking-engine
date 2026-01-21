package com.jusoft.bookingengine.usecase.auction;

import com.jusoft.bookingengine.component.auction.api.AuctionManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBidderToAuctionUseCase {

    private final AuctionManagerComponent auctionManagerComponent;
    private final SlotManagerComponent slotManagerComponent;
    private final AuthorizationManagerComponent authorizationManagerComponent;

    public void addBidderToAuctionFor(long userId, long slotId) {
        SlotView slot = slotManagerComponent.find(slotId);
        authorizationManagerComponent.authorizeBidInAuction(
                new AuthorizeCommand(
                        userId,
                        slot.roomId(),
                        slot.buildingId(),
                        slot.clubId(),
                        slot.creationTime()));
        auctionManagerComponent.addBidderToAuctionFor(userId, slotId);
    }
}
