package academy.devdojo.Controller;


import academy.devdojo.Domain.User;
import academy.devdojo.Mapper.UserMapper;
import academy.devdojo.Response.UserGetResponse;
import academy.devdojo.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;
    private final UserMapper mapper;


    @GetMapping()
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String name){
        log.info("Request to list with all users '{}'", name);

        var users = service.findAll(name);

        var response = mapper.toListUserGetResponse(users);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id){

        var userById = service.findByIdOrThrowsNotFoundException(id);

        var response = mapper.toAnimeGetResponse(userById);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Void> delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }







}
