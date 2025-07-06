package academy.devdojo.Repository;

import academy.devdojo.Domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCoreRepositoryTest {

    @InjectMocks
    private AnimeHardCoreRepository repository;

    @Mock
    private AnimeData data;

    private final List<Anime> animeList = new ArrayList<>();

    @BeforeEach
    void init() {
        var anime1 = Anime.builder()
                .id(ThreadLocalRandom.current().nextLong(100000))
                .name("anime1")
                .build();

        var anime2 = Anime.builder()
                .id(ThreadLocalRandom.current().nextLong(100000))
                .name("anime2")
                .build();

        animeList.addAll(List.of(anime1, anime2));
    }


    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSucessful() {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var anime = repository.findAll();

        Assertions.assertThat(anime).isNotNull().hasSize(2);
    }

    @Test
    @Order(2)
    @DisplayName("findById returns a anime with given id")
    void findByName_ReturnsEmpityList_WhenISNull() {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var expectAnime = animeList.getFirst();

        var anime = repository.findById(expectAnime.getId());

        Assertions.assertThat(anime).isPresent().contains(expectAnime);
    }

    @Test
    @Order(3)
    @DisplayName("findByName returns a list empity when name is null")
    void findByNameReturnsListEmpity_whenNameIsNull() {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var animeNull = repository.findByName(null);

        Assertions.assertThat(animeNull).isNotNull().isEmpty();

    }

    @Test
    @Order(4)
    @DisplayName("findByName returns a list found  object with name exist")
    void findByNameReturnsListFoundObjectWithNameExist_WhenSucessful(){

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var animeName = animeList.getFirst();

        var anime = repository.findByName(animeName.getName());

        Assertions.assertThat(anime).contains(animeName);
    }

    @Test
    @Order(5)
    @DisplayName("save create a new anime")
    void saveReturnNewAnime_WhenSucessful(){

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var newAnime = Anime.builder()
                .id(ThreadLocalRandom.current().nextLong(13322))
                .name("Alex")
                .build();

        var animeSaved = repository.save(newAnime);

        Assertions.assertThat(animeSaved).isEqualTo(newAnime).hasNoNullFieldsOrProperties();

        var anime = repository.findById(animeSaved.getId());

        Assertions.assertThat(anime).isPresent().contains(newAnime);
    }

    @Test
    @Order(6)
    @DisplayName("delete remove a anime")
    void deleteRemoveAnime(){

    }






}