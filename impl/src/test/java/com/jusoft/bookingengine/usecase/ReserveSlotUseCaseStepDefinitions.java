package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.booking.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.booking.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.booking.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.slot.api.ReserveSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private SlotManagerComponent slotManagerComponent;

  @Autowired
  private ReserveSlotUseCase reserveSlotUseCase;

  public ReserveSlotUseCaseStepDefinitions() {
    Then("^the user (.*) should get a notification that he is not a member of the club$", (Long userId) -> {
      assertThat(exceptionThrown).isInstanceOf(UserNotMemberException.class);
      UserNotMemberException exception = (UserNotMemberException) exceptionThrown;
      assertThat(exception.getUserId()).isEqualTo(userId);
      assertThat(exception.getClubId()).isEqualTo(clubCreated.getId());
    });
    When("^the slot is reserved by user (\\d+)$", (Long userId) ->
      reserveSlotUseCase.reserveSlot(ReserveSlotCommand.of(slotCreated.getId(), userId)));
    When("^the user (\\d+) tries to reserve the slot$", (Long userId) ->
      storeException(() -> reserveSlotUseCase.reserveSlot(ReserveSlotCommand.of(slotCreated.getId(), userId))));
    Then("^the slot should be reserved$", () -> {
      SlotView slot = slotManagerComponent.find(slotCreated.getId());
      assertThat(slot.getState()).isEqualTo(SlotState.RESERVED);
    });
    Then("^a notification of a slot reserved by user (\\d+) should be published$", (Long userId) -> {
      SlotReservedEvent event = verifyAndGetMessageOfType(SlotReservedEvent.class);
      assertThat(event.getSlotId()).isEqualTo(slotCreated.getId());
      assertThat(event.getUserId()).isEqualTo(userId);
    });
    Then("^the user should get a notification that the slot is already reserved$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotAlreadyReservedException.class);
      SlotAlreadyReservedException exception = (SlotAlreadyReservedException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    Then("^the user should be notified the slot is still in auction$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotPendingAuctionException.class);
      SlotPendingAuctionException exception = (SlotPendingAuctionException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    Then("^the user should get a notification that the slot is already started$", () -> {
      assertThat(exceptionThrown).isInstanceOf(SlotNotAvailableException.class);
      SlotNotAvailableException exception = (SlotNotAvailableException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
  }
}
