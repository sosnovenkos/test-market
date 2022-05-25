package ru.csv.order_management.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.csv.order_management.domain.context.Context;
import ru.csv.order_management.service.MessageFactory;
import ru.csv.order_management.service.Sender;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramSender extends DefaultAbsSender implements Sender {
    @Value("${bot.token}")
    private String token;

    @Autowired
    private MessageFactory messageFactory;

    @Autowired
    private ObjectMapper objectMapper;

    protected TelegramSender() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public List<Message> sendList(List<SendMessage> sendMessageList) throws TelegramApiException, JsonProcessingException {
        List<Message> executedMessages = new ArrayList<>();
        for (SendMessage sendMessage : sendMessageList) {
            var executedMessage = execute(sendMessage);
            executedMessages.add(executedMessage);
            log.info(objectMapper.writeValueAsString(executedMessage));
        }
        return executedMessages;
    }

    public void send(List<DeleteMessage> deleteMessages) throws TelegramApiException {
        for (DeleteMessage sendMessage : deleteMessages) {
            var x = execute(sendMessage);
        }
    }

    @Override
    @SneakyThrows
    public List<Message> prepareAndSend(Context context) {
       return sendList(messageFactory.createMessages(context));
    }
}