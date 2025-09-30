package academy.devdojo.controller;


import academy.devdojo.mapper.UserMapper;
import academy.devdojo.request.UserPostRequest;
import academy.devdojo.request.UserPutRequest;
import academy.devdojo.response.UserGetResponse;
import academy.devdojo.service.UserService;
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
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;
    private final UserMapper mapper;


    @GetMapping()
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String name) {

        var users = service.findAll(name);

        var response = mapper.toListUserGetResponse(users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<UserGetResponse>> findAllPage(Pageable pageable) {

        var response = service.findAllPage(pageable).map(mapper::toUserGetResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.info("Request to find By id '{}'", id);

        var userById = service.findByIdOrThrowsNotFoundException(id);

        var response = mapper.toUserGetResponse(userById);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGetResponse> save(@RequestBody @Valid UserPostRequest userPostRequest) {

        var request = mapper.toUser(userPostRequest);

        var saved = service.save(request);

        var response = mapper.toUserGetResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest putRequest) {

        var request = mapper.toUser(putRequest);

        service.update(request);

        return ResponseEntity.noContent().build();

    }


}
