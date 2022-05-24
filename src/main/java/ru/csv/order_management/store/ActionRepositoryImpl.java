package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.DbEntityAction;

@Slf4j
@Repository
@AllArgsConstructor
public class ActionRepositoryImpl {
    private final ActionJpaRepository actionJpaRepository;

    public void save(DbEntityAction action) {
        actionJpaRepository.save(action);
    }


    public DbEntityAction findWaitingForActionByUserId(Long userId) {
        return actionJpaRepository.findByUserIdAndWaitingForAction(userId, true);
    }
}

