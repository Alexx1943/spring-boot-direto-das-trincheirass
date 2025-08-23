package academy.devdojo.mapper;

import academy.devdojo.domain.User;
import academy.devdojo.request.UserPostRequest;
import academy.devdojo.request.UserPutRequest;
import academy.devdojo.response.UserGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(1000))")
    User toUser(UserPostRequest userPostRequest);

    User toUser(UserPutRequest userPutRequest);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toListUserGetResponse(List<User> user);

}
