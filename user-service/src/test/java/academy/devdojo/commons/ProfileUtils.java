package academy.devdojo.commons;

import academy.devdojo.domain.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileUtils {

    public List<Profile> getListProfile() {

        var test1 = Profile.builder().id(1L).name("Admin").description("Administrator").build();
        var test2 = Profile.builder().id(2L).name("Regular").description("Regular User").build();

        return new ArrayList<>(List.of(test1, test2));
    }

    public List<Profile> getSingleProfile() {

        var test1 = Profile.builder().id(1L).name("Admin").description("Administrator").build();

        return new ArrayList<>(List.of(test1));
    }

    public Profile getProfile() {

        var test1 = Profile.builder().id(1L).name("Admin").description("Administrator").build();

        return test1;
    }
}
