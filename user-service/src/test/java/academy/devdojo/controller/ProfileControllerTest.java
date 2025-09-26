package academy.devdojo.controller;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.domain.Profile;
import academy.devdojo.repository.ProfileRepository;
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

import java.util.Optional;

@WebMvcTest(controllers = ProfileController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProfileControllerTest {

    @MockBean
    private UserRepository userRepository;

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
    @DisplayName("save create a profile")
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
    @DisplayName("findAll returns a list with all profiles when argument is null")
    void findallReturnsListAllProfiles_WhenSucessful() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(profileUtils.getListProfile());

        var requestNull = fileUtils.readResourceFile("profile/get/get-profile-null-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNull));
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns a list with a profile when argument exist")
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
    @DisplayName("findById returns a profile when argument exist")
    void findByIdReturnProfile_WhenIdExist() throws Exception {

        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(profileUtils.getProfile()));

        String requestById = fileUtils.readResourceFile("profile/get/get-profile-profileById-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestById));
    }


}