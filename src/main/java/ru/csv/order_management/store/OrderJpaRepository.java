package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityOrder;


public interface OrderJpaRepository extends JpaRepository<DbEntityOrder, Long> {
    DbEntityOrder findByStatusAndUserId(String status, Long userId);
}
