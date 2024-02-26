package Project.moduleshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_stock")
public class Stock {
    @Id
    private Long productId;
    private Long stock;
}
