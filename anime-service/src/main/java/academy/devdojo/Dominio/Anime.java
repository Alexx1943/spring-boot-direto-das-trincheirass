package academy.devdojo.Dominio;

import lombok.Getter;

import java.util.List;

@Getter
public class Anime {

    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Anime> listaRetorno(){
        List<Anime> retorno = List.of(new Anime(1L, "Naruto"), new Anime(2L, "Dragon Ball Z"));
        return retorno;
    }


}
