package academy.devdojo.Request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPutRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
