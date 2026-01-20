package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Command;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class CreateClassCommand implements Command {

    private final long buildingId;

    @NonNull private final String description;

    @NonNull private final String type;

    private final List<Long> instructorsId;
}
