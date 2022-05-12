package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityOrders;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<DbEntityOrders, UUID> {
//    DbEntityOrders findByStatus(String status);
}
