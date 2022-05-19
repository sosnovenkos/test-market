package ru.exampl.bot2.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.exampl.bot2.store.entity.DbEntityTimeslot;

public interface TimeslotJpaRepository extends JpaRepository<DbEntityTimeslot, Long> {

//    DbEntityTimeslot findByStatusAndUserId(String cart, Long userId);

}
