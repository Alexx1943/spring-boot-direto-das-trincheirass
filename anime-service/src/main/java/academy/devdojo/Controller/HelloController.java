package academy.devdojo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/greetings")
@Slf4j
public class HelloController {

    @GetMapping
    public String hi() {
        return "Alex Tavares de Oliveira";
    }

    @PostMapping
    public Long save(@RequestBody String name) {
        log.info("save '{}", name);
        return ThreadLocalRandom.current().nextLong(1, 500);
    }

}
