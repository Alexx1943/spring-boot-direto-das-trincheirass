package academy.devdojo.Controller;


import academy.devdojo.Mapper.UserMapper;
import academy.devdojo.Request.UserPostRequest;
import academy.devdojo.Request.UserPutRequest;
import academy.devdojo.Response.UserGetResponse;
import academy.devdojo.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        log.info("Request to find By id '{}'", id);

        var userById = service.findByIdOrThrowsNotFoundException(id);

        var response = mapper.toUserGetResponse(userById);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> save(@RequestBody @Valid  UserPostRequest userPostRequest){

        var request = mapper.toUser(userPostRequest);

        var saved = service.save(request);

        UserGetResponse response = mapper.toUserGetResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid  UserPutRequest putRequest){

        var request = mapper.toUser(putRequest);

        service.update(request);

        return ResponseEntity.noContent().build();

    }







}
