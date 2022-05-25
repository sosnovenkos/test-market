package ru.csv.order_management.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.csv.order_management.store.entity.MessageToBeDeleted;

import java.util.List;

@Repository
@AllArgsConstructor
public class MessageToBeDeletedRepositoryImpl {
    private final MessageToBeDeletedJpaRepository messageToBeDeletedJpaRepository;

    public List<MessageToBeDeleted> save(List<MessageToBeDeleted> messagesToBeDeleted) {
        return messageToBeDeletedJpaRepository.saveAll(messagesToBeDeleted);
    }

    public MessageToBeDeleted findById(Long id) {
        return messageToBeDeletedJpaRepository.findById(id).get();
    }

    public List<MessageToBeDeleted> findAllByUserId(Long userId) {
        return messageToBeDeletedJpaRepository.findAllByUserId(userId);
    }

    public void deleteAllByUserId(Long userId) {
        messageToBeDeletedJpaRepository.deleteByUserId(userId);
    }
    public void deleteAll(List<MessageToBeDeleted> messagesToBeDeletedNow) {
        messageToBeDeletedJpaRepository.deleteAll(messagesToBeDeletedNow);
    }

}