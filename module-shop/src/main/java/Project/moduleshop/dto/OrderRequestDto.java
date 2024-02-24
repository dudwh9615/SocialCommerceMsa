package Project.moduleshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long userId;
    private Long productId;
}
