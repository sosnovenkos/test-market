package ru.csv.order_management.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Timeslot;

import java.util.List;

@Repository
public class TimeslotRepositoryImpl {
    @Autowired
    private TimeslotJpaRepository timeslotJpaRepository;


    public List<Timeslot> findAllItems() {
        return timeslotJpaRepository.findAll();
    }

    public List<Timeslot> findHeadGroup() {
        return timeslotJpaRepository.findByParentId(null);
    }

    public List<Timeslot> findChildGroup(Long parentId) {
        return timeslotJpaRepository.findByParentId(parentId);
    }

    public Timeslot findItem(Long id){
        return timeslotJpaRepository.findById(id).get();
    }
}

}
