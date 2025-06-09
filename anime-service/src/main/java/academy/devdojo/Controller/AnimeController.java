package academy.devdojo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/animes")
public class AnimeController {

private static final List<String> animes = List.of("1","2","3","4","5");

    @GetMapping
    public List<String> listAll(){
        return animes;
    }

}
