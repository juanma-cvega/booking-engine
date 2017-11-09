package com.jusoft.component.booking;

import com.jusoft.component.booking.api.SlotAlreadyStartedException;
import com.jusoft.component.booking.api.WrongBookingUserException;
import com.jusoft.component.slot.Slot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Booking {

    private final long id;
    private final long userId;
    @NonNull
    private final ZonedDateTime bookingTime;
    @NonNull
    private final Slot slot;

    boolean canClose(ZonedDateTime currentTime, Long requestUserId) {
        validateSlotIsOpen(currentTime);
        validateUserOwnsBooking(requestUserId);
        return true;
    }

    private void validateUserOwnsBooking(Long requestUserId) {
        if (Long.compare(requestUserId, userId) != 0) {
            throw new WrongBookingUserException(userId, userId, id);
        }
    }

    private void validateSlotIsOpen(ZonedDateTime requestTime) {
        if (slot.getStartDate().isBefore(requestTime)) {
            throw new SlotAlreadyStartedException(slot.getId(), slot.getRoomId(), slot.getStartDate());
        }
    }
}
