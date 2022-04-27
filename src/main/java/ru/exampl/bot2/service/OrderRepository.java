package ru.exampl.bot2.service;

import ru.exampl.bot2.domain.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findByUserId(String userid);
}
