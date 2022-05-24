package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.DbEntityTimeslot;

import java.util.List;

public interface TimeslotJpaRepository extends JpaRepository<DbEntityTimeslot, Long> {
    List<DbEntityTimeslot> findByParentId(Long parentId);
}
