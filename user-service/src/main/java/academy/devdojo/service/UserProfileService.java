package academy.devdojo.service;


import academy.devdojo.domain.UserProfile;
import academy.devdojo.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public List<UserProfile> findAll(){

        return repository.findAll();
    }

    public UserProfile save(UserProfile user){

        return repository.save(user);
    }

}
