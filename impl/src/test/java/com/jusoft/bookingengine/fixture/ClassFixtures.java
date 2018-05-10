package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.classmanager.api.CreateClassCommand;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.BiFunction;

@UtilityClass
public class ClassFixtures {

  public static final String CLASS_TYPE = "classType";
  public static final String CLASS_DESCRIPTION = "classDescription";
  public static final BiFunction<Long, List<Long>, CreateClassCommand> CREATE_CLASS_COMMAND_SUPPLIER = (buildingId, instructorsId) ->
    CreateClassCommand.of(buildingId, CLASS_DESCRIPTION, CLASS_TYPE, instructorsId);
}
