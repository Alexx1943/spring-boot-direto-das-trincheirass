package academy.devdojo.Repository;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Domain.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimeHardCoreRepository {
    private final static List<Anime> ANIMES = new ArrayList<>();

    static{

        var naruto = new Anime(1L,"Naruto");
        var dragonBallZ = new Anime(2L,"Dragoon Ball Z");
        ANIMES.addAll(List.of(naruto, dragonBallZ));
    }



    public  List<Anime> findAll() {
        return ANIMES;
    }


    public List<Anime> findByName(String name){

        return ANIMES.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Anime> findById(Long id){
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime anime){
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime){
        ANIMES.remove(anime);
    }

    public Anime update(Anime anime){
        delete(anime);
        save(anime);
        return anime;
    }

}
