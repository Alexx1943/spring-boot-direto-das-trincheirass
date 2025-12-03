package academy.devdojo.repository;

import academy.devdojo.commons.UserUtils;
import academy.devdojo.config.IntegrationTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest extends IntegrationTestConfig {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserUtils userUtils;

    @Test
    @Order(1)
    @DisplayName("save creates an user")
    void saveCreatesUser_WhenSucessful() {

        var user = userUtils.user();
        var save = repository.save(user);

        Assertions.assertThat(save).hasNoNullFieldsOrProperties();
        Assertions.assertThat(save.getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    @DisplayName("findAll return a list with all users")
    @Sql("/sql/init_one_user.sql")
    void findAllReturnListAllUsers_WhenSucessful() {

        var usersList = repository.findAll();

        Assertions.assertThat(usersList).isNotEmpty();
    }


}