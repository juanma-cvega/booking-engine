package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.MainConfig;
import com.jusoft.bookingengine.component.room.Room;
import com.jusoft.bookingengine.component.room.RoomFixtures;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jusoft.bookingengine.component.room.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.component.room.RoomFixtures.IS_ACTIVE;
import static com.jusoft.bookingengine.component.room.RoomFixtures.NO_AUCTION_CONFIG;
import static com.jusoft.bookingengine.component.room.RoomFixtures.OPEN_TIMES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class)
public class RoomUseCaseTest {

  @MockBean
  private MessagePublisher messagePublisher;

  @Captor
  private ArgumentCaptor<RoomCreatedEvent> roomCreatedEventCaptor;

  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private RoomUseCase roomUseCase;

  @Test
  public void use_case_create_room_creates_room_and_publishes_room_created_event() {
    Room room = roomUseCase.createRoom(RoomFixtures.CREATE_ROOM_COMMAND);

    Room storedRoom = roomComponent.find(room.getId());
    verify(messagePublisher).publish(roomCreatedEventCaptor.capture());
    RoomCreatedEvent roomCreatedEvent = roomCreatedEventCaptor.getValue();
    assertThat(roomCreatedEvent.getRoomId())
      .isEqualTo(room.getId())
      .isEqualTo(storedRoom.getId());
    assertThat(roomCreatedEvent.getMaxSlots())
      .isEqualTo(room.getMaxSlots())
      .isEqualTo(storedRoom.getMaxSlots());
    assertThat(roomCreatedEvent.getSlotDurationInMinutes())
      .isEqualTo(room.getSlotDurationInMinutes())
      .isEqualTo(storedRoom.getSlotDurationInMinutes());
    assertThat(roomCreatedEvent.getAvailableDays())
      .isEqualTo(AVAILABLE_DAYS)
      .isEqualTo(storedRoom.getAvailableDays());
    assertThat(roomCreatedEvent.getOpenTimesPerDay())
      .isEqualTo(OPEN_TIMES)
      .isEqualTo(storedRoom.getOpenTimesPerDay());
    assertThat(roomCreatedEvent.isActive())
      .isEqualTo(IS_ACTIVE)
      .isEqualTo(storedRoom.isActive());
    assertThat(room.getAuctionConfigInfo()).isEqualTo(NO_AUCTION_CONFIG);
  }
}
