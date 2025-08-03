package academy.devdojo.Request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPutRequest {

    @NotNull(message = "The field 'id' cannot be null")
    private Long id;

    @NotBlank(message = "The field 'firstName' is required")
    private String firstName;

    @NotBlank(message = "The field 'lastName' is required")
    private String lastName;

    @NotBlank
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The e-mail is not valid")
    private String email;
}
