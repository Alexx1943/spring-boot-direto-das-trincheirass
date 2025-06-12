package academy.devdojo.Controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private static final List<String> HEROES = List.of("Kakashi", "Naruto", "Dragon Ball Z");

    @GetMapping
    public List<String> listaAll(){
        return HEROES;
    }

    @GetMapping("filter")
    public List<String> listAllHeroesParam(@RequestParam(defaultValue = "")String name){
        return HEROES.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterList")
    public List<String> listAllHeroesParamList(@RequestParam List<String> names){
        return HEROES.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String findByName(@PathVariable(required = true) String name){

        return HEROES
                .stream()
                .filter(hero -> hero.equalsIgnoreCase(name))
                .findFirst().orElse("");
    }





}
