package academy.devdojo.Controller;

import academy.devdojo.Dominio.Anime;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.nodes.AnchorNode;

import java.util.List;

@RestController
@RequestMapping("/v1/animes")
public class AnimeController {

private static final List<String> animes = List.of("1","2","3","4","5");

    @GetMapping
    public List<String> listAll(){
        return animes;
    }

    @GetMapping("listaAnime")
    public List<Anime> lista(){
        return Anime.listaRetorno();
    }

    @GetMapping("findByName")
    public List<Anime> findByName(@RequestParam(defaultValue = "")String name){
        return Anime.listaRetorno()
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("findById")
    public List<Anime> findById(@PathVariable(required = true) Long id){
        return Anime.listaRetorno()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .toList();
    }


}
