package academy.devdojo.Controller;

import academy.devdojo.Dominio.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {


    @GetMapping("listAll")
    public List<Producer> listAll() {
        return Producer.listaRetorno();
    }

    @GetMapping("findByName")
    public List<Producer> findByName(@RequestParam String name) {

        if (name.equals("")) return Producer.listaRetorno();

        return Producer.listaRetorno()
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();
    }

    @GetMapping("findById/{id}")
    public List<Producer> findById(@PathVariable Long id) {
        return Producer.listaRetorno()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .toList();
    }

    @PostMapping
    public Producer addProducer(@RequestBody Producer producer) {
        producer.setId(ThreadLocalRandom.current().nextLong(1, 100) * 23);
        Producer.addProducer(producer);
        return producer;
    }


}
