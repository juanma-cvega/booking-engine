package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class CreateClubCommand implements Command {

  private final String name;
  private final String description;
  private final long adminId;
}
