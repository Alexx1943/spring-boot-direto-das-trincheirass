package academy.devdojo.controller;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.domain.Profile;
import academy.devdojo.repository.ProfileRepository;
import academy.devdojo.repository.UserProfileRepository;
import academy.devdojo.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

@WebMvcTest(controllers = ProfileController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProfileControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserProfileRepository userProfileRepository;

    private static final String URL = "/v1/profiles";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileUtils profileUtils;

    @Autowired
    private FileUtils fileUtils;

    @MockBean
    private ProfileRepository repository;

    @BeforeEach
    void init() {

        profileUtils.getListProfile();
    }

    @Test
    @Order(1)
    @DisplayName("POST/v1/profiles create a profile")
    void saveCreateProfile_WheSucessful() throws Exception {

        var profileToSave = Profile.builder().id(1L).name("test1").description("description1").build();

        BDDMockito.when(repository.save(profileToSave)).thenReturn(profileToSave);

        var profileSaved = fileUtils.readResourceFile("profile/post/post-profile-save-201.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(profileSaved)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/profiles returns a list with all profiles when argument is null")
    void findAllReturnsListAllProfiles_WhenSucessful() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(profileUtils.getListProfile());

        var requestNull = fileUtils.readResourceFile("profile/get/get-profile-null-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNull));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/profiles?param returns a list with a profile when argument exist")
    void findAllReturnProfile_WhenArgumentExist() throws Exception {

        var name = "test1";

        BDDMockito.when(repository.findByName(name)).thenReturn(profileUtils.getSingleProfile());

        var requestByName = fileUtils.readResourceFile("profile/get/get-profile-profileByName-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL).param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestByName));

    }

    @Test
    @Order(4)
    @DisplayName("GET/v1/profiles?param returns a empity list when argument is not found")
    void findAllReturnEmpityList_WhenArgumenteNotFound() throws Exception {

        var name = "nameNotFound";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var requestNotFound = fileUtils.readResourceFile("profile/get/get-profile-[]-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNotFound));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/profiles/1 returns a profile when argument exist")
    void findByIdReturnProfile_WhenIdExist() throws Exception {

        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(profileUtils.getProfile()));

        var requestById = fileUtils.readResourceFile("profile/get/get-profile-profileById-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestById));
    }

    @Test
    @Order(6)
    @DisplayName("GET/v1/profiles/90 throws ResponseStatusException 404 when profile is not found")
    void findByIdThrowsResponseStatusException_WhenProfileIsNotFound() throws Exception {

        var id = 90L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Profile not found"));
    }


}