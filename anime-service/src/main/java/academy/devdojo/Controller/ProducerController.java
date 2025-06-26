package academy.devdojo.Controller;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Mapper.ProducerMapper;
import academy.devdojo.Request.ProducerPostResquest;
import academy.devdojo.Request.ProducerPutRequest;
import academy.devdojo.Response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/producers")
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;


    @GetMapping("listAll")
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {

        var producers = Producer.getProducers();
        var prodecerGetList = MAPPER.toProducerGetResponseList(producers);

        if (name == null) return ResponseEntity.ok(prodecerGetList);

        var response = prodecerGetList.stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(response);

    }

    @GetMapping("findByName")
    public ResponseEntity<ProducerGetResponse> findaByName(@RequestParam String name) {

        var response = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(MAPPER::toProducerGetResponse)
                .orElse(null);

        return ResponseEntity.ok(response);

    }

    @GetMapping("findById/{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {

        var response = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .map(MAPPER::toProducerGetResponse)
                .orElse(null);

        return ResponseEntity.ok(response);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> addProducer(@RequestBody ProducerPostResquest producerPostResquest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var producer = MAPPER.toProducer(producerPostResquest);
        var response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id) {
        log.info("Request to delete producer by id:  {}", id);

        var producerDelete = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "producer not found"));

        Producer.getProducers().remove(producerDelete);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request){

        var producerToDeleted = Producer.getProducers().stream()
                .filter(producer -> producer.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var producerUpdated = MAPPER.toProducer(request);

        Producer.getProducers().remove(producerToDeleted);
        Producer.getProducers().add(producerUpdated);

        return ResponseEntity.noContent().build();


    }


}
