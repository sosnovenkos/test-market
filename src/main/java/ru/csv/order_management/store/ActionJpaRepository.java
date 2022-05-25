package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Action;

public interface ActionJpaRepository extends JpaRepository<Action, Long> {
    Action findByUserIdAndWaitingForAction(Long userId, boolean waitingForAction);

    Action findByUserName(String s);

}
