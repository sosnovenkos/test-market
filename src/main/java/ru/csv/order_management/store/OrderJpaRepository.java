package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Order;

import java.util.List;


public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByStatusAndUserId(String status, Long userId);

    List<Order> findAllByUserId(Long userId);

    List<Order> findByDate(String date);

    Order findByTime(String time);
}
