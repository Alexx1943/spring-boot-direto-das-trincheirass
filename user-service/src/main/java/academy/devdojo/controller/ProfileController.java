package academy.devdojo.controller;


import academy.devdojo.mapper.ProfileMapper;
import academy.devdojo.dto.post.ProfilePostRequest;
import academy.devdojo.dto.get.ProfileGetResponse;
import academy.devdojo.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/profiles")
@RestController
public class ProfileController {

    private final ProfileService service;
    private final ProfileMapper mapper;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileGetResponse> save(@Valid @RequestBody ProfilePostRequest profilePostRequest){

        var request = mapper.toPostProfile(profilePostRequest);

        var saved = service.save(request);

        var response = mapper.toGetProfileResponse(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping()
    public ResponseEntity<List<ProfileGetResponse>> findAll(@RequestParam(required = false) String name){

        var profiles = service.findAll(name);

        var response = mapper.toGetListProfileResponse(profiles);

        return ResponseEntity.ok(response);


    }

    @GetMapping("{id}")
    public ResponseEntity<ProfileGetResponse> findById(@PathVariable(required = false) Long id){

        var profileById = service.findById(id);

        var response = mapper.toGetProfileResponse(profileById);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
