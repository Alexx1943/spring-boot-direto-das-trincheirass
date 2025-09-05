package academy.devdojo.Service;

import academy.devdojo.Domain.Anime;
import academy.devdojo.Exception.NotFoundException;
import academy.devdojo.Repository.AnimeHardCoreRepository;
import academy.devdojo.Repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCoreRepository repository;
    private final AnimeRepository animeRepository;


    public List<Anime> findAll(String name) {

        return name == null ? animeRepository.findAll() : animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowNotFound(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anime not found"));
    }

    public Anime save(Anime anime) {
        return animeRepository.save(anime);
    }

    public void delete(Long id) {

        var animeToDeleted = findByIdOrThrowNotFound(id);
        animeRepository.delete(animeToDeleted);
    }

    public void update(Anime anime) {

        var animeToUpdated = findByIdOrThrowNotFound(anime.getId());
        animeRepository.save(anime);
    }
}
