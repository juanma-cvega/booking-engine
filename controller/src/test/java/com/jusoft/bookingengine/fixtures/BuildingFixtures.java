package com.jusoft.bookingengine.fixtures;

import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;

import com.jusoft.bookingengine.component.building.api.Address;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.controller.building.api.BuildingResource;
import com.jusoft.bookingengine.controller.building.api.CreateBuildingRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BuildingFixtures {

    public static final long BUILDING_ID = 125L;
    public static final String STREET = "any street";
    public static final String ZIP_CODE = "any zip code";
    public static final String CITY = "any city";
    public static final String BUILDING_DESCRIPTION = "building description";
    public static final Address ADDRESS = Address.of(STREET, ZIP_CODE, CITY);

    public static final CreateBuildingRequest CREATE_BUILDING_REQUEST =
            new CreateBuildingRequest(CLUB_ID, STREET, ZIP_CODE, CITY, BUILDING_DESCRIPTION);

    public static final CreateBuildingCommand CREATE_BUILDING_COMMAND =
            new CreateBuildingCommand(CLUB_ID, ADDRESS, BUILDING_DESCRIPTION);

    public static final BuildingView BUILDING_VIEW =
            new BuildingView(BUILDING_ID, CLUB_ID, ADDRESS, BUILDING_DESCRIPTION);

    public static final BuildingResource BUILDING_RESOURCE =
            new BuildingResource(
                    BUILDING_ID, CLUB_ID, STREET, ZIP_CODE, CITY, BUILDING_DESCRIPTION);
}
