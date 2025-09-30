package academy.devdojo.controller;

import academy.devdojo.domain.User;
import academy.devdojo.mapper.UserMapper;
import academy.devdojo.response.UserGetResponse;
import academy.devdojo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user-profiles")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(String name){

        var userProfiles = service.findAll(name);

        var responseFindAll = mapper.toListUserGetResponse(userProfiles);

        return ResponseEntity.ok(responseFindAll);
    }
}
