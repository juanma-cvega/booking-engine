package com.jusoft.bookingengine.component.auction.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

  public static AuctionView of(long id,
                               long referenceId,
                               OpenDate openDate,
                               AuctionConfigInfo auctionConfigInfo,
                               Set<Bid> bidders) {
    return new AuctionView(id, referenceId, openDate, auctionConfigInfo, new HashSet<>(bidders));
  }

  public Set<Bid> getBidders() {
    return new HashSet<>(bidders);
  }
}
