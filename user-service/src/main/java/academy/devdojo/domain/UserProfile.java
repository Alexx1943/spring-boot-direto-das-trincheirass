package academy.devdojo.domain;


import jakarta.persistence.*;
import lombok.*;

@With
@Setter
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "UserProfile.fullDetails",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("profile")})
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
