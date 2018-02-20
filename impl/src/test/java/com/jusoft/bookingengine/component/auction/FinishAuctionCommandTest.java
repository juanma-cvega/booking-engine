package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.FinishAuctionCommand;
import org.junit.Test;

import static com.jusoft.bookingengine.fixture.AuctionFixtures.AUCTION_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FinishAuctionCommandTest {

  @Test
  public void nullAuctionConfigFailsConstructor() {
    assertThatThrownBy(() -> FinishAuctionCommand.of(AUCTION_ID, null))
      .isInstanceOf(NullPointerException.class);
  }
}
