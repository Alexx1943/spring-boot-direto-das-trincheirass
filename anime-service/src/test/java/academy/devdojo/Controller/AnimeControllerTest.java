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


    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }
}