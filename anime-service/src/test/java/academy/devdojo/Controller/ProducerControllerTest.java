package academy.devdojo.Controller;

import academy.devdojo.Commons.FileUtils;
import academy.devdojo.Commons.ProducersUtils;
import academy.devdojo.Domain.Producer;
import academy.devdojo.Repository.ProducerData;
import academy.devdojo.Repository.ProducerHardCodeRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
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
import java.time.LocalDateTime;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProducerControllerTest {

    private static final String URL = "/v1/producers";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerData data;

    @Autowired
    private ProducersUtils producersUtils;

    @Autowired
    private FileUtils fileUtils;


    @Autowired
    private ResourceLoader resourceLoader;

    @SpyBean
    private ProducerHardCodeRepository repository;

    @BeforeEach
    void init() {
        producersUtils.newProducers();
    }


    @Test
    @Order(1)
    @DisplayName("GET/v1/producers returns a list producers when argument is null")
    void findAllReturnsListProducers_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var responseNull = readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseNull));
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/producers?param return a list with found object when name exist ")
    void findAllReturnListWithFoundObject_WhenNameIsFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var responseTeste7 = readResourceFile("producer/get-producer-teste7-name-200.json");

        var name = "teste7";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseTeste7));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/producers?name is not found returns a empity list")
    void findAllReturnEmpityList_WhenNameIsNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var responseEmpityList = readResourceFile("producer/get-producer-[]-name-200.json");

        var name = "[]";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseEmpityList));
    }

    @Test
    @Order(4)
    @DisplayName("GET/v1/producers/1 returns a producer with given id")
    void findByIdReturnProducer_WhenSucessful() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var responseId = readResourceFile("producer/get-producer-id-200.json");

        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/findById/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseId));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/producers/99 throws ResponseStatusException 404 when producer is not found")
    void findByIdThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/findById/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not found"));
    }

    @Test
    @Order(6)
    @DisplayName("POST/v1/producers create a producers")
    void saveCreateProducer_WhenSucessful() throws Exception {

        var producerToSave = Producer.builder()
                .id(2L)
                .name("teste")
                .createdAt(LocalDateTime.now()).build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);

        var request = readResourceFile("producer/post-request-producer-teste7-name-200.json");
        var response = readResourceFile("producer/post-response-producer-teste7-name-201.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
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
    @DisplayName("PUT/v1/producers updates a producer")
    void updateUpdatesProducer_WhenSucessful() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var request = readResourceFile("producer/put-producer-id-200.json");
        var id = producersUtils.newProducers().getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL, id)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("PUT/v1/producers throws ResponseStatusException when producer is not found")
    void updateThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());


        var request = readResourceFile("producer/put-producer-id-404.json");


        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Producer not found"));
    }

    @Test
    @Order(9)
    @DisplayName("DELETE/v1/producers/1 remove a producer")
    void deleteRemoveProducer_WhenSucessful() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var id = producersUtils.newProducers().getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("DELETE/v1/producers/999 throws ResponseStatusException when producer is not found")
    void deleteThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        BDDMockito.when(data.getProducers()).thenReturn(producersUtils.newProducers());

        var id = 999L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }


}