package academy.devdojo.service;

import academy.devdojo.domain.Profile;
import academy.devdojo.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;

    public List<Profile> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Profile findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
    }

    public Profile save(Profile profile) {

        return repository.save(profile);
    }

    public void delete(Long id){

        repository.deleteById(id);
    }

}
