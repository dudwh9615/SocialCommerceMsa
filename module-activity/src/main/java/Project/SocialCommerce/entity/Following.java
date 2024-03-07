package Project.SocialCommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "following")
@Entity
@NoArgsConstructor
public class Following {
    @Id
    private Long myId;
    private List<Long> following = new ArrayList<>();
    private List<Long> follower = new ArrayList<>();
}
