package com.jusoft.bookingengine.component.classmanager.api;

import java.util.List;

public interface ClassManagerComponent {

    ClassView create(CreateClassCommand command);

    ClassView find(long classId);

    List<ClassView> findByBuilding(long buildingId);

    void remove(long classId);

    void addInstructor(AddInstructorCommand command);

    void removeInstructor(RemoveInstructorCommand command);

    void registerRoom(RegisterRoomCommand command);

    void unregisterRoom(UnregisterRoomCommand command);
}
