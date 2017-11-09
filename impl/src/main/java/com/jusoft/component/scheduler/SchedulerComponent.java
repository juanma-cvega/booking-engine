package com.jusoft.component.scheduler;

import java.util.function.Consumer;

public interface SchedulerComponent {

  void schedule(Consumer<TaskBuilder> consumer);
}
