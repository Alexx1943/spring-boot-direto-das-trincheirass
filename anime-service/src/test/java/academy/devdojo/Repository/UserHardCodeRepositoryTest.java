package academy.devdojo.Repository;

import academy.devdojo.Domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodeRepositoryTest {

    @InjectMocks
    private UserHardCodeRepository repository;

    private List<User> userList;

    @Mock
    private UserData data;

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
    @DisplayName("findAll returns a list with all users")
    void findAllReturnsUsersList_WhenSucessful() {
        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var users = repository.findAll();

        Assertions.assertThat(users).isNotNull().hasSize(5);
    }

    @Test
    @Order(2)
    @DisplayName("findByName returns a list with a object when param exist")
    void findByNameReturnsUserListWithObject_WhentSucessful() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userByName = userList.getFirst();

        var userName = repository.findByName(userByName.getFirstName());

        Assertions.assertThat(userName).contains(userByName);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns a enpity list when param is null")
    void findByNameReturnsEmpityLis_WheNameIsNull() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userNull = repository.findByName(null);

        Assertions.assertThat(userNull).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a user user with given id")
    void findByIdReturnsUser__WhenSucessful() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userById = userList.getFirst();

        var byId = repository.findById(userById.getId());

        Assertions.assertThat(byId).isPresent().contains(userById);
    }

    @Test
    @Order(5)
    @DisplayName("save created a user")
    void saveCreatedUser_WhenSucessful() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userToCreate = User.builder().id(ThreadLocalRandom.current()
                        .nextLong()).firstName("TestSave")
                .lastName("TesSave")
                .email("TestSave").build();

        var userCreated = repository.save(userToCreate);

        Assertions.assertThat(userCreated).isEqualTo(userToCreate).hasNoNullFieldsOrProperties();

        var user = repository.findById(userCreated.getId());

        Assertions.assertThat(user).contains(userCreated);
    }

    @Test
    @Order(6)
    @DisplayName("delete remove a user")
    void deleteRemoveUser() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userToRemove = userList.getFirst();

        repository.delete(userToRemove);

        var users = repository.findAll();

        Assertions.assertThat(users).isNotEmpty().doesNotContain(userToRemove);
    }

    @Test
    @Order(7)
    @DisplayName("update updaes a user list")
    void updateUpdatesUserList() {

        BDDMockito.when(data.getUsers()).thenReturn(userList);

        var userToUpdate = userList.getFirst();

        userToUpdate.setFirstName("Testtt");

        repository.update(userToUpdate);

        Assertions.assertThat(this.userList).contains(userToUpdate);

        var userUpdated = repository.findById(userToUpdate.getId());

        Assertions.assertThat(userUpdated).isPresent();
        Assertions.assertThat(userUpdated.get().getId()).isEqualTo(userToUpdate.getId());
    }


}