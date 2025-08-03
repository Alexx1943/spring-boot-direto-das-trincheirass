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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = UserController.class)
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

    @Autowired
    private FileUtils fileUtils;

    @SpyBean
    private UserHardCodeRepository repository;

    @BeforeEach
    void init(){
        userUtils.newUsers();
    }

    @Test
    @Order(1)
    @DisplayName("GET/v1/users return a list with all users when argument is null")
    void findAllReturnAllUsers_WhenArgumentIsNull() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var requestNull = fileUtils.readResourceFile("user/get-user-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNull));
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/users?param returns a list with found object when name exist")
    void findAllReturnListWithUser_WhenSucessful() throws Exception {

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var requestName = fileUtils.readResourceFile("user/get-user-FirstName1-name-200.json");

        var name = "FirstName1";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestName));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/users?name is not found returns a empity list")
    void findAllReturnEmpityList_WhenNameIsNotFound() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var responseEmpityList = fileUtils.readResourceFile("user/get-user-[]-name-200.json");

        var name = "[]";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseEmpityList));
    }


    @Test
    @Order(4)
    @DisplayName("GET/v1/users/1 returns a user with given id")
    void findByIdReturnUser_WhenSucessful() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var responseId = fileUtils.readResourceFile("user/get-user-id-200.json");

        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseId));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/users/99 throws ResponseStatusException 404 when user is not found")
    void findByIdThrowsResponseStatusException_WhenUserIsNotFound() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var id = 143L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @Order(6)
    @DisplayName("POST/v1/users create a user")
    void saveCreateUser_WhenSucessful() throws Exception{


        var userToSave = User.builder()
                .id(2L)
                .firstName("teste")
                .lastName("Teste")
                .email("emailTeste").build();

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var request = fileUtils.readResourceFile("user/post-request-user-FirstName5-name-200.json");
        var response = fileUtils.readResourceFile("user/post-response-user-FirstName5-name-201.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(7)
    @DisplayName("PUT/v1/users updates an user")
    void updateUpdateUser_WhenSucessful() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var request = fileUtils.readResourceFile("user/put-request-user-name-200.json");
        var id = userUtils.newUsers().getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.put(URL, id)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("PUT/v1/users throws ResponseStatusException when an user is not found")
    void updateThrowsResponseStatusException_WhenUserIsNotFound() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var response = fileUtils.readResourceFile("user/put-response-user-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(response)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @Order(9)
    @DisplayName("DELETE/v1/users/1 remove an user")
    void deleteRemoveUser_WhenSucessful() throws Exception{

        BDDMockito.when(data.getUsers()).thenReturn(userUtils.newUsers());

        var id = userUtils.newUsers().getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("DELETE/v1/users/431 throws ResponseStatusException when id is not found")
    void deleteThrowsResponseStatusException_WhenIdIsNotFound() throws Exception {

        var id = 431L;
    }




}