package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityOrder;

import java.util.List;


public interface OrderJpaRepository extends JpaRepository<DbEntityOrder, Long> {
    DbEntityOrder findByStatusAndUserId(String status, Long userId);

    List<DbEntityOrder> findAllByUserId(Long userId);
}
