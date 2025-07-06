package academy.devdojo.Repository;

import academy.devdojo.Domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private final  List<Anime> animes = new ArrayList<>();

    {

        var naruto = new Anime(1L,"Naruto");
        var dragonBallZ = new Anime(2L,"Dragoon Ball Z");
        animes.addAll(List.of(naruto, dragonBallZ));
    }

    public List<Anime> getAnimes(){
        return animes;
    }
}
