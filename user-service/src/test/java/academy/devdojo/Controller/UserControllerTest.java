package academy.devdojo.Controller;

import academy.devdojo.Commons.FileUtils;
import academy.devdojo.Commons.UserUtils;
import academy.devdojo.controller.UserController;
import academy.devdojo.domain.User;
import academy.devdojo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class UserControllerTest {

    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private FileUtils fileUtils;

    @MockBean
    private UserRepository repository;


    @BeforeEach
    void init() {
        userUtils.newUsers();
    }

    @Test
    @Order(1)
    @DisplayName("GET/v1/users return a list with all users when argument is null")
    void findAllReturnAllUsers_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(userUtils.newUsers());

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


        var name1 = "FirstName1";
        BDDMockito.when(repository.findByFirstNameIgnoreCase(name1)).thenReturn(userUtils.newUser());

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
    void findAllReturnEmpityList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(userUtils.newUsers());

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
    void findByIdReturnUser_WhenSucessful() throws Exception {

        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(userUtils.newUsers().getFirst()));

        var responseId = fileUtils.readResourceFile("user/get-user-id-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseId));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/users/99 throws ResponseStatusException 404 when user is not found")
    void findByIdThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {

        var id = 99999L;


        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Test
    @Order(6)
    @DisplayName("POST/v1/users create a user")
    void saveCreateUser_WhenSucessful() throws Exception {


        var userToSave = User.builder()
                .id(2L)
                .firstName("FirstName5")
                .lastName("lastname")
                .email("Test@gmail.com").build();

        BDDMockito.when(repository.save(userToSave)).thenReturn(userToSave);

        var request = fileUtils.readResourceFile("user/post-request-user-FirstName5-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(7)
    @DisplayName("PUT/v1/users updates an user")
    void updateUpdateUser_WhenSucessful() throws Exception {

        var id = userUtils.newUsers().getFirst();
        id.setFirstName("sssss");

        BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(id));
        BDDMockito.when(repository.save(id)).thenReturn(id);

        var request = fileUtils.readResourceFile("user/put-request-user-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL, id.getId())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("PUT/v1/users throws ResponseStatusException when an user is not found")
    void updateThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {

        BDDMockito.when(repository.findAll()).thenReturn(userUtils.newUsers());

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
    @DisplayName("DELETE /v1/users/{id} remove um usuário")
    void deleteRemoveUser_WhenSuccessful() throws Exception {
        var user = userUtils.newUsers().getFirst();
        var id = user.getId();

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(user));
        BDDMockito.doNothing().when(repository).delete(user);

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


    @ParameterizedTest
    @MethodSource("postUserBadRequest")
    @Order(11)
    @DisplayName("POST/v1/users returns bad request when fields are blank")
    void saveReturnBadRequest_WhenFieldsAreblank(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("putUserBadRequest")
    @Order(12)
    @DisplayName("PUT/v1/users returns bad request when fields are blank")
    void updateReturnBadRequest_WhenFieldsAreblank(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }


    private static Stream<Arguments> postUserBadRequest() {

        var allRequiredErrors = allErrors();
        var emailrequiredErros = emailErrors();

        return Stream.of(
                Arguments.of("post-request-user-blank-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-user-empity-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-user-invalid-email-400.json", emailrequiredErros)
        );
    }

    private static Stream<Arguments> putUserBadRequest() {

        var allRequiredErrorsPut = allErrors();
        allRequiredErrorsPut.add("The field 'id' cannot be null");

        return Stream.of(
                Arguments.of("put-request-user-blank-fields-400.json", allRequiredErrorsPut),
                Arguments.of("put-request-user-empity-fields-400.json", allErrors()),
                Arguments.of("put-request-user-invalid-email-400.json", emailErrors())
                );
    }

    private static List<String> allErrors() {

        var firstNameRequiredError = "The field 'firstName' is required";
        var lastNameRequiredError = "The field 'lastName' is required";
        var emailRequiredError = "The field  'email' is required";
        return  new ArrayList<>(List.of(firstNameRequiredError, lastNameRequiredError, emailRequiredError));
    }

    public static List<String> emailErrors() {

        var emailInvalidError = "The e-mail is not valid";

        return new ArrayList<>(List.of(emailInvalidError));
    }


}