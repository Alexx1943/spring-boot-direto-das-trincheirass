package academy.devdojo.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileGetResponse {

    public record UserRecord(Long id, String firstName) {}
    public record ProfileRecord(Long id, String name) {}

    private Long id;
    private UserRecord user;
    private ProfileRecord profile;
}
