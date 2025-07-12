package academy.devdojo.Controller;

import academy.devdojo.Domain.Producer;
import academy.devdojo.Repository.ProducerData;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerData producerData;

    private List<Producer> producerList;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {


        var dateTime = "2025-07-12T14:55:39.226635219";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


        var teste1 = Producer.builder().id(1L).name("Teste5").createdAt(localDateTime).build();
        var teste2 = Producer.builder().id(2L).name("Teste6").createdAt(localDateTime).build();
        var teste3 = Producer.builder().id(3L).name("Teste7").createdAt(localDateTime).build();
        var teste4 = Producer.builder().id(4L).name("Teste8").createdAt(localDateTime).build();

        producerList = new ArrayList<>(List.of(teste1, teste2, teste3, teste4));
    }


    @Test
    @Order(1)
    @DisplayName("findAll returns a list producers when argument is null")
    void findAllReturnsListProducers_WhenArgumentIsNull() throws Exception {

        BDDMockito.when(producerData.getProducers()).thenReturn(producerList);

        var response = readResourceFile("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }


}