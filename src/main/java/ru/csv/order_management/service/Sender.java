package ru.csv.order_management.service;

import ru.csv.order_management.domain.context.Context;
import ru.csv.order_management.store.entity.MessageToBeDeleted;

import java.util.List;

public interface Sender  {
    List<MessageToBeDeleted> prepareAndSend(Context context);

    void delete(List<MessageToBeDeleted> messagesToBeDeleted);
}