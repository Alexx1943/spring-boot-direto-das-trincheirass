package academy.devdojo.Request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnimePutRequest {

    private Long id;
    private String name;
}
