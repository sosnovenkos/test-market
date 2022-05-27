package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Timeslot;

public interface TimeslotJpaRepository extends JpaRepository<Timeslot, String> {
}
