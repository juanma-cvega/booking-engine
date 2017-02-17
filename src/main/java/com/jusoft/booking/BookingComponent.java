package com.jusoft.booking;

import java.util.List;

public interface BookingComponent {

    void add(Long userId, Long roomId, Long slotId);

    void remove(Long userId, Long slotId);

    List<BookingResource> getBookingsFor(Long userId);
}
