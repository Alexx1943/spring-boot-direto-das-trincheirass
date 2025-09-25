package academy.devdojo.controller;

import academy.devdojo.Commons.FileUtils;
import academy.devdojo.Commons.ProfileUtils;
import academy.devdojo.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProfileController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProfileControllerTest {

    private static final String URL = "/v1/profiles";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProfileUtils profileUtils;

    @Autowired
    private FileUtils fileUtils;

    @MockBean
    private ProfileRepository repository;

    @BeforeEach
    void init(){

        profileUtils.getListProfile();
    }


}