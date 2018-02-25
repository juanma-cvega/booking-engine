package com.jusoft.bookingengine.component.member.api;

import com.jusoft.bookingengine.component.member.Role;
import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class MemberView {

  private final long id;
  private final long userId;
  private final long clubId;
  private final PersonalInfo personalInfo;
  private final List<Role> roles;
}
