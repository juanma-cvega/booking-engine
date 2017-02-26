package com.jusoft.component.booking;

import org.junit.Test;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.fixtures.CommonFixtures.USER_ID_1;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_ID_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateBookingCommandTest {

    @Test
    public void nullUserIdFailsConstructor() {
        assertThatThrownBy(() -> new CreateBookingCommand(null, ROOM_ID, SLOT_ID_1)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void nullRoomIdFailsConstructor() {
        assertThatThrownBy(() -> new CreateBookingCommand(USER_ID_1, null, SLOT_ID_1)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void nullSlotIdFailsConstructor() {
        assertThatThrownBy(() -> new CreateBookingCommand(USER_ID_1, ROOM_ID, null)).isInstanceOf(NullPointerException.class);
    }
}
