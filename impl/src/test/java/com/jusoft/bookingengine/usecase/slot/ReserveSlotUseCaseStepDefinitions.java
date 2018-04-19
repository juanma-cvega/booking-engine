package com.jusoft.bookingengine.usecase.slot;

import com.google.common.collect.ImmutableList;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedReservationException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.component.slot.api.SlotPendingAuctionException;
import com.jusoft.bookingengine.component.slot.api.SlotReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class ReserveSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  private static final String TAG_ONLY_IN_CLUB = "TAG_ONLY_IN_CLUB";

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;
  @Autowired
  private SlotManagerComponent slotManagerComponent;

  @Autowired
  private ReserveSlotUseCase reserveSlotUseCase;

  public ReserveSlotUseCaseStepDefinitions() {
    Given("^user (\\d+) is the member (.*) of the club created$", (Long userId, Long memberId) ->
      authorizationManagerComponent.createMember(memberId, userId, roomCreated.getClubId()));
    Given("^the room created requires authorization to use it$", () ->
      authorizationManagerComponent.addRoomTagsToClub(AddRoomTagsToClubCommand.of(
        clubCreated.getId(),
        buildingCreated.getId(),
        roomCreated.getId(),
        SlotStatus.NORMAL,
        ImmutableList.of(Tag.of(TAG_ONLY_IN_CLUB))
      )));
    Then("^the user (.*) should get a notification that he is not a member of the club$", (Long userId) -> {
      assertThat(exceptionThrown).isInstanceOf(UserNotMemberException.class);
      UserNotMemberException exception = (UserNotMemberException) exceptionThrown;
      assertThat(exception.getUserId()).isEqualTo(userId);
      assertThat(exception.getClubId()).isEqualTo(clubCreated.getId());
    });
    When("^the slot is reserved by user (\\d+)$", (Long userId) ->
      reserveSlotUseCase.reserveSlot(slotCreated.getId(), userId));
    When("^the user (\\d+) tries to reserve the slot$", (Long userId) ->
      storeException(() -> reserveSlotUseCase.reserveSlot(slotCreated.getId(), userId)));
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
      assertThat(exceptionThrown).isInstanceOf(SlotNotOpenException.class);
      SlotNotOpenException exception = (SlotNotOpenException) exceptionThrown;
      assertThat(exception.getSlotId()).isEqualTo(slotCreated.getId());
    });
    Then("^the user (\\d+) should receive a notification he is not authorized to use the room created$", (Long userId) -> {
      assertThat(exceptionThrown).isNotNull();
      assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
      UnauthorizedReservationException exception = (UnauthorizedReservationException) exceptionThrown;
      assertThat(exception.getBuildingId()).isEqualTo(roomCreated.getBuildingId());
      assertThat(exception.getClubId()).isEqualTo(roomCreated.getClubId());
      assertThat(exception.getRoomId()).isEqualTo(roomCreated.getId());
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
  }
}
