package academy.devdojo.Controller;

import academy.devdojo.Dominio.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {


    @GetMapping("listAll")
    public List<Anime> listAll() {
        return Anime.getAnime();
    }

    @GetMapping("findByName")
    public List<Anime> findaByName(@RequestParam String name) {

        if (name.equals("")) return Anime.getAnime();

        return Anime.getAnime()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("findById/{id}")
    public List<Anime> findById(@PathVariable Long id) {
        return Anime.getAnime()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .toList();
    }

    @PostMapping
    public Anime addAnime(@RequestBody Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(1, 100) * 23);
        Anime.getAnime().add(anime);
        return anime;
    }


}
