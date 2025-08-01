package academy.devdojo.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProducerPutRequest {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
