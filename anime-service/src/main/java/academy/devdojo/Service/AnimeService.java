package academy.devdojo.Service;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Repository.AnimeHardCoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCoreRepository repository;


    public List<Anime> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Anime findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public void delete(Long id) {

        var animeToDeleted = findByIdOrThrowNotFound(id);
        repository.delete(animeToDeleted);
    }

    public void update(Anime anime) {

        var animeToUpdated = findByIdOrThrowNotFound(anime.getId());
        repository.update(anime);
    }
}
