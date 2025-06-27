package academy.devdojo.Controller;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Mapper.AnimeMapper;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Request.AnimePutRequest;
import academy.devdojo.Response.AnimeGetResponse;
import academy.devdojo.Service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;
    private AnimeService service;

    public AnimeController() {
        this.service = new AnimeService();
    }


    @GetMapping("listAll")
    public ResponseEntity<List<AnimeGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.info("Resquet to list all animes '{}", name);

        var animes = Anime.findAll();

        var animeGetResponseList = MAPPER.toListAnimeGetResponse(animes);

        return ResponseEntity.ok(animeGetResponseList);

    }


    @GetMapping("findById/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request to find By id '{}'", id);

        var animeById = service.findByIdOrThrowNotFound(id);

        var animeResponse = MAPPER.toAnimeGetResponse(animeById);

        return ResponseEntity.ok().body(animeResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnimeGetResponse> addAnime(@RequestBody AnimePostRequest animePostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var request = MAPPER.toAnime(animePostRequest);

        var animeSaved = service.save(request);

        var response = MAPPER.toAnimeGetResponse(animeSaved);

        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id) {
        log.info("Request to delete producer by id:  {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();


    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {

        var animeToUpdate = MAPPER.toAnime(request);

        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }

}
