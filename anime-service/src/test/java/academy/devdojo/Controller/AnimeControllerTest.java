package academy.devdojo.Controller;


import academy.devdojo.Domain.Anime;
import academy.devdojo.Repository.AnimeData;
import academy.devdojo.Repository.AnimeHardCoreRepository;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeData data;

    private List<Anime> animeList;

    @Autowired
    private ResourceLoader resourceLoader;

    @SpyBean
    private AnimeHardCoreRepository repository;

    @BeforeEach
    void init() {
        Anime teste1 = Anime.builder()
                .id(1L)
                .name("Naruto")
                .build();

        Anime teste2 = Anime.builder()
                .id(2L)
                .name("Dragoon Ball Z")
                .build();

        animeList = new ArrayList<>(List.of(teste1, teste2));
    }

    @Test
    @Order(1)
    @DisplayName("GET/v1/animes returns a animes list with all anime")
    void findAllReturnListAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var requestNull = readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestNull));
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/animes?param returns a list with found object when name exist")
    void findAllReturnListWithAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var requestName = readResourceFile("anime/get-anime-Naruto-name-200.json");

        var name = "Naruto";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(requestName));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/animes?param returns a empity list when name is not found")
    void findAllReturnsEmpityList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/get-anime-[]-name-200.json");

        var name = "[]";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(request));
    }

    @Test
    @Order(4)
    @DisplayName("GET/v1/animes/1 returns a anime with given id")
    void findByIdReturnsAnime_WhenSucessful() throws Exception {

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/get-anime-id-name-200.json");

        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }


    @Test
    @Order(6)
    @DisplayName("POST/v1/animes creates a anime")
    void saveCreatesAnime_WhenSucessful() throws Exception {

        var animeToSave = Anime.builder().id(1L).name("Test").build();

        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var request = readResourceFile("anime/post-request-anime-test-200.json");
        var response = readResourceFile("anime/post-response-anime-test-201.json");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/animes")
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

        var request = readResourceFile("anime/put-request-anime-name-200.json");
        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes", id)
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

        var response = readResourceFile("anime/put-response-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
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
    void deleteRemoveAnime_WhenSucessful() throws Exception{

        BDDMockito.when(data.getAnimes()).thenReturn(animeList);

        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("DELETE/v1/anime/431 throws ResponseStatusException when id is not found")
    void deleteThrowsResponseStatusException_WhenIdIsNotFound() throws Exception {

        var id = 431L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}