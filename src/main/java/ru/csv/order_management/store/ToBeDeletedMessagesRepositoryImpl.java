package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.Order;
import ru.csv.order_management.store.entity.ToBeDeletedMessages;

import java.util.List;

@Repository
@AllArgsConstructor
public class ToBeDeletedMessagesRepositoryImpl {
    private final ToBeDeletedMessagesJpaRepository toBeDeletedMessagesJpaRepository;

    public ToBeDeletedMessages save(ToBeDeletedMessages order) {
        return toBeDeletedMessagesJpaRepository.save(order);
    }

    public ToBeDeletedMessages findById(Long id) {
        return toBeDeletedMessagesJpaRepository.findById(id).get();
    }

    public List<ToBeDeletedMessages> findAllByUserId(Long userId) {
        return toBeDeletedMessagesJpaRepository.findAllByUserId(userId);
    }

    public ToBeDeletedMessages findOrderInCartStatus(Long userId) {
        return toBeDeletedMessagesJpaRepository.findByStatusAndUserId("cart", userId);
    }
}