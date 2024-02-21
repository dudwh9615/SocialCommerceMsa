package Project.moduleshop.service;

import Project.moduleshop.dto.ProductRequestDto;
import Project.moduleshop.entity.Product;
import Project.moduleshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public void addProduct(ProductRequestDto productRequestDto) {
        /*
         * Product에 ProductRequestDto의 내용 저장
         * Redis에는 product+상품Id:재고 형태로 저장
         */
        Product product = Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .stock(productRequestDto.getStock())
                .explain(productRequestDto.getExplain())
                .openAt(LocalDateTime.parse(productRequestDto.getOpenAt()))
                .build();

    }

    public Page<ProductOfList> findAllProducts() {
    }

    public Object findProduct(ProductRequestDto productRequestDto) {
    }

    public Long checkProductStock(Long productId) {
    }
}
