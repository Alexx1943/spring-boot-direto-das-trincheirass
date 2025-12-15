package academy.devdojo.dto.post;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.EnableMBeanExport;

@Getter
@Setter
@ToString
public class ProfilePostRequest {

    @Schema(name = "profile's name", description = "")
    @NotBlank(message = "The field 'name' is required")
    private String name;

    @NotBlank(message = "The field 'description' is required")
    private String description;

}
