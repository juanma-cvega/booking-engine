package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.PersonalInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Member {

  private final long id;
  private final long userId;
  private final long clubId;
  private final PersonalInfo personalInfo;
  private final List<Role> roles;

  Member(long id, long userId, long clubId, PersonalInfo personalInfo, List<Role> roles) {
    this.id = id;
    this.userId = userId;
    this.clubId = clubId;
    this.personalInfo = personalInfo;
    this.roles = new ArrayList<>(roles);
  }

  public List<Role> getRoles() {
    return new ArrayList<>(roles);
  }
}
