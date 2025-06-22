package academy.devdojo.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnimePostRequest {

    private Long id;
    private String name;

}
