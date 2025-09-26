package academy.devdojo.commons;

import academy.devdojo.domain.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileUtils {

    public List<Profile> getListProfile() {

        var test1 = Profile.builder().id(1L).name("test1").description("description1").build();
        var test2 = Profile.builder().id(2L).name("test2").description("description2").build();
        var test3 = Profile.builder().id(3L).name("test3").description("description3").build();
        var test4 = Profile.builder().id(4L).name("test4").description("description4").build();
        var test5 = Profile.builder().id(5L).name("test5").description("description5").build();

        return new ArrayList<>(List.of(test1, test2, test3, test4, test5));
    }

    public List<Profile> getSingleProfile() {

        var test1 = Profile.builder().id(1L).name("test1").description("description1").build();

        return new ArrayList<>(List.of(test1));
    }

    public Profile getProfile() {

        var test1 = Profile.builder().id(1L).name("test1").description("description1").build();

        return test1;
    }
}
