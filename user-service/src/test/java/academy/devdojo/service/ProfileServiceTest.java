package academy.devdojo.service;

import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.domain.Profile;
import academy.devdojo.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProfileServiceTest {


    @InjectMocks
    private ProfileService service;

    @InjectMocks
    private ProfileUtils profileUtils;

    @Mock
    private ProfileRepository repository;

    @BeforeEach
    void init() {

        profileUtils.getListProfile();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a lis with all profiles whn argument is null")
    void findAllRetunrsListAllProfiles_WhenSucessful() {

        BDDMockito.when(repository.findAll()).thenReturn(profileUtils.getListProfile());

        var profiles = service.findAll(null);

        Assertions.assertThat(profiles).isNotEmpty().hasSameElementsAs(profileUtils.getListProfile());
    }

    @Test
    @Order(2)
    @DisplayName("findByName returns a list with pofile when name exist")
    void findAllReturnsListWithProfile_WhenNameExist() {

        var profileName = profileUtils.getListProfile().getFirst();

        var profiles = Collections.singletonList(profileName);

        BDDMockito.when(repository.findByName(profileName.getName())).thenReturn(profiles);

        var profile = service.findAll(profileName.getName());

        Assertions.assertThat(profile).containsAll(profiles);
    }

    @Test
    @Order(3)
    @DisplayName("finById returns a profile when id exist")
    void findByIdReturnsProfile_WhenIdExist() {

        var profileId = profileUtils.getListProfile().getFirst();

        BDDMockito.when(repository.findById(profileId.getId())).thenReturn(Optional.of(profileId));

        var response = service.findById(profileId.getId());

        Assertions.assertThat(response).isEqualTo(profileId);
    }

    @Test
    @Order(4)
    @DisplayName("findById throws ResponseStatusException")
    void findByIdThorsResponseStatusException_WhenIdNotExist() {

        var profileById = profileUtils.getListProfile().getFirst();

        BDDMockito.when(repository.findById(profileById.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findById(profileById.getId())).isInstanceOf(ResponseStatusException.class);
    }


    @Test
    @Order(5)
    @DisplayName("save create a new profile")
    void saveCreateProfile_WhenSucessful() {

        var profileToCreate = Profile.builder().id(6L).name("test6").description("descriptionTes6").build();

        BDDMockito.when(repository.save(profileToCreate)).thenReturn(profileToCreate);

        var profileSaved = service.save(profileToCreate);

        Assertions.assertThat(profileSaved).isEqualTo(profileToCreate);
    }


}