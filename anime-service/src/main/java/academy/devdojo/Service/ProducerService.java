package academy.devdojo.Service;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Repository.ProducerHardCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ProducerService {

    private ProducerHardCodeRepository repository;

    public ProducerService() {

        this.repository = new ProducerHardCodeRepository();
    }


    public List<Producer> listAll(String name) {

        return name == null ? repository.listAll() : repository.findByName(name);
    }


    public Producer findByIdOrThrowNotFound(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produce not found"));
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
        repository.updte(producerToUpdate);
    }


}
