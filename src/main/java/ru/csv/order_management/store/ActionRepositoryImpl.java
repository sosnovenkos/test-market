package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Action;

@Slf4j
@Repository
@AllArgsConstructor
public class ActionRepositoryImpl {
    private final ActionJpaRepository actionJpaRepository;

    public void save(Action action) {
        actionJpaRepository.save(action);
    }


    public Action findWaitingForActionByUserId(Long userId) {
        return actionJpaRepository.findByUserIdAndWaitingForAction(userId, true);
    }
}

