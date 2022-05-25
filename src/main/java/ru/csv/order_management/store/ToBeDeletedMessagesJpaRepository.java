package ru.csv.order_management.store;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.csv.order_management.store.entity.Order;
import ru.csv.order_management.store.entity.ToBeDeletedMessages;

import java.util.List;


public interface ToBeDeletedMessagesJpaRepository extends JpaRepository<ToBeDeletedMessages, Long> {

    ToBeDeletedMessages findByStatusAndUserId(String status, Long userId);

    List<ToBeDeletedMessages> findAllByUserId(Long userId);
}
