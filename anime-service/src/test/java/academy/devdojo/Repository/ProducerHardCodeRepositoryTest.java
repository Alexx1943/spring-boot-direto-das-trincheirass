package academy.devdojo.Repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProducerHardCodeRepositoryTest {

    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Test
    @DisplayName("findAll returns a list with all producers")
    void findAllReturnsAllProducers_whenSucessful(){
        var producer = repository.findAll();

        Assertions.assertThat(producer).isNotNull().hasSize(4);
    }
}