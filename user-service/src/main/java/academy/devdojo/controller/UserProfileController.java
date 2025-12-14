package academy.devdojo.controller;

import academy.devdojo.mapper.UserProfileMapper;
import academy.devdojo.dto.get.UserProfileGetResponse;
import academy.devdojo.dto.get.UserProfileUserGetResponse;
import academy.devdojo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user-profiles")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService service;
    private final UserProfileMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserProfileGetResponse>> findAll(String name){

        var userProfiles = service.findAll();

        var userProfileGetResponses = mapper.userProfileGetResponseList(userProfiles);

        return ResponseEntity.ok(userProfileGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<UserProfileUserGetResponse>> findBrProfile(@PathVariable Long id){

        var requestByProfile = service.findByProfile(id);

        var responseByProfile = mapper.userProfileGetProfileList(requestByProfile);


        return ResponseEntity.ok().body(responseByProfile);
    }
}
