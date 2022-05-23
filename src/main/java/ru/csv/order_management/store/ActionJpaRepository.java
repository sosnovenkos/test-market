package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityAction;

import java.util.List;

public interface ActionJpaRepository extends JpaRepository<DbEntityAction, Long> {
    List<DbEntityAction> findByUserId(Integer userId);

    DbEntityAction findByUserName(String s);

}
