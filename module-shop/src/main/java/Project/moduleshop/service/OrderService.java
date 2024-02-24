package Project.moduleshop.service;

import Project.moduleshop.dto.OrderRequestDto;
import Project.moduleshop.entity.Order;
import Project.moduleshop.entity.OrderStatusEnum;
import Project.moduleshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RedissonClient redissonClient;

    public Long createOrder(OrderRequestDto orderRequestDto) {
        Order order = Order.builder()
                .userId(orderRequestDto.getUserId())
                .productId(orderRequestDto.getProductId())
                .orderStatus(OrderStatusEnum.UNPAID)
                .build();
        return orderRepository.save(order).getId();
    }

    public void executeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        RMap<Long, Long> stockRepo = redissonClient.getMap("productStock");
        Long productId = order.getProductId();
        RLock lock = redissonClient.getMap("productStock").getLock(productId);

        try {
            if (!lock.tryLock()) {
                Long stock = stockRepo.get(productId);
                if (stock.equals(0L)) {
                    throw new IllegalArgumentException("남은 재고가 없습니다.");
                }
                stockRepo.put(productId, stock-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
