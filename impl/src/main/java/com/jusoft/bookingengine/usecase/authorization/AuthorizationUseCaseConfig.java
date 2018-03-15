package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationUseCaseConfig {

  @Autowired
  private AuthorizationManagerComponent authorizationManagerComponent;

  @Autowired
  private MemberManagerComponent memberManagerComponent;

  @Bean
  public AddBuildingTagsToClubUseCase addBuildingTagsToClubUseCase() {
    return new AddBuildingTagsToClubUseCase(authorizationManagerComponent);
  }

  @Bean
  public AddBuildingTagsToMemberUseCase addBuildingTagsToMemberUseCase() {
    return new AddBuildingTagsToMemberUseCase(authorizationManagerComponent);
  }

  @Bean
  public AddRoomTagsToClubUseCase addRoomTagsToClubUseCase() {
    return new AddRoomTagsToClubUseCase(authorizationManagerComponent);
  }

  @Bean
  public AddRoomTagsToMemberUseCase addRoomTagsToMemberUseCase() {
    return new AddRoomTagsToMemberUseCase(authorizationManagerComponent);
  }

  @Bean
  public AuthoriseUserUseCase authoriseMemberUseCase() {
    return new AuthoriseUserUseCase(authorizationManagerComponent);
  }

  @Bean
  public NewClubCreatedUseCase newClubCreatedUseCase() {
    return new NewClubCreatedUseCase(authorizationManagerComponent);
  }

  @Bean
  public NewMemberCreatedUseCase newMemberCreatedUseCase() {
    return new NewMemberCreatedUseCase(authorizationManagerComponent);
  }

  @Bean
  public ReplaceSlotAuthenticationConfigForRoomUseCase replaceSlotAuthenticationConfigForRoomUseCase() {
    return new ReplaceSlotAuthenticationConfigForRoomUseCase(authorizationManagerComponent);
  }
}
