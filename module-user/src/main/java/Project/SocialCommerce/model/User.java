package Project.SocialCommerce.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "follow_relationship",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> following;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;

//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Interaction> interactions = new ArrayList<>();

}
