package com.jusoft.bookingengine.publisher.message;

import com.jusoft.bookingengine.publisher.InfrastructureMessage;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotRequiredMessage implements InfrastructureMessage {

    private final long roomId;
}
