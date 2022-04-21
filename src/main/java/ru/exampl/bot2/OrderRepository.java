package ru.exampl.bot2;

import java.util.List;

public interface OrderRepository {
    List<Order> findByUserId(String userid);
}
