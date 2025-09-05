package academy.devdojo.Repository;

import academy.devdojo.Domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findByName(String name);
}
