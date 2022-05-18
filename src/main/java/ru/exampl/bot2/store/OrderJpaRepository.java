package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityOrder;


public interface OrderJpaRepository extends JpaRepository<DbEntityOrder, Long> {
    DbEntityOrder findByStatusAndUserId(String status, Long userId);
}
