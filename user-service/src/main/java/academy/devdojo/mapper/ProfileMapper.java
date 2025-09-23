package academy.devdojo.mapper;

import academy.devdojo.domain.Profile;
import academy.devdojo.request.ProfilePostRequest;
import academy.devdojo.response.ProfileGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    Profile toPostProfile(ProfilePostRequest profilePostRequest);

    ProfileGetResponse toGetProfileResponse(Profile profile);

    List<ProfileGetResponse> toGetListProfileResponse(List<Profile> profiles);
}
