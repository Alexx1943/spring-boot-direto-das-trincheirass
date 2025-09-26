package academy.devdojo.controller;


import academy.devdojo.commons.AnimesUtils;
import academy.devdojo.commons.FileUtils;
import academy.devdojo.Domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCoreRepository;
import academy.devdojo.repository.AnimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {

    private static final String URL = "/v1/animes";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeData data;

    private List<Anime> animeList;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private AnimesUtils animesUtils;

    @MockBean
    private AnimeHardCoreRepository repository;

    @MockBean
    private AnimeRepository animeRepository;

    @BeforeEach
    void init() {
        animesUtils.newAnimes();
    }

    @Test
    @Order(1)
    @DisplayName("GET/v1/animes returns a animes list with all anime")
    void findAllReturnListAnime_WhenSucessful() throws Exception {

        BDDMockito.when(animeRepository.findAll()).thenReturn(animesUtils.newAnimes());

        var requestNull = fileUtils.readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNull));
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/animes?param returns a list with found object when name exist")
    void findAllReturnListWithAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var requestName = fileUtils.readResourceFile("anime/get-anime-Naruto-name-200.json");

        var name = "Naruto";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestName));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/animes?param returns a empity list when name is not found")
    void findAllReturnsEmpityList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var request = fileUtils.readResourceFile("anime/get-anime-[]-name-200.json");

        var name = "[]";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(request));
    }

    @Test
    @Order(4)
    @DisplayName("GET/v1/animes/1 returns a anime with given id")
    void findByIdReturnsAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var request = fileUtils.readResourceFile("anime/get-anime-id-name-200.json");

        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(request));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/animes/999 returns ResponseStatusException when id is not found")
    void findByIdThrowsResponseStatusException_WhenIdIsNotFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var id = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }


    @Test
    @Order(6)
    @DisplayName("POST/v1/animes creates a anime")
    void saveCreatesAnime_WhenSucessful() throws Exception {

        var animeToSave = Anime.builder().id(1L).name("Test").build();

        BDDMockito.when(animeRepository.save(animeToSave)).thenReturn(animeToSave);

        var request = fileUtils.readResourceFile("anime/post-request-anime-test-200.json");
        var response = fileUtils.readResourceFile("anime/post-response-anime-test-201.json");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(request)
                        .header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(7)
    @DisplayName("PUT/v1/animes updates an anime")
    void updateUpdateAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var request = fileUtils.readResourceFile("anime/put-request-anime-name-200.json");
        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.put(URL, id)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("PUT/v1/animes throws ResponseStatusException when an anime is not found")
    void updateThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var response = fileUtils.readResourceFile("anime/put-response-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put(URL)
                        .content(response)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @Order(9)
    @DisplayName("DELETE/v1/animes/1 remove an anime")
    void deleteRemoveAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("DELETE/v1/anime/431 throws ResponseStatusException when id is not found")
    void deleteThrowsResponseStatusException_WhenIdIsNotFound() throws Exception {

        var id = 431L;


    }

    @ParameterizedTest
    @MethodSource("putAnimeBadRequest")
    @Order(11)
    @DisplayName("PUT/v1/animes returns bad request when fields are invalids")
    void updateReturnBadRequest_WhenFieldsInvalids(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        org.assertj.core.api.Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @ParameterizedTest
    @MethodSource("postAnimeBadRequest")
    @Order(11)
    @DisplayName("POST/v1/animes returns bad request when fields are invalids")
    void updateReturnBadRequest_WhenFieldsAreEmpity(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        org.assertj.core.api.Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postAnimeBadRequest() {


        return Stream.of(
                Arguments.of("post-request-anime-blank-fields-400.json", allErrors()),
                Arguments.of("post-request-anime-empity-fields-400.json", allErrors())
        );
    }

    private static Stream<Arguments> putAnimeBadRequest() {

        return Stream.of(
                Arguments.of("put-request-anime-blank-fields-400.json", allErrors()),
                Arguments.of("put-request-anime-empity-fields-400.json", allErrors())
        );
    }

    private static List<String> allErrors() {

        var firstNameRequiredError = "The field 'id' cannot be null";
        var lastNameRequiredError = "The field 'name' is required";
        return new ArrayList<>(List.of(firstNameRequiredError, lastNameRequiredError)
        );
    }
}