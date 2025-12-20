package academy.devdojo.controller;


import academy.devdojo.mapper.UserMapper;
import academy.devdojo.dto.post.UserPostRequest;
import academy.devdojo.dto.put.UserPutRequest;
import academy.devdojo.dto.get.UserGetResponse;
import academy.devdojo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@Tag(name = "User API", description = "User's endpoints")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;
    private final UserMapper mapper;


    @Operation(summary = "Get all users", description = "Get all users in the system")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String name) {

        var users = service.findAll(name);

        var response = mapper.toListUserGetResponse(users);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all users, but get pageable")
    @GetMapping("/pageable")
    public ResponseEntity<Page<UserGetResponse>> findAllPage(Pageable pageable) {

        var response = service.findAllPage(pageable).map(mapper::toUserGetResponse);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get User By Id")
    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.info("Request to find By id '{}'", id);

        var userById = service.findByIdOrThrowsNotFoundException(id);

        var response = mapper.toUserGetResponse(userById);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> save(@RequestBody @Valid UserPostRequest userPostRequest) {

        var request = mapper.toUser(userPostRequest);

        var saved = service.save(request);

        var response = mapper.toUserGetResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete User by Id ")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Update User")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest putRequest) {

        var request = mapper.toUser(putRequest);

        service.update(request);

        return ResponseEntity.noContent().build();

    }
}
