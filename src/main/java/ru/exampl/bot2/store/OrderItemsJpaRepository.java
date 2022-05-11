package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.exampl.bot2.store.entity.DbEntityOrdersItems;

import java.util.UUID;


public interface OrderItemsJpaRepository extends JpaRepository<DbEntityOrdersItems, UUID> {
}
