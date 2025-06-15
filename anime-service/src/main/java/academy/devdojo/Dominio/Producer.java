package academy.devdojo.Dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Producer {

    private Long id;
    private String name;

    private static List<Producer> retorno = new ArrayList<>(
            List.of(new Producer(1L, "Naruto"),
                    new Producer(2L, "Dragon Ball Z")));


    public static List<Producer> listaRetorno() {

        return retorno;
    }

    public static void addProducer(Producer producer) {
        retorno.add(producer);
        System.out.println("Anime salvo com sucesso!!!");
    }


}
