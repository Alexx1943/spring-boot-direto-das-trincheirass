package academy.devdojo.Repository;

import academy.devdojo.Domain.Producer;
import external.dependecy.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j2
public class ProducerHardCodeRepository {

    private final Connection connection;
    private final ProducerData producers;

    public  List<Producer> findAll() {
        return producers.getProducers();
    }


    public Optional<Producer> findById(Long id) {
        return producers.getProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        log.debug(connection);
        return producers.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer) {
        producers.getProducers().add(producer);
        return producer;
    }

    public void delete(Producer producer) {
        producers.getProducers().remove(producer);
    }

    public Producer update(Producer producer) {
        delete(producer);
        save(producer);
        return producer;
    }


}
