package ru.exampl.bot2;

import ru.exampl.bot2.command.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findByOrderNumber(String userId, int orderNumber);
}
