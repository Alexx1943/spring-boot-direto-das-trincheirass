package academy.devdojo.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Anime {

    private Long id;
    private String name;

    private static List<Anime> animes = new ArrayList<>();

    static {

        var naruto = new Anime(1L, "Naruto");
        var dragonBallZ = new Anime(2L, "Dragoon Ball Z");
        animes.addAll(List.of(naruto, dragonBallZ));
    }


    public static List<Anime> findAll() {

        return animes;
    }


}
