package academy.devdojo.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;



}
