package com.jusoft.bookingengine.component.member.api;

import com.jusoft.bookingengine.publisher.Message;
import lombok.Data;

@Data(staticConstructor = "of")
public class MemberCreatedEvent implements Message {

  private final long memberId;
  private final long userId;
  private final long clubId;
}
