package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class AddBuildingTagsToMemberCommand implements Command {

  private final long memberId;
  private final long buildingId;
  @NonNull
  private final List<Tag> tags;
}
