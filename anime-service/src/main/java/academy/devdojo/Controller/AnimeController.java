package academy.devdojo.Controller;

import academy.devdojo.Dominio.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private static final List<String> animes = List.of("1", "2", "3", "4", "5");

    @GetMapping
    public List<String> listAlll(){
        return animes;
    }

    @GetMapping("listAll")
    public List<Anime> listAll() {
        return Anime.listaRetorno();
    }

    @GetMapping("findByName")
    public List<Anime> findaByName(@RequestParam String name) {

        if (name == "") return Anime.listaRetorno();

        return Anime.listaRetorno()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("findById/{id}")
    public List<Anime> findById(@PathVariable Long id) {
        return Anime.listaRetorno()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .toList();
    }



}
