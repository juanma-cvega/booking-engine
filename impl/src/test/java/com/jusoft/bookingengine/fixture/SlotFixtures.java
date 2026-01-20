package com.jusoft.bookingengine.fixture;

import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;

import com.jusoft.bookingengine.component.slot.api.SlotUser;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlotFixtures {

    public static final ZonedDateTime END_TIME = ZonedDateTime.now().plus(5, ChronoUnit.DAYS);
    public static final ZonedDateTime START_TIME = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
    public static final OpenDate OPEN_DATE = OpenDate.of(START_TIME, END_TIME);
    public static final long SLOT_ID_1 = 2;
    public static final String PERSON_USER_TYPE = "personUserType";
    public static final String CLASS_USER_TYPE = "classUserType";
    public static final SlotUser SLOT_USER = SlotUser.of(USER_ID_1, PERSON_USER_TYPE);
    public static final SlotUser ANOTHER_SLOT_USER = SlotUser.of(USER_ID_1, CLASS_USER_TYPE);
    public static final AuctionConfigInfo NO_AUCTION_CONFIG_INFO =
            NoAuctionConfigInfo.getInstance();
}
