package ru.exampl.bot2.service;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.exampl.bot2.domain.command.HistoryCommand;
import ru.exampl.bot2.domain.command.StartCommand;
import ru.exampl.bot2.entity.DbEntityAction;
import ru.exampl.bot2.sender.Sender;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VClientRepositoryImpl {
    private final ObjectMapper objectMapper;

    private final VClientJpaRepository vClientJpaRepository;
    private final Sender sender;

    public void save(StartCommand command) throws JsonProcessingException {
        DbEntityAction dbEntityAction = new DbEntityAction();
        dbEntityAction.setUserId(command.getUserId());
        dbEntityAction.setUserName(command.getUserName());
        dbEntityAction.setCreatedAt(OffsetDateTime.now());
        Gson gson = new Gson();
//        gson.
//        dbEntityAction.setData(GsonFactoryBean.OBJECT_TYPE_ATTRIBUTE.(objectMapper.writeValueAsString(dbEntityAction)));
        dbEntityAction.setData(objectMapper.writeValueAsString(dbEntityAction));
        vClientJpaRepository.save(dbEntityAction);
//        vClientJpaRepository.findByUserId(command.getUserId())
    }
    
    public void getHistory(HistoryCommand command) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(command.getChatId());
        for (DbEntityAction dbEntityAction : vClientJpaRepository.findByUserId(command.getUserId())) {
            sendMessage.setText(String.valueOf(dbEntityAction));
            sender.send(sendMessage);
        }

    }


}
