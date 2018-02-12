package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class AuctionView {

  private final long id;
  private final long referenceId;
  @NonNull
  private final OpenDate openDate;
  @NonNull
  private final AuctionConfigInfo auctionConfigInfo;
  @NonNull
  private final Set<Bid> bidders;

  public AuctionView(long id,
                     long referenceId,
                     OpenDate openDate,
                     AuctionConfigInfo auctionConfigInfo,
                     Set<Bid> bidders) {
    this.id = id;
    this.referenceId = referenceId;
    this.openDate = openDate;
    this.auctionConfigInfo = auctionConfigInfo;
    this.bidders = new HashSet<>(bidders);
  }

  public Set<Bid> getBidders() {
    return new HashSet<>(bidders);
  }
}
