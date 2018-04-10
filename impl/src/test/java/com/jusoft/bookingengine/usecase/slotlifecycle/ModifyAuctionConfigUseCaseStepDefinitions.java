package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ModifyAuctionConfigUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  @Autowired
  private ModifyAuctionConfigUseCase modifyAuctionConfigUseCase;

  public ModifyAuctionConfigUseCaseStepDefinitions() {
    When("^an auction configuration of (\\d+) minutes duration and bookings period of (\\d+) days is added to slot lifecycle manager (\\d+)$",
      (Integer auctionDuration, Integer periodValue, Long roomId) ->
        modifyAuctionConfigUseCase.modifyAuctionConfigFor(roomId, LessBookingsWithinPeriodConfigInfo.of(auctionDuration, periodValue)));
    Then("^the slot lifecycle manager (\\d+) should contain the auction configuration of (\\d+) minutes duration and bookings period of (\\d+) days$", (
      Long roomId, Integer auctionDuration, Integer periodValue) -> {
      SlotLifeCycleManagerView slotLifeCycleManager = slotLifeCycleManagerComponent.find(roomId);
      assertThat(slotLifeCycleManager.getAuctionConfigInfo()).isInstanceOf(LessBookingsWithinPeriodConfigInfo.class);
      LessBookingsWithinPeriodConfigInfo auctionConfig = (LessBookingsWithinPeriodConfigInfo) slotLifeCycleManager.getAuctionConfigInfo();
      assertThat(auctionConfig.getAuctionDuration()).isEqualTo(auctionDuration);
      assertThat(auctionConfig.getEndRangeTimeInDays()).isEqualTo(periodValue);
    });
  }
}
