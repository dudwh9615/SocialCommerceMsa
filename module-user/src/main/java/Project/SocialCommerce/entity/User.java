package Project.SocialCommerce.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="pwd", nullable = false)
    private String pwd;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="profile", nullable = false)
    private String profile;

    @Column(name="greetings", nullable = false)
    private String greetings;

//    private String auth;

}
