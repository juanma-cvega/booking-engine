package com.jusoft.bookingengine.fixtures;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.USER_ID_1;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.controller.club.api.CreateClubRequest;
import com.jusoft.bookingengine.controller.club.api.CreateJoinRequestRequest;
import com.jusoft.bookingengine.controller.club.api.ReviewJoinRequestRequest;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClubFixtures {

    public static final long CLUB_ID = 555;
    public static final String CLUB_NAME = "The Booking Club";
    public static final String CLUB_DESCRIPTION = "A club to book things";
    public static final long ADMIN_ID = 777;
    public static final long JOIN_REQUEST_ID = 888;

    public static final CreateClubCommand CREATE_CLUB_COMMAND =
            new CreateClubCommand(CLUB_NAME, CLUB_DESCRIPTION, ADMIN_ID);
    public static final ClubView CLUB_VIEW =
            new ClubView(CLUB_ID, CLUB_NAME, CLUB_DESCRIPTION, Set.of(ADMIN_ID));
    public static final CreateClubRequest CREATE_CLUB_REQUEST =
            new CreateClubRequest(CLUB_NAME, CLUB_DESCRIPTION, ADMIN_ID);

    public static final CreateJoinRequestCommand CREATE_JOIN_REQUEST_COMMAND =
            new CreateJoinRequestCommand(CLUB_ID, USER_ID_1);
    public static final JoinRequest JOIN_REQUEST = new JoinRequest(JOIN_REQUEST_ID, USER_ID_1);
    public static final CreateJoinRequestRequest CREATE_JOIN_REQUEST_REQUEST =
            new CreateJoinRequestRequest(USER_ID_1);

    public static final AcceptJoinRequestCommand ACCEPT_JOIN_REQUEST_COMMAND =
            new AcceptJoinRequestCommand(JOIN_REQUEST_ID, CLUB_ID, ADMIN_ID);
    public static final DenyJoinRequestCommand DENY_JOIN_REQUEST_COMMAND =
            new DenyJoinRequestCommand(JOIN_REQUEST_ID, CLUB_ID, ADMIN_ID);
    public static final ReviewJoinRequestRequest REVIEW_JOIN_REQUEST_REQUEST =
            new ReviewJoinRequestRequest(ADMIN_ID);
}
