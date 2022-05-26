package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Order;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl {
    private final OrderJpaRepository orderJpaRepository;

    public Order saveOrder(Order order) {
        return orderJpaRepository.save(order);
    }

    public Order findById(Long id) {
        return orderJpaRepository.findById(id).get();
    }

    public List<Order> findAllByUserId(Long userId) {
        return orderJpaRepository.findAllByUserId(userId);
    }

    public Order findOrderInCartStatus(Long userId) {
        return orderJpaRepository.findByStatusAndUserId("cart", userId);
    }
}