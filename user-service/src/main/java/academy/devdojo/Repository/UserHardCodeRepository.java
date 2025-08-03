package academy.devdojo.Repository;


import academy.devdojo.Domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@RequiredArgsConstructor
@Log4j2
public class UserHardCodeRepository {

    private final UserData data;

    public List<User> findAll() {

        return data.getUsers();
    }

    public List<User> findByName(String name) {

        var userByName = data.getUsers().stream().filter(user -> user.getFirstName().equalsIgnoreCase(name)).toList();

        return userByName;
    }

    public Optional<User> findById(Long id) {

        var userById = data.getUsers().stream().filter(user -> user.getId().equals(id)).findFirst();

        return userById;
    }

    public User save(User user) {

        var userToSave = data.getUsers().add(user);

        return user;
    }

    public void delete(User user) {

        var userToDelete = data.getUsers().remove(user);
    }

    public void update(User user) {

        delete(user);
        save(user);
    }

}
