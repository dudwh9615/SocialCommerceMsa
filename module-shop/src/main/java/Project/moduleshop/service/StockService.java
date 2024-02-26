package Project.moduleshop.service;

import Project.moduleshop.entity.Stock;
import Project.moduleshop.repository.ProductRepository;
import Project.moduleshop.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    public void addStockInfo(Long productId, Long stock) {
        stockRepository.save(Stock
                .builder()
                .productId(productId)
                .stock(stock)
                .build()
        );
    }

    public Long getStockInfo(Long productId) {
        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
        System.out.println("===============================================================" + stockInfo.getStock());
        return stockInfo.getStock();
    }

    public void decreaseStock(Long productId) {
        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
        Long remain = stockInfo.getStock();
        if (remain.equals(0L)) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        stockInfo.setStock(remain-1);
        stockRepository.save(stockInfo);
    }

    public void increaseStock(Long productId) {
        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
        Long ogStock = productRepository.findById(productId).orElseThrow().getStock();
        Long remain = stockInfo.getStock();

        if (remain.equals(ogStock)) {
            throw new IllegalStateException("재고 초과");
        }
        stockInfo.setStock(remain+1);
        stockRepository.save(stockInfo);
    }
}
