package academy.devdojo.Repository;

import academy.devdojo.Commons.ProducersUtils;
import academy.devdojo.Domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodeRepositoryTest {

    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Mock
    private ProducerData producerData;
    private List<Producer> listProducers;

    @InjectMocks
    private ProducersUtils producersUtils;

    @BeforeEach
    void init() {

        listProducers = producersUtils.newProducers();
    }


    @Test
    @DisplayName("findAll returns a list with all producers")
    @Order(1)
    void findAllReturnsAllProducers_whenSucessful() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var producer = repository.findAll();

        Assertions.assertThat(producer).isNotNull().hasSize(4);
    }

    @Test
    @DisplayName("findById returns producer with given id")
    @Order(2)
    void findByIdReturnsProducerById() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var expectedProducer = listProducers.getFirst();

        var producer = repository.findById(expectedProducer.getId());

        Assertions.assertThat(producer).isPresent().contains(expectedProducer);
    }

    @Test
    @DisplayName("findByName returns empty list when name is null")
    @Order(3)
    void findByName_ReturnsEmptyList_whenNameIsNull() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var producer = repository.findByName(null);

        Assertions.assertThat(producer).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list with found object when name exists")
    @Order(4)
    void findByName_ReturnsFoundProducersInList_WhenNameIsFound() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var expectedProducer = listProducers.getFirst();

        var producer = repository.findByName(expectedProducer.getName());

        Assertions.assertThat(producer).contains(expectedProducer);
    }

    @Test
    @DisplayName("save created producer")
    @Order(5)
    void saveCreatesProducer_WhenSucessful() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var produceToSave = producersUtils.newProducerToSave();

        var producer = repository.save(produceToSave);

        Assertions.assertThat(producer).isEqualTo(produceToSave).hasNoNullFieldsOrProperties();

        var producerSavedOptional = repository.findById(producer.getId());

        Assertions.assertThat(producerSavedOptional).isPresent().contains(produceToSave);
    }

    @Test
    @DisplayName("delete remove producer")
    @Order(6)
    void deleteRemoveProducer_WhenSucessful() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);


        var producerToRemuve = listProducers.getFirst();

        repository.delete(producerToRemuve);

        var producers = repository.findAll();

        Assertions.assertThat(producers).isNotEmpty().doesNotContain(producerToRemuve);
    }

    @Test
    @DisplayName("update updates producer")
    @Order(7)
    void update_Update() {

        BDDMockito.when(producerData.getProducers()).thenReturn(listProducers);

        var producerUpdated = this.listProducers.getFirst();

        producerUpdated.setName("ALex");

        repository.update(producerUpdated);

        Assertions.assertThat(this.listProducers).contains(producerUpdated);

        var producerOptional = repository.findById(producerUpdated.getId());

        Assertions.assertThat(producerOptional).isPresent();
        Assertions.assertThat(producerOptional.get().getName()).isEqualTo(producerUpdated.getName());


    }


}