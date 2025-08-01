package academy.devdojo.Request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPostRequest {


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
