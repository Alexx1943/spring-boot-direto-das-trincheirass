package academy.devdojo.dto.post;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UserPostRequest {


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
