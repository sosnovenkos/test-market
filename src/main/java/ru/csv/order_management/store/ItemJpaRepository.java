package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Items;

import java.util.List;


public interface ItemJpaRepository extends JpaRepository<Items, Long> {
    List<Items> findByParentId(Long parentId);
}
