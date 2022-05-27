package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Timeslot;

import java.util.List;

public interface TimeslotJpaRepository extends JpaRepository<Timeslot, Long> {

    List<Timeslot> findByParentId(Long parentId);

}
