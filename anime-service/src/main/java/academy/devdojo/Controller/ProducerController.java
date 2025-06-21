package academy.devdojo.Controller;

import academy.devdojo.Dominio.Producer;
import academy.devdojo.Request.ProducerPostResquest;
import academy.devdojo.Response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("v1/producers")
public class ProducerController {


    @GetMapping("listAll")
    public List<Producer> listAll() {
        return Producer.getProducers();
    }

    @GetMapping("findByName")
    public List<Producer> findaByName(@RequestParam String name) {

        if (name.equals("")) return Producer.getProducers();

        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("findById/{id}")
    public List<Producer> findById(@PathVariable Long id) {
        return Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .toList();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")

    public ResponseEntity<ProducerGetResponse> addProducer(@RequestBody ProducerPostResquest producerPostResquest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);

        var producer = Producer.builder()
                .id(ThreadLocalRandom.current().nextLong(1000))
                .name(producerPostResquest.getName())
                .createdAt(LocalDateTime.now())
                .build();
        Producer.getProducers().add(producer);

        var response = ProducerGetResponse.builder()
                .id(producer.getId())
                .name(producer.getName())
                .createdAt(LocalDateTime.now())
                .build();


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
