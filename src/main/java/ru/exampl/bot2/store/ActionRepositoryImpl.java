package ru.exampl.bot2.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.AddItemCommand;
import ru.exampl.bot2.domain.command.ActionHistoryCommand;
import ru.exampl.bot2.domain.command.StartCommand;
import ru.exampl.bot2.store.entity.DbEntityAction;
import ru.exampl.bot2.store.entity.DbEntityItems;
import ru.exampl.bot2.sender.Sender;

import java.time.OffsetDateTime;

@Slf4j
@Repository
public class ActionRepositoryImpl {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActionJpaRepository actionJpaRepository;
    @Autowired
    private Sender sender;

    public void save(StartCommand command) throws JsonProcessingException {
        DbEntityAction dbEntityAction = new DbEntityAction();
        dbEntityAction.setUserId(command.getUserId());
        dbEntityAction.setUserName(command.getFirstName());
        dbEntityAction.setCreatedAt(OffsetDateTime.now());
//        Gson gson = new Gson();
//        gson.
//        dbEntityAction.setData(GsonFactoryBean.OBJECT_TYPE_ATTRIBUTE.(objectMapper.writeValueAsString(dbEntityAction)));
        dbEntityAction.setData(objectMapper.writeValueAsString(dbEntityAction));
        actionJpaRepository.save(dbEntityAction);
//        vClientJpaRepository.findByUserId(command.getUserId())
    }
    
    public void getHistory(ActionHistoryCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(command.getChatId());

        for (DbEntityAction dbEntityAction : actionJpaRepository.findByUserId(command.getUserId())) {
            sendMessage.setText(String.valueOf(dbEntityAction.toString()));
            sender.send(sendMessage);
        }

    }

    public void addItem(AddItemCommand command){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(command.getChatId());
        DbEntityItems dbEntityItems = new DbEntityItems();
        sendMessage.setText("Введите код продукта");
//        dbEntityItems.setProductCode();
    }


}

