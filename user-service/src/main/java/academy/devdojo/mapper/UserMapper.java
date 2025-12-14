package academy.devdojo.mapper;

import academy.devdojo.domain.User;
import academy.devdojo.dto.post.UserPostRequest;
import academy.devdojo.dto.put.UserPutRequest;
import academy.devdojo.dto.get.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest userPutRequest);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toListUserGetResponse(List<User> user);

}
