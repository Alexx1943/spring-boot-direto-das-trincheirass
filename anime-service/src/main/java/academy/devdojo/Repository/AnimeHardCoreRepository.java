package academy.devdojo.Repository;

import academy.devdojo.Domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCoreRepository {
    
    private AnimeData animeData;


    public List<Anime> findAll() {
        return animeData.getAnimes();
    }


    public List<Anime> findByName(String name) {

        return animeData.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        animeData.getAnimes().remove(anime);
    }

    public Anime update(Anime anime) {
        delete(anime);
        save(anime);
        return anime;
    }

}
