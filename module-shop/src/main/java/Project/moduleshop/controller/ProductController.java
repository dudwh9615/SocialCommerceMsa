package Project.moduleshop.controller;

import Project.moduleshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
    public Page<ProductOfList> findAllProducts() {
        return productService.findAllProducts();
    }

    /*
     * 상품 상세 페이지
     */
    @GetMapping
    public ResponseEntity<ProductResponseDto> findProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.findProduct(productRequestDto));
    }

    /*
     * 남은 수량
     */
    @GetMapping("/{productId}/stock")
    public ResponseEntity<Long> checkProductStock(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.checkProductStock(productId));
    }
}
