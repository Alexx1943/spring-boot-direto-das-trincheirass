package academy.devdojo.commons;

import academy.devdojo.Domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProducersUtils {

    public List<Producer> getListProducer(){


        var dateTime = "2025-07-12T14:55:39.226635219";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


        var teste1 = Producer.builder().id(1L).name("Teste5").createdAt(localDateTime).build();
        var teste2 = Producer.builder().id(2L).name("Teste6").createdAt(localDateTime).build();
        var teste3 = Producer.builder().id(3L).name("Teste7").createdAt(localDateTime).build();
        var teste4 = Producer.builder().id(4L).name("Teste8").createdAt(localDateTime).build();

        return new ArrayList<>(List.of(teste1, teste2, teste3, teste4));
    }

    public List<Producer> getSingleProducer(){

        var dateTime = "2025-07-12T14:55:39.226635219";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var teste1 = Producer.builder().id(1L).name("Teste5").createdAt(null).build();

        return new ArrayList<>(List.of(teste1));
    }

    public Producer getProducer(){

        var dateTime = "2025-07-12T14:55:39.226635219";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var teste1 = Producer.builder().id(1L).name("Teste5").createdAt(null).build();

        return teste1;

    }


}
