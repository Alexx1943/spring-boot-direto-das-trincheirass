package academy.devdojo.Repository;

import academy.devdojo.Domain.Producer;
import lombok.Lombok;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCodeRepository {

    private final static List<Producer> PRODUCERS = new ArrayList<>();

    static {

        var Teste1 = Producer.builder().id(1L).name("Teste1").createdAt(LocalDateTime.now()).build();
        var Teste2 = Producer.builder().id(2L).name("Teste2").createdAt(LocalDateTime.now()).build();
        var Teste3 = Producer.builder().id(3L).name("Teste3").createdAt(LocalDateTime.now()).build();
        var Teste4 = Producer.builder().id(4L).name("Teste4").createdAt(LocalDateTime.now()).build();

        PRODUCERS.addAll(List.of(Teste1, Teste2, Teste3, Teste4));

    }

    public static List<Producer> getProducers() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        PRODUCERS.remove(producer);
    }

    public Producer updte(Producer producer) {
        delete(producer);
        save(producer);
        return producer;
    }


}
