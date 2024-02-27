package Project.moduleshop.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StockService {
    private final String PRODUCT_ID = "productId";
    private final RedisTemplate<String, Integer> redisTemplate;
    private final RedissonClient redissonClient;

    public void addStockInfo(Long productId, Integer stock) {
        redisTemplate.opsForValue().set(PRODUCT_ID + productId, stock);
    }

    public Integer getStockInfo(Long productId) {
        return redisTemplate.opsForValue().get(PRODUCT_ID + productId);
    }

    public void decreaseStock(Long productId) {
        String key = PRODUCT_ID + productId;
        RLock lock = redissonClient.getLock(key);

        try {
            boolean isLocked = lock.tryLock(10, 3, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new IllegalStateException("락이 걸리지 않았습니다.");
            }

            Integer remain = redisTemplate.opsForValue().get(key);
            if (remain != null && remain > 0) {
                redisTemplate.opsForValue().set(key, remain-1);
            } else {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }

        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());

        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}



/**
 * DB 사용 시
 */
//    private final StockRepository stockRepository;
//    private final ProductRepository productRepository;
//
//    public void addStockInfo(Long productId, Long stock) {
//        stockRepository.save(Stock
//                .builder()
//                .productId(productId)
//                .stock(stock)
//                .build()
//        );
//    }
//
//    public Long getStockInfo(Long productId) {
//        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
//        System.out.println("===============================================================" + stockInfo.getStock());
//        return stockInfo.getStock();
//    }
//    public void decreaseStock(Long productId) {
//        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
//        Long remain = stockInfo.getStock();
//        if (remain.equals(0L)) {
//            throw new IllegalStateException("재고가 부족합니다.");
//        }
//        stockInfo.setStock(remain-1);
//        stockRepository.save(stockInfo);
//    }

//    public void increaseStock(Long productId) {
//        Stock stockInfo = stockRepository.findById(productId).orElseThrow();
//        Long ogStock = productRepository.findById(productId).orElseThrow().getStock();
//        Long remain = stockInfo.getStock();
//
//        if (remain.equals(ogStock)) {
//            throw new IllegalStateException("재고 초과");
//        }
//        stockInfo.setStock(remain+1);
//        stockRepository.save(stockInfo);
//    }