package academy.devdojo.dto.put;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPutRequest {


    @Schema(name = "id", example = "13")
    @NotNull(message = "The field 'id' cannot be null")
    private Long id;

    @Schema(name = "firstName", example = "Policarpo")
    @NotBlank(message = "The field 'firstName' is required")
    private String firstName;

    @Schema(name = "lastName", example = "Quaresma")
    @NotBlank(message = "The field 'lastName' is required")
    private String lastName;

    @Schema(name = "email", example = "PolicarpoQuaresma@gmail.com")
    @NotBlank(message = "The field  'email' is required")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,10}$", message = "The e-mail is not valid")
    private String email;
}
