package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class ScheduledMessage implements InfrastructureMessage {

    @NonNull private final InfrastructureMessage message;

    @NonNull private final ZonedDateTime executionTime;
}
