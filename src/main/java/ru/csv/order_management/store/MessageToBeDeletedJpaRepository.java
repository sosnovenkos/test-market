package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Order;
import ru.csv.order_management.store.entity.MessageToBeDeleted;

import java.util.List;


public interface MessageToBeDeletedJpaRepository extends JpaRepository<MessageToBeDeleted, Long> {

//    MessageToBeDeleted findByStatusAndUserId(String status, Long userId);

    List<MessageToBeDeleted> findAllByUserId(Long userId);

    void deleteByUserId(Long aLong);

    void deleteAllByUserId(Long userId);
}
