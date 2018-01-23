package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data
public class FindJoinRequestCommand implements Command {

  private final long adminId;
  private final long clubId;
}
