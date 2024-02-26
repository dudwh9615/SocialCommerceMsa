package Project.moduleshop.controller;

import Project.moduleshop.dto.ProductOfList;
import Project.moduleshop.dto.ProductRequestDto;
import Project.moduleshop.dto.ProductResponseDto;
import Project.moduleshop.service.ProductService;
import Project.moduleshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/products")
public class ProductController {

    private final ProductService productService;
    private final StockService stockService;

    /*
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.addProduct(productRequestDto);
        return ResponseEntity.ok("상품 등록 완료");
    }

    /*
     * 상품 목록
     */
    @GetMapping("/all")
    public List<ProductOfList> findAllProducts() {
        return productService.findAllProducts();
    }

    /*
     * 상품 상세 페이지
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findProduct(productId));
    }

    @GetMapping("/{productId}/remain")
    public ResponseEntity<Long> checkProductStock(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.getStockInfo(productId));
    }

    /*
     * 남은 수량
     */
//    @GetMapping("/{productId}/stock")
//    public ResponseEntity<Long> checkProductStock(@PathVariable Long productId) {
//        return ResponseEntity.ok(productService.checkProductStock(productId));
//    }
}
