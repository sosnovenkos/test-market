package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityItems;

import java.util.List;


public interface ItemJpaRepository extends JpaRepository<DbEntityItems, Long> {
    List<DbEntityItems> findByParentId(Long parentId);
}
