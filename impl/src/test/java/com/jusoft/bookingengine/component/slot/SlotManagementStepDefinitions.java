//package com.jusoft.bookingengine.component.slot;
//
//import com.jusoft.bookingengine.component.AbstractStepDefinitions;
//import com.jusoft.bookingengine.fixture.CommonFixtures;
//import com.jusoft.bookingengine.component.slot.api.SlotComponent;
//import com.jusoft.bookingengine.component.slot.api.SlotNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class SlotManagementStepDefinitions extends AbstractStepDefinitions {
//
//  private static final Long ANY_SLOT_ID = 44444L;
//
//  @Autowired
//  private SlotComponent slotComponent;
//
//  private RuntimeException exceptionThrown;
//
//  public SlotManagementStepDefinitions() {
//
//    When("^a non existent slot is retrieved$", () -> storeException(() ->
//      slotComponent.find(ANY_SLOT_ID, CommonFixtures.ROOM_ID)));
//    Then("^the user should be notified the slot does not exist$", () ->
//      assertThat(exceptionThrown).isNotNull().isInstanceOf(SlotNotFoundException.class));
//  }
//
//  private void storeException(Runnable runnable) {
//    try {
//      runnable.run();
//    } catch (RuntimeException exception) {
//      exceptionThrown = exception;
//    }
//  }
//}
