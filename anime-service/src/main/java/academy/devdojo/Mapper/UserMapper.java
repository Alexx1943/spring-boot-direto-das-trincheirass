package academy.devdojo.Mapper;

import academy.devdojo.Domain.User;
import academy.devdojo.Request.UserPostRequest;
import academy.devdojo.Request.UserPutRequest;
import academy.devdojo.Response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest userPutRequest);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toListUserGetResponse(List<User> user);

}
