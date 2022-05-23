package ru.csv.order_management.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.csv.order_management.sender.Sender;
import ru.csv.order_management.store.entity.DbEntityAction;
import ru.csv.order_management.store.entity.DbEntityItems;
import ru.csv.order_management.domain.command.AddItemCommand;
import ru.csv.order_management.domain.command.ActionHistoryCommand;
import ru.csv.order_management.domain.command.StartCommand;

import java.time.OffsetDateTime;

@Slf4j
@Repository
public class ActionRepositoryImpl {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActionJpaRepository actionJpaRepository;

    public void save(StartCommand command) throws JsonProcessingException {
        DbEntityAction dbEntityAction = new DbEntityAction();
        dbEntityAction.setUserId(command.getUserId());
        dbEntityAction.setUserName(command.getFirstName());
        dbEntityAction.setCreatedAt(OffsetDateTime.now());
        dbEntityAction.setData(objectMapper.writeValueAsString(dbEntityAction));
        actionJpaRepository.save(dbEntityAction);
    }
}

