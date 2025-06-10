package academy.devdojo.Controller;

import academy.devdojo.Dominio.Pessoa;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private static final List<Pessoa> HEROES = List.of(
            new Pessoa("Goku", 29,"goku@gmail.com"),
            new Pessoa("Kakashi", 67,"kakashi@gmail"),
            new Pessoa("Naruto",42,"nruto@gmail.com"));

    @GetMapping
    public List<Pessoa> listaAll(){
        return HEROES;
    }

    @GetMapping("filter")
    public List<Pessoa> listAllHeroesParam(@RequestParam(defaultValue = "")String name){
        return HEROES.stream().filter(hero -> hero.getName().equals(name)).toList();
    }

    @GetMapping("filterList")
    public List<Pessoa> listAllHeroesParamList(@RequestParam List<Pessoa> names){
        return HEROES.stream().filter(names::contains).toList();
    }

//    @GetMapping("{name}")
//    public String findByName(@PathVariable String name){
//        return HEROES
//                .stream()
//                .filter(hero -> hero.getName().equals(name))
//                .toList();
//    }



}
