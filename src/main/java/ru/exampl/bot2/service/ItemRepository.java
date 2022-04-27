package ru.exampl.bot2.service;

import ru.exampl.bot2.domain.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findByOrderNumber(String userId, int orderNumber);
}
