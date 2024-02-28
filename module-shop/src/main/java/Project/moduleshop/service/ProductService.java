package Project.moduleshop.service;

import Project.moduleshop.dto.ProductOfList;
import Project.moduleshop.dto.ProductRequestDto;
import Project.moduleshop.dto.ProductResponseDto;
import Project.moduleshop.entity.Product;
import Project.moduleshop.entity.Stock;
import Project.moduleshop.repository.ProductRepository;
import Project.moduleshop.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockService stockService;
//    private final RedissonClient redissonClient;


    public void addProduct(ProductRequestDto productRequestDto) {
        /*
         * Product에 ProductRequestDto의 내용 저장
         * Redis에는 product+상품Id:재고 형태로 저장
         */
//        RMap<Long, Long> stockRepo = redissonClient.getMap("productStock");

        Product product = Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .stock(productRequestDto.getStock())
                .explain(productRequestDto.getExplain())
                .openAt(LocalDateTime.parse(productRequestDto.getOpenAt()))
                .build();

        Long productId = productRepository.save(product).getId();
        stockService.addStockInfo(productId, product.getStock());
    }

    public List<ProductOfList> findAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    private ProductOfList toDto(Product product) {
        return ProductOfList.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }

    public ProductResponseDto findProduct(Long productId) {
        Product res = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException("해당 ID의 상품이 존재하지 않습니다.")
        );
        return ProductResponseDto.builder()
                .name(res.getName())
                .price(res.getPrice())
                .explain(res.getExplain())
                .build();
    }

    /**
     * redis
     */
//    public Long checkProductStock(Long productId) {
//        RMap<Long, Long> stockRepo = redissonClient.getMap("productStock");
//        return stockRepo.get(productId);
//    }
}
