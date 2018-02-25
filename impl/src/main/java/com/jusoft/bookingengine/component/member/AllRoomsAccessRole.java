package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AllRoomsAccessRole implements RoomRole {

  private final Map<Long, AllRoomsAccessRole> roles = new ConcurrentHashMap<>();

  @Override
  public boolean satisfiesFor(long buildingId, long roomId) {
    return true;
  }

  public RoomRole getInstanceFor(long buildingId, long roomId) {
    return roles.computeIfAbsent(roomId, );
  }
}
