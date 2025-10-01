package academy.devdojo.service;

import academy.devdojo.Domain.Anime;
import academy.devdojo.commons.AnimesUtils;
import academy.devdojo.repository.AnimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    @InjectMocks
    private AnimesUtils animesUtils;

    private List<Anime> animeList;

    @BeforeEach
    void init() {

        animeList = animesUtils.getListAnimes();
    }

    @Test
    @Order(1)
    @DisplayName("findaAll returns a list with all animes when argument is null")
    void findAllReturnsListAnimes_WhenSucessful() {

        BDDMockito.when(repository.findAll()).thenReturn(animeList);

        var findAllResponse = service.findAll(null);

        Assertions.assertThat(findAllResponse).isNotEmpty().hasSameElementsAs(animeList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns a anime when argument is name")
    void findaAllReturnsAnime_WhenSucessful() {

        var animeName = animeList.getFirst();

        var singletonList = Collections.singletonList(animeName);

        BDDMockito.when(repository.findByName(animeName.getName())).thenReturn(singletonList);

        var anime = service.findAll(animeName.getName());

        Assertions.assertThat(anime).containsAll(singletonList);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns a empity list when name is not found")
    void findAllReturnsEmpityList_WhenNameIsNotFound() {

        var name = "tess";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var anime = service.findAll(name);

        Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a anime")
    void findByIdReturnsAnime_WhenSucessful() {

        var animeToId = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToId.getId())).thenReturn(Optional.of(animeToId));

        var anime = service.findByIdOrThrowNotFound(animeToId.getId());

        Assertions.assertThat(anime).isEqualTo(animeToId);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws ResponseStatusException when id is not found")
    void findByIdThrowsResponseStatusException_WhenIdIsNotfound() {
        var animeId = animeList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(animeId.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save created a anime")
    void saveCreatedAnime_WhenSucessful() {

        var animeToCreate = Anime.builder()
                .id(ThreadLocalRandom.current().nextLong(765))
                .name("Teste")
                .build();


        BDDMockito.when(repository.save(animeToCreate)).thenReturn(animeToCreate);

        var animeCreated = service.save(animeToCreate);

        Assertions.assertThat(animeCreated).isEqualTo(animeToCreate).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete remove anime by id")
    void deleteRemoveAnime_WhenSucessful() {

        var animeToRemove = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToRemove.getId())).thenReturn(Optional.of(animeToRemove));
        BDDMockito.doNothing().when(repository).delete(animeToRemove);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(animeToRemove.getId()));
    }

    @Test
    @Order(8)
    @DisplayName("delete throws responseStatusException when anime is not found")
    void deleteThrowsResponseStatusException_WhenAnimeIsNotFound() {

        var animeToDelete = animeList.getFirst();

        BDDMockito.when(repository.findById(animeToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(animeToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @Order(9)
    @DisplayName("update Updated a anime list")
    void updateUpdatedAnimeList_WhenSucessful() {

        var animeToUpdate = animeList.getFirst();
        animeToUpdate.setName("Teste");

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.when(repository.save(animeToUpdate)).thenReturn(animeToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @Order(10)
    @DisplayName("update throws ResponseStatusException when anime is not found")
    void updateThrowsResponseStatusException_WhenAnimeIsNotFound() {

        var animeToUpdate = animeList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);

    }


}