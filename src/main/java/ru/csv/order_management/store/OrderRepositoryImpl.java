package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.DbEntityOrder;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl {
    private final OrderJpaRepository orderJpaRepository;

    public DbEntityOrder saveOrder(DbEntityOrder order) {
        return orderJpaRepository.save(order);
    }

    public DbEntityOrder findById(Long id) {
        return orderJpaRepository.findById(id).get();
    }

    public List<DbEntityOrder> findAllByUserId(Long userId) {
        return orderJpaRepository.findAllByUserId(userId);
    }

    public DbEntityOrder findOrderInCartStatus(Long userId) {
        return orderJpaRepository.findByStatusAndUserId("cart", userId);
    }
}