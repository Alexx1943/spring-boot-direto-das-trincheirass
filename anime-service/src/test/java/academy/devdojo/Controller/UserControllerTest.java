package academy.devdojo.Controller;

import academy.devdojo.Commons.FileUtils;
import academy.devdojo.Commons.UserUtils;
import academy.devdojo.Domain.User;
import academy.devdojo.Repository.UserData;
import academy.devdojo.Repository.UserHardCodeRepository;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class UserControllerTest {

    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserData data;

    @Autowired
    private UserUtils userUtils;

    private FileUtils fileUtils;

    @SpyBean
    private UserHardCodeRepository repository;

    @BeforeEach
    void init(){
        userUtils.userList();
    }

    @Test
    @Order(1)
    @DisplayName("GET/v1/users return a list with all users when argument is null")
    void findAllReturnAllUsers_WhenArgumentIsNull(){

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.userList());


    }





}