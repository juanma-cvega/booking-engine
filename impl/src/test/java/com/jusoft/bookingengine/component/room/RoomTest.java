package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.fixtures.CommonFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.jusoft.bookingengine.component.room.RoomFixtures.ACTIVE;
import static com.jusoft.bookingengine.component.room.RoomFixtures.AUCTION_STRATEGY_TYPE;
import static com.jusoft.bookingengine.component.room.RoomFixtures.AUCTION_TIME;
import static com.jusoft.bookingengine.component.room.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.bookingengine.component.room.RoomFixtures.LESS_BOOKINGS_WITHIN_PERIOD_CONFIG;
import static com.jusoft.bookingengine.component.room.RoomFixtures.MAX_SLOTS;
import static com.jusoft.bookingengine.component.room.RoomFixtures.OPEN_TIMES;
import static com.jusoft.bookingengine.component.room.RoomFixtures.SLOT_DURATION_IN_MINUTES;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

  private final Room room;

  public RoomTest() {
    room = new Room(CommonFixtures.ROOM_ID,
      MAX_SLOTS,
      SLOT_DURATION_IN_MINUTES,
      OPEN_TIMES,
      AVAILABLE_DAYS,
      ACTIVE,
      AUCTION_TIME,
      AUCTION_STRATEGY_TYPE,
      LESS_BOOKINGS_WITHIN_PERIOD_CONFIG);
  }

  @Test
  public void test() {
//        room.findUpcomingSlot()
  }
}
