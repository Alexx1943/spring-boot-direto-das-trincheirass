package academy.devdojo.mapper;


import academy.devdojo.domain.Profile;
import academy.devdojo.domain.User;
import academy.devdojo.domain.UserProfile;
import academy.devdojo.response.UserProfileGetResponse;
import academy.devdojo.response.UserProfileUserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileMapper {

    List<UserProfileGetResponse> userProfileGetResponseList(List<UserProfile> userProfiles);

    List<UserProfileUserGetResponse> userProfileGetProfileList(List<User> users);
}
