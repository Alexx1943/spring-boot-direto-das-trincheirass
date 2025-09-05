package academy.devdojo.Service;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Exception.NotFoundException;
import academy.devdojo.Repository.ProducerHardCodeRepository;
import academy.devdojo.Repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodeRepository repository;
    private final ProducerRepository producerRepository;




    public List<Producer> findAll(String name) {

        return name == null ? producerRepository.findAll() : producerRepository.findByName(name);
    }


    public Producer findByIdOrThrowNotFound(Long id) {

        return producerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producer not found"));
    }

    public Producer save(Producer producer) {

    return producerRepository.save(producer);
    }

    public void delete(Long id) {

        var produceToRemoved = findByIdOrThrowNotFound(id);
        producerRepository.delete(produceToRemoved);
    }

    public void update(Producer producerToUpdate) {

        var producer = findByIdOrThrowNotFound(producerToUpdate.getId());
        producer.setCreatedAt(producer.getCreatedAt());
        producerRepository.save(producerToUpdate);

    }


}
