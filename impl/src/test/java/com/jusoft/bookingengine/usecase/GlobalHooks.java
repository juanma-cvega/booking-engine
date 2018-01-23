package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.holder.DataHolder;
import cucumber.api.java.Before;

public class GlobalHooks {

  @Before
  public void clear() {
    DataHolder.clear();
  }
}
