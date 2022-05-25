package ru.csv.order_management.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.csv.order_management.domain.context.Context;

import java.util.List;

public interface Sender  {
    List<Message> prepareAndSend(Context context);
}