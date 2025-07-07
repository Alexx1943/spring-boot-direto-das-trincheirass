package academy.devdojo.Service;


import academy.devdojo.Domain.Producer;
import academy.devdojo.Repository.ProducerHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodeRepository repository;

    private List<Producer> producerList;

    @BeforeEach
    void init() {

        var teste1 = Producer.builder().id(1L).name("Teste5").createdAt(LocalDateTime.now()).build();
        var teste2 = Producer.builder().id(2L).name("Teste6").createdAt(LocalDateTime.now()).build();
        var teste3 = Producer.builder().id(3L).name("Teste7").createdAt(LocalDateTime.now()).build();
        var teste4 = Producer.builder().id(4L).name("Teste8").createdAt(LocalDateTime.now()).build();

        producerList = new ArrayList<>(List.of(teste1, teste2, teste3, teste4));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns a list with all producers when argument is null")
    void findAllReturnsListProducers_WhenArgumentIsNull() {

        BDDMockito.when(repository.findAll()).thenReturn(producerList);

        var producers = service.findAll(null);

        Assertions.assertThat(producers).isNotNull().hasSameElementsAs(producerList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns a list with found object when name exists")
    void findAllReturnsListProducers_WhenSucessfull() {

        var producerToFound = producerList.getFirst();

        var expectedFound = Collections.singletonList(producerToFound);

        BDDMockito.when(repository.findByName(producerToFound.getName())).thenReturn(expectedFound);

        var producerFind = service.findAll(producerToFound.getName());

        Assertions.assertThat(producerFind).containsAll(expectedFound);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns empity list when name is not found")
    void findByName_ReturnsEmpityList_WhenNameIsNotFound() {

        var name = "Not found";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var producers = service.findAll(name);

        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById returns a producer")
    void findByNameReturnsProducer_WhenSuccesful() {

        var expectedProducer = producerList.getFirst();

        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.of(expectedProducer));

        var producer = service.findByIdOrThrowNotFound(expectedProducer.getId());

        Assertions.assertThat(producer).isEqualTo(expectedProducer);
    }

    @Test
    @Order(5)
    @DisplayName("findById throws responseStatusException when id is not found")
    void findByIdThrowsException_WhenIdIsNotFound() {

        var expectedProducer = producerList.getFirst();

        BDDMockito.when(repository.findById(expectedProducer.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProducer.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save created pruoducer")
    void saveCreatedProducer_WhenSucessful() {

        var producerToSave = Producer.builder()
                .id(ThreadLocalRandom.current().nextLong(764))
                .name("Teste")
                .createdAt(LocalDateTime.now())
                .build();

        BDDMockito.when(repository.save(producerToSave)).thenReturn(producerToSave);

        var producer = service.save(producerToSave);

        Assertions.assertThat(producer).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete removed a producer")
    void deleteRemovedProducer_WhenSucessfull(){

        var producerToDelete = producerList.getFirst();

        BDDMockito.when(repository.delete(producerToDelete))


    }




}