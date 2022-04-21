package ru.exampl.bot2.command;

import ru.exampl.bot2.Order;
import ru.exampl.bot2.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public List<Order> findByUserId(String userid) {
        return null;
    }
}
