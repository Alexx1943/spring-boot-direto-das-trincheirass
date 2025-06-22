package academy.devdojo.Controller;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Mapper.AnimeMapper;
import academy.devdojo.Request.AnimePostRequest;
import academy.devdojo.Response.AnimeGetResponse;
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
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;


    @GetMapping("listAll")
    public List<Anime> listAll() {
        return Anime.getAnime();
    }

    @GetMapping("findByName")
    public ResponseEntity<AnimeGetResponse> findaByName(@RequestParam String name) {


        var animeGetResponse = Anime.getAnime()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(MAPPER::toAnimeGetResponse)
                .orElse(null);

        return ResponseEntity.ok().body(animeGetResponse);
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

        return ResponseEntity.ok().body(response);

    }


}
