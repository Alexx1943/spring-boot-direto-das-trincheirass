package academy.devdojo.Dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class Producer {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    private static List<Producer> producers = new ArrayList<>();

    static{

        var Teste1 = Producer.builder().id(1L).name("Teste1").createdAt(LocalDateTime.now()).build();
        var Teste2 = Producer.builder().id(2L).name("Teste2").createdAt(LocalDateTime.now()).build();
        var Teste3 = Producer.builder().id(3L).name("Teste3").createdAt(LocalDateTime.now()).build();
        var Teste4 = Producer.builder().id(4L).name("Teste4").createdAt(LocalDateTime.now()).build();

        producers.addAll(List.of(Teste1, Teste2,Teste3,Teste4));

    }



    public static List<Producer> getProducers() {
        return producers;
    }


}
