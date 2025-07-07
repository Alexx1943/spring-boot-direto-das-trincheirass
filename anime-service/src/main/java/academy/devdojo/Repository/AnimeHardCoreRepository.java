package academy.devdojo.Repository;

import academy.devdojo.Domain.Anime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class AnimeHardCoreRepository {
    
    private final AnimeData data;


    public List<Anime> findAll() {
        return data.getAnimes();
    }


    public List<Anime> findByName(String name) {

        return data.getAnimes().stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Anime> findById(Long id) {
        return data.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public Anime save(Anime anime) {
        data.getAnimes().add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        data.getAnimes().remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }

}
