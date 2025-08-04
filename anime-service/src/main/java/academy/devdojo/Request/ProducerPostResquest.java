package academy.devdojo.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProducerPostResquest {


    @NotBlank(message = "The field 'name' is required")
    private String name;

}
