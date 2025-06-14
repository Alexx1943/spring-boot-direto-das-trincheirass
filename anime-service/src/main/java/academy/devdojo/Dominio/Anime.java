package academy.devdojo.Dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;

    private static List<Anime> retorno = new ArrayList<>(
            List.of(new Anime(1L, "Naruto"),
                    new Anime(2L, "Dragon Ball Z")));


    public static List<Anime> listaRetorno() {

        return retorno;
    }

    public static void addAnime(Anime anime) {
        retorno.add(anime);
        System.out.println("Anime salvo com sucesso!!!");
    }


}
