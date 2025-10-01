package academy.devdojo.commons;

import academy.devdojo.Domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimesUtils {

    public List<Anime> getListAnimes(){

        Anime teste1 = Anime.builder()
                .id(1L)
                .name("Naruto")
                .build();

        Anime teste2 = Anime.builder()
                .id(2L)
                .name("Dragoon Ball Z")
                .build();

        return new ArrayList<>(List.of(teste1, teste2));
    }

    public Anime getAnime(){

        Anime teste1 = Anime.builder()
                .id(1L)
                .name("Naruto")
                .build();

        return teste1;
    }

    public List<Anime> getAnimes(){

        Anime teste1 = Anime.builder()
                .id(1L)
                .name("Naruto")
                .build();

        return new ArrayList<>(List.of(teste1));
    }

}
