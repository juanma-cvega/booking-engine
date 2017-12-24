package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import com.jusoft.bookingengine.publisher.Message;
import com.jusoft.bookingengine.publisher.message.AuctionFinishedMessage;
import com.jusoft.bookingengine.publisher.message.AuctionWinnerFoundMessage;
import com.jusoft.bookingengine.publisher.message.BookingCreatedMessage;
import com.jusoft.bookingengine.publisher.message.OpenNextSlotMessage;
import com.jusoft.bookingengine.publisher.message.RoomCreatedMessage;
import com.jusoft.bookingengine.publisher.message.SlotCreatedMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jusoft.bookingengine.fixtures.AuctionFixtures.AUCTION_FINISHED_EVENT;
import static com.jusoft.bookingengine.fixtures.AuctionFixtures.AUCTION_WINNER_FOUND_EVENT;
import static com.jusoft.bookingengine.fixtures.BookingFixtures.BOOKING_CREATED_EVENT;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_CREATED_EVENT;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.OPEN_NEXT_SLOT_COMMAND;
import static com.jusoft.bookingengine.fixtures.SlotFixtures.SLOT_CREATED_EVENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InfrastructureMessageFactoryConfig.class)
public class InfrastructureMessageFactoryTest {

  @Autowired
  private InfrastructureMessageFactory infrastructureMessageFactory;

  @Test
  public void create_from_auction_finished_event_should_create_an_auction_finished_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(AUCTION_FINISHED_EVENT);

    assertThat(message).isInstanceOf(AuctionFinishedMessage.class);
    AuctionFinishedMessage auctionFinishedMessage = (AuctionFinishedMessage) message;
    assertThat(auctionFinishedMessage.getAuctionId()).isEqualTo(AUCTION_FINISHED_EVENT.getAuctionId());
    assertThat(auctionFinishedMessage.getRoomId()).isEqualTo(AUCTION_FINISHED_EVENT.getRoomId());
    assertThat(auctionFinishedMessage.getSlotId()).isEqualTo(AUCTION_FINISHED_EVENT.getSlotId());
  }

  @Test
  public void create_from_auction_winner_found_event_should_create_an_auction_winner_found_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(AUCTION_WINNER_FOUND_EVENT);

    assertThat(message).isInstanceOf(AuctionWinnerFoundMessage.class);
    AuctionWinnerFoundMessage auctionWinnerFoundEvent = (AuctionWinnerFoundMessage) message;
    assertThat(auctionWinnerFoundEvent.getAuctionId()).isEqualTo(AUCTION_WINNER_FOUND_EVENT.getAuctionId());
    assertThat(auctionWinnerFoundEvent.getRoomId()).isEqualTo(AUCTION_WINNER_FOUND_EVENT.getRoomId());
    assertThat(auctionWinnerFoundEvent.getSlotId()).isEqualTo(AUCTION_WINNER_FOUND_EVENT.getSlotId());
    assertThat(auctionWinnerFoundEvent.getRoomId()).isEqualTo(AUCTION_WINNER_FOUND_EVENT.getRoomId());
  }

  @Test
  public void create_from_booking_created_event_should_create_a_booking_created_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(BOOKING_CREATED_EVENT);

    assertThat(message).isInstanceOf(BookingCreatedMessage.class);
    BookingCreatedMessage bookingCreatedEvent = (BookingCreatedMessage) message;
    assertThat(bookingCreatedEvent.getBookingId()).isEqualTo(BOOKING_CREATED_EVENT.getBookingId());
    assertThat(bookingCreatedEvent.getUserId()).isEqualTo(BOOKING_CREATED_EVENT.getUserId());
    assertThat(bookingCreatedEvent.getSlotId()).isEqualTo(BOOKING_CREATED_EVENT.getSlotId());
  }

  @Test
  public void create_from_open_next_slot_command_should_create_an_open_next_slot_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(OPEN_NEXT_SLOT_COMMAND);

    assertThat(message).isInstanceOf(OpenNextSlotMessage.class);
    OpenNextSlotMessage openNextSlotMessage = (OpenNextSlotMessage) message;
    assertThat(openNextSlotMessage.getRoomId()).isEqualTo(OPEN_NEXT_SLOT_COMMAND.getRoomId());
  }

  @Test
  public void create_from_room_created_event_should_create_a_room_created_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(ROOM_CREATED_EVENT);

    assertThat(message).isInstanceOf(RoomCreatedMessage.class);
    RoomCreatedMessage roomCreatedEvent = (RoomCreatedMessage) message;
    assertThat(roomCreatedEvent.getRoomId()).isEqualTo(ROOM_CREATED_EVENT.getRoomId());
    assertThat(roomCreatedEvent.getMaxSlots()).isEqualTo(ROOM_CREATED_EVENT.getMaxSlots());
    assertThat(roomCreatedEvent.getSlotDurationInMinutes()).isEqualTo(ROOM_CREATED_EVENT.getSlotDurationInMinutes());
    assertThat(roomCreatedEvent.getAvailableDays()).isEqualTo(ROOM_CREATED_EVENT.getAvailableDays());
    assertThat(roomCreatedEvent.getOpenTimesPerDay()).isEqualTo(ROOM_CREATED_EVENT.getOpenTimesPerDay());
  }

  @Test
  public void create_from_slot_created_event_should_create_a_slot_created_message() {
    InfrastructureMessage message = infrastructureMessageFactory.createFrom(SLOT_CREATED_EVENT);

    assertThat(message).isInstanceOf(SlotCreatedMessage.class);
    SlotCreatedMessage slotCreatedEvent = (SlotCreatedMessage) message;
    assertThat(slotCreatedEvent.getRoomId()).isEqualTo(SLOT_CREATED_EVENT.getRoomId());
    assertThat(slotCreatedEvent.getSlotId()).isEqualTo(SLOT_CREATED_EVENT.getSlotId());
    assertThat(slotCreatedEvent.getStartDate()).isEqualTo(SLOT_CREATED_EVENT.getStartDate());
    assertThat(slotCreatedEvent.getEndDate()).isEqualTo(SLOT_CREATED_EVENT.getEndDate());
  }

  @Test
  public void create_from_unknown_type_should_fail() {
    assertThatThrownBy(() -> infrastructureMessageFactory.createFrom(new UnknownMessage()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Factory not found for message");
  }

  private static class UnknownMessage implements Message {
  }
}
