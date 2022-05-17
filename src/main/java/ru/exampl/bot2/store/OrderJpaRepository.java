package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityOrder;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<DbEntityOrder, UUID> {
    DbEntityOrder findByStatusAndUserId(String status, Long userId);
}
