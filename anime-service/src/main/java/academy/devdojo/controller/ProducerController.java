package academy.devdojo.controller;

import academy.devdojo.Mapper.ProducerMapper;
import academy.devdojo.Request.ProducerPostResquest;
import academy.devdojo.Request.ProducerPutRequest;
import academy.devdojo.Response.ProducerGetResponse;
import academy.devdojo.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerMapper mapper;
    private final ProducerService service;


    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.info("Request received to list all producers, para name '{}'", name);

        var producers = service.findAll(name);

        var response = mapper.toProducerGetResponseList(producers);
        return ResponseEntity.ok(response);

    }


    @GetMapping("findById/{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        log.info("Request to find producer by id '{}'", id);

        var producerById = service.findByIdOrThrowNotFound(id);

        var produceResponse = mapper.toProducerGetResponse(producerById);

        return ResponseEntity.ok(produceResponse);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> save(@RequestBody @Valid ProducerPostResquest producerPostResquest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var producer = mapper.toProducer(producerPostResquest);

        var producerSaved = service.save(producer);

        var response = mapper.toProducerGetResponse(producerSaved);


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteId(@PathVariable Long id) {
        log.info("Request to delete producer by id:  {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid ProducerPutRequest request) {
        log.info("Request to update producer '{}'", request);

        var producerToUpdate = mapper.toProducer(request);

        service.update(producerToUpdate);

        return ResponseEntity.noContent().build();

    }


}


