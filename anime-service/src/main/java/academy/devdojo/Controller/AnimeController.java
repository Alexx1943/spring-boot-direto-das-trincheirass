package academy.devdojo.Controller;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Mapper.AnimeMapper;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Request.AnimePutRequest;
import academy.devdojo.Response.AnimeGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;


    @GetMapping("listAll")
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {

        var animes = Anime.getAnime();
        var animeGetResponseList = MAPPER.toListAnimeGetResponse(animes);
        if (name == null) return ResponseEntity.ok(animeGetResponseList);

        var response = animeGetResponseList.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(response);

    }

    @GetMapping("findByName")
    public ResponseEntity<AnimeGetResponse> findaByName(@RequestParam String name) {


        var animeGetResponse = Anime.getAnime()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElse(null);

        return ResponseEntity.ok(animeGetResponse);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {

        var animeGetResponse = Anime.getAnime()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElse(null);

        return ResponseEntity.ok().body(animeGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnimeGetResponse> addAnime(@RequestBody AnimePostRequest animePostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var request = MAPPER.toAnime(animePostRequest);
        var response = MAPPER.toAnimeGetResponse(request);

        Anime.getAnime().add(request);

        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id){
        log.info("Request to delete producer by id:  {}", id);
        var animeDelete = Anime.getAnime().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "producer not found"));

        Anime.getAnime().remove(animeDelete);

        return ResponseEntity.noContent().build();


    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request){

        var animeDeleted = Anime.getAnime().stream()
                .filter(anime -> anime.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        var animeUpdated = MAPPER.toAnime(request);

        Anime.getAnime().remove(animeDeleted);
        Anime.getAnime().add(animeUpdated);

        return ResponseEntity.noContent().build();
    }

}
