package academy.devdojo.dto.get;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileGetResponse {

    private Long id;
    private String name;
    private String description;
}
