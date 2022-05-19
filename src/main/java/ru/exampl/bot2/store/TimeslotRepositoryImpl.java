package ru.exampl.bot2.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.exampl.bot2.store.entity.DbEntityItems;
import ru.exampl.bot2.store.entity.DbEntityOrder;
import ru.exampl.bot2.store.entity.DbEntityTimeslot;

@Repository
public class TimeslotRepositoryImpl {

    @Autowired
    private TimeslotJpaRepository timeslotJpaRepository;

    public DbEntityTimeslot saveTimeslot(DbEntityTimeslot timeslot) {
        try {
            return timeslotJpaRepository.save(timeslot);
        } catch (Exception e){
            e.printStackTrace();
        }
        return timeslot;
    }

//    public DbEntityTimeslot findTimeslotInCartStatus(Long userId) {
//        return timeslotJpaRepository.findByStatusAndUserId("cart", userId);
//    }

//    public DbEntityTimeslot findOrder(Long userId) {
//        return timeslotJpaRepository.getById(userId);
//    }
}
