package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomAccessRole implements RoomRole {

  private final long roomId;

  private final Map<Long, RoomAccessRole> roles = new ConcurrentHashMap<>();

  @Override
  public boolean satisfiesFor(long id) {
    return this.roomId == id;
  }

  public RoomRole getInstanceFor(long id) {
    return roles.computeIfAbsent(id, RoomAccessRole::new);
  }
}
