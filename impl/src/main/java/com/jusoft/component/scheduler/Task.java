package com.jusoft.component.scheduler;

import com.jusoft.component.shared.Message;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
class Task {

  @NonNull
  private final ZonedDateTime executionTime;
  @NonNull
  private final Message message;
}
