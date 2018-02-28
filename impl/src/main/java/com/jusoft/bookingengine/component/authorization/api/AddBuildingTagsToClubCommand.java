package com.jusoft.bookingengine.component.authorization.api;

import com.jusoft.bookingengine.publisher.Command;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data(staticConstructor = "of")
public class AddBuildingTagsToClubCommand implements Command {

  private final long clubId;
  private final long buildingId;
  @NonNull
  private final List<Tag> tags;
}
