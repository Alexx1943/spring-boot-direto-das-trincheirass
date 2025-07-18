package academy.devdojo.Controller;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Mapper.AnimeMapper;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Request.AnimePutRequest;
import academy.devdojo.Response.AnimeGetResponse;
import academy.devdojo.Service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper mapper;
    private final AnimeService service;


    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.info("Resquet to list all animes '{}", name);

        var animes = service.findAll(name);

        var response = mapper.toListAnimeGetResponse(animes);

        return ResponseEntity.ok(response);

    }


    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request to find By id '{}'", id);

        var animeById = service.findByIdOrThrowNotFound(id);

        var animeResponse = mapper.toAnimeGetResponse(animeById);

        return ResponseEntity.ok().body(animeResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<AnimeGetResponse> addAnime(@RequestBody AnimePostRequest animePostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var request = mapper.toAnime(animePostRequest);

        var animeSaved = service.save(request);

        var response = mapper.toAnimeGetResponse(animeSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id) {
        log.info("Request to delete producer by id:  {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();


    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {

        var animeToUpdate = mapper.toAnime(request);

        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }

}
