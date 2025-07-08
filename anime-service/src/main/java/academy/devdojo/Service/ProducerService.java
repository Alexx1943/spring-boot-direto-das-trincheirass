package academy.devdojo.Service;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Repository.ProducerHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodeRepository repository;




    public List<Producer> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }


    public Producer findByIdOrThrowNotFound(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public Producer save(Producer producer) {

    return repository.save(producer);
    }

    public void delete(Long id) {

        var produceToRemoved = findByIdOrThrowNotFound(id);
        repository.delete(produceToRemoved);
    }

    public void update(Producer producerToUpdate) {

        var producer = findByIdOrThrowNotFound(producerToUpdate.getId());
        producer.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToUpdate);

    }


}
