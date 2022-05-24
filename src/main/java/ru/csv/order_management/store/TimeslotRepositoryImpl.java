package ru.csv.order_management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.store.entity.DbEntityTimeslot;

import java.util.List;

@Repository
public class TimeslotRepositoryImpl {

    @Autowired
    private TimeslotJpaRepository timeslotJpaRepository;


    public List<DbEntityTimeslot> findAllItems() {
        return timeslotJpaRepository.findAll();
    }

    public List<DbEntityTimeslot> findHeadGroup() {
        return timeslotJpaRepository.findByParentId(null);
    }

    public List<DbEntityTimeslot> findChildGroup(Long parentId) {
        return timeslotJpaRepository.findByParentId(parentId);
    }

    public DbEntityTimeslot findItem(Long id){
        return timeslotJpaRepository.findById(id).get();
    }
}
