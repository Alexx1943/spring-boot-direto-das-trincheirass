package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(name);
    }

    public User findByIdOrThrowsNotFoundException(Long id) {

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }


    public User save(User user) {

        assetEmaildoesNotExist(user.getEmail());

        return repository.save(user);
    }

    public void delete(Long id) {

        var userToDelete = findByIdOrThrowsNotFoundException(id);

        repository.delete(userToDelete);
    }

    public void update(User user) {

        assertUserExist(user.getId());

        assetEmaildoesNotExist(user.getEmail(), user.getId());

        repository.save(user);
    }

    public void assertUserExist(Long id) {

        findByIdOrThrowsNotFoundException(id);
    }

    public void assetEmaildoesNotExist(String email) {

        repository.findByEmail(email).ifPresent(this::throwEmailExistException);
    }

    public void assetEmaildoesNotExist(String email, Long id) {

        repository.findByEmailAndIdNot(email, id).ifPresent(this::throwEmailExistException);
    }

    private void throwEmailExistException(User user) {

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
    }

}
