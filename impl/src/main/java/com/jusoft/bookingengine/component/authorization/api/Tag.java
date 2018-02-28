package com.jusoft.bookingengine.component.authorization.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Tag {

  private static final Map<String, Tag> values = new ConcurrentHashMap<>();

  private final String name;

  public static Tag of(String name) {
    return values.computeIfAbsent(name, Tag::new);
  }
}
