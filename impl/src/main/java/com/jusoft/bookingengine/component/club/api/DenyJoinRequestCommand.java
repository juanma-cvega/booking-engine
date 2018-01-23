package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data
public class DenyJoinRequestCommand implements Command {

  private final long joinRequestId;
  private final long clubId;
  private final long adminId;
}
