package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;

@Data(staticConstructor = "of")
public class ChangeAccessToAuctionsCommand implements Command {

  private final long memberId;
  private final long buildingId;
  private final long roomId;
}
