package com.jusoft.bookingengine.component.member.api;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class PersonalInfo {

    @NonNull private final String name;

    @NonNull private final String surname;

    @NonNull private final LocalDateTime dateOfBirth;
}
