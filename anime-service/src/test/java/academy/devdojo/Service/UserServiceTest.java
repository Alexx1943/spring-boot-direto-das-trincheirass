package academy.devdojo.Service;


import academy.devdojo.Domain.User;
import academy.devdojo.Repository.UserHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {


    @InjectMocks
    private UserService service;

    @Mock
    private UserHardCodeRepository repository;

    private List<User> userList;


    @BeforeEach
    void init() {

        var test1 = User.builder().id(1L).firstName("FirstName1").lastName("lastname").email("emailTest").build();
        var test2 = User.builder().id(2L).firstName("FirstName2").lastName("lastname").email("emailTest").build();
        var test3 = User.builder().id(3L).firstName("FirstName3").lastName("lastname").email("emailTest").build();
        var test4 = User.builder().id(4L).firstName("FirstName4").lastName("lastname").email("emailTest").build();
        var test5 = User.builder().id(5L).firstName("FirstName5").lastName("lastname").email("emailTest").build();

        userList = new ArrayList<>(List.of(test1, test2, test3, test4, test5));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all users when argument iis null")
    void findAllReturnUserList_WhenArgumentIsNull() {

        BDDMockito.when(repository.findAll()).thenReturn(userList);

        var users = service.findAll(null);

        Assertions.assertThat(users).isNotEmpty().hasSameElementsAs(userList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll return a user when name exist")
    void findAllReturnUser_WhenNameExist() {

        var userName = userList.getFirst();

        var users = Collections.singletonList(userName);

        BDDMockito.when(repository.findByName(userName.getFirstName())).thenReturn(users);

        var user = service.findAll(userName.getFirstName());

        Assertions.assertThat(user).containsAll(users);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns a empity list when argument is not found")
    void findAllReturnEmpityList_WhenArgumentIsNotFound() {

        String name = "ldlwdcc";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var userNotFound = service.findAll(name);

        Assertions.assertThat(userNotFound).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById return a user when argument exist")
    void findByIdReturnUser_WhenArgumentExist() {

        var userById = userList.getFirst();

        BDDMockito.when(repository.findById(userById.getId())).thenReturn(Optional.of(userById));

        var userFind = service.findByIdOrThrowsNotFoundException(userById.getId());

        Assertions.assertThat(userFind).isEqualTo(userById);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws ResponseStatusException")
    void findByIdThrowsResponseStatusException_WhenArgumentIsNotFound() {

        var userException = userList.getFirst();

        BDDMockito.when(repository.findById(userException.getId())).thenReturn(Optional.empty());


        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowsNotFoundException(userException.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save creates a new user")
    void saveCreateUser_WhenSucessful() {

        var userToSave = User.builder().id(9L).firstName("testeSave").firstName("test").email("test").build();

        BDDMockito.when(repository.save(userToSave)).thenReturn(userToSave);

        var userSaved = service.save(userToSave);

        Assertions.assertThat(userSaved).isEqualTo(userToSave);
    }

    @Test
    @Order(7)
    @DisplayName("delete removes a user by id")
    void deleteRemoveUser_WhenSucessful() {

        var userToRemove = userList.getFirst();

        BDDMockito.when(repository.findById(userToRemove.getId())).thenReturn(Optional.of(userToRemove));
        BDDMockito.doNothing().when(repository).delete(userToRemove);

        service.delete(userToRemove.getId());

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(userToRemove.getId()));
    }

    @Test
    @Order(8)
    @DisplayName("delete throws ResponseStatusException when id not exist")
    void deleteThowsResponseStatusException_WhenIdNotExist() {

        var userToDelete = userList.getFirst();

        BDDMockito.when(repository.findById(userToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(userToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

}