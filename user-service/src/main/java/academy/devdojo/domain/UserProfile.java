package academy.devdojo.domain;


import jakarta.persistence.*;
import lombok.*;

@With
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Profile profile;

}
