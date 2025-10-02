package academy.devdojo.service;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Exception.NotFoundException;
import academy.devdojo.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;


    public List<Anime> findAll(String name) {

        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Anime findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anime not found"));
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
        repository.save(anime);
    }
}
