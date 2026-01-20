package com.jusoft.bookingengine.component.auction.api;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(exclude = "creationTime")
public class Bid {

    private final long userId;

    @NonNull private final ZonedDateTime creationTime;
}
