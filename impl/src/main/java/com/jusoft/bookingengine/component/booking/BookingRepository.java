package com.jusoft.bookingengine.component.booking;

import com.jusoft.bookingengine.repository.Repository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

interface BookingRepository extends Repository {

    void save(Booking newBooking);

    boolean delete(
            long bookingId,
            Predicate<Booking> predicate,
            Supplier<RuntimeException> notEntityFoundException);

    Optional<Booking> find(long bookingId);

    List<Booking> getByUser(long userId);

    List<Booking> findBookingsUntilFor(ZonedDateTime endTime, Set<Long> users);
}
