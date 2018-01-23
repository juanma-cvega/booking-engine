package com.jusoft.bookingengine.component.member.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data
public class CreateMemberCommand implements Command {

  private final long userId;
  private final long clubId;
}
