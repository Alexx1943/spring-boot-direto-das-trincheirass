package academy.devdojo.controller;

import academy.devdojo.commons.FileUtils;
import academy.devdojo.commons.ProducersUtils;
import academy.devdojo.Domain.Producer;
import academy.devdojo.repository.AnimeRepository;
import academy.devdojo.repository.ProducerData;
import academy.devdojo.repository.ProducerHardCodeRepository;
import academy.devdojo.repository.ProducerRepository;
import academy.devdojo.service.ProducerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProducerControllerTest {

    private static final String URL = "/v1/producers";

    @MockBean
    private AnimeRepository animeRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerData data;

    @Autowired
    private ProducersUtils producersUtils;

    @Autowired
    private FileUtils fileUtils;

    @MockBean
    private ProducerHardCodeRepository repository;

    @MockBean
    private ProducerService service;

    @MockBean
    private ProducerRepository producerRepository;

    @BeforeEach
    void init() {

        producersUtils.getListProducer();
    }


    @Test
    @Order(1)
    @DisplayName("GET/v1/producers returns a list producers when argument is null")
    void findAllReturnsListProducers_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(service.findAll(null)).thenReturn(producersUtils.getListProducer());

        var responseNull = fileUtils.readResourceFile("producer/get/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseNull));
    }

    @Test
    @Order(2)
    @DisplayName("GET/v1/producers?param return a list with found object when name exist ")
    void findAllReturnListWithFoundObject_WhenNameIsFound() throws Exception {

        var name = "teste5";

        BDDMockito.when(service.findAll(name)).thenReturn(producersUtils.getSingleProducer());

        var responseTeste7 = fileUtils.readResourceFile("producer/get/get-producer-teste-name-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseTeste7));
    }

    @Test
    @Order(3)
    @DisplayName("GET/v1/producers?name is not found returns a empity list")
    void findAllReturnEmpityList_WhenNameIsNotFound() throws Exception {

        var name = "[]";

        BDDMockito.when(service.findAll(name)).thenReturn(Collections.emptyList());

        var responseEmpityList = fileUtils.readResourceFile("producer/get/get-producer-[]-name-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseEmpityList));
    }

    @Test
    @Order(4)
    @DisplayName("GET/v1/producers/1 returns a producer with given id")
    void findByIdReturnProducer_WhenSucessful() throws Exception {

        var id = producersUtils.getProducer();

        BDDMockito.when(service.findByIdOrThrowNotFound(id.getId())).thenReturn(producersUtils.getProducer());

        var responseId = fileUtils.readResourceFile("producer/get/get-producer-id-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseId));
    }

    @Test
    @Order(5)
    @DisplayName("GET/v1/producers/99 throws ResponseStatusException 404 when producer is not found")
    void findByIdThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        var id = 99L;

        BDDMockito.when(service.findByIdOrThrowNotFound(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    @Test
    @Order(6)
    @DisplayName("POST/v1/producers create a producers")
    void saveCreateProducer_WhenSucessful() throws Exception {

        var producerToSave = producersUtils.getProducer();

        BDDMockito.when(service.save(producerToSave)).thenReturn(producerToSave);

        var request = fileUtils.readResourceFile("producer/post/post-request-producer-teste7-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(7)
    @DisplayName("PUT/v1/producers updates a producer")
    void updateUpdatesProducer_WhenSucessful() throws Exception {

        var id = producersUtils.getListProducer().getFirst();
        id.setName("sssd");

        BDDMockito.when(service.findByIdOrThrowNotFound(id.getId())).thenReturn(id);
        BDDMockito.doNothing().when(service).update(id);

        var request = fileUtils.readResourceFile("producer/put/put-producer-id-200.json");

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

        var id = 90L;

        BDDMockito.when(service.findByIdOrThrowNotFound(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    @Test
    @Order(9)
    @DisplayName("DELETE/v1/producers/1 remove a producer")
    void deleteRemoveProducer_WhenSucessful() throws Exception {

        var id = producersUtils.getListProducer().getFirst();

        BDDMockito.when(service.findByIdOrThrowNotFound(id.getId())).thenReturn(id);
        BDDMockito.doNothing().when(service).delete(id.getId());


        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("DELETE/v1/producers/999 throws ResponseStatusException when producer is not found")
    void deleteThrowsResponseStatusException_WhenProducerIsNotFound() throws Exception {

        var id = 999L;

        BDDMockito.when(service.findByIdOrThrowNotFound(id)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

    }

    @ParameterizedTest
    @MethodSource("putProducerBadRequest")
    @Order(11)
    @DisplayName("PUT/v1/producers returns bad request when fields are invalids")
    void updateReturnBadRequest_WhenFieldsInvalids(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

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
    @MethodSource("postProducerBadRequest")
    @Order(12)
    @DisplayName("POST/v1/produces returns bad request when fields are invalids")
    void updateReturnBadRequest_WhenFieldsAreEmpity(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .header("x-api-key", "v1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postProducerBadRequest() {

        var lastNameRequiredError = "The field 'name' is required";
        var strings = Collections.singletonList(lastNameRequiredError);
        return Stream.of(
                Arguments.of("post/post-request-producer-blank-fields-400.json", strings),
                Arguments.of("post/post-request-producer-empity-fields-400.json", strings)
        );
    }

    private static Stream<Arguments> putProducerBadRequest() {

        return Stream.of(
                Arguments.of("put/put-request-producer-blank-fields-400.json", allErrors()),
                Arguments.of("put/put-request-producer-empity-fields-400.json", allErrors())
        );
    }

    private static List<String> allErrors() {

        var firstNameRequiredError = "The field 'id' cannot be null";
        var lastNameRequiredError = "The field 'name' is required";
        return  new ArrayList<>(List.of(firstNameRequiredError, lastNameRequiredError)
        );
    }





}