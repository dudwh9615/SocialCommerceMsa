package Project.moduleshop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    private String name;
    private Long price;
    private String explain;
}
