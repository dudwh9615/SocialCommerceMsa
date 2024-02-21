package Project.moduleshop.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductRequestDto {
    private String name;
    private Long price;
    private Long stock;
    private String explain;
    private String openAt;
}
