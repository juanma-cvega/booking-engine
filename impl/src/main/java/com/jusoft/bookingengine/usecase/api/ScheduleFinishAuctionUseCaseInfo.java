package com.jusoft.bookingengine.usecase.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class ScheduleFinishAuctionUseCaseInfo {

  private final long auctionId;
  @NonNull
  private final OpenDate openDate;
}
