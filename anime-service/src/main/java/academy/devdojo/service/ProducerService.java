package academy.devdojo.service;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Exception.NotFoundException;
import academy.devdojo.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {


    private final ProducerRepository repository;




    public List<Producer> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }


    public Producer findByIdOrThrowNotFound(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producer not found"));
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
        repository.save(producerToUpdate);

    }


}
