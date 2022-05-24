package ru.csv.order_management.domain.command;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum CommandType {
    START(List.of("start", "старт", "/start")),
    HISTORY(List.of("history", "история")),
    PRICE(List.of("price", "прайс")),
    ADD_ADDRESS(List.of("ADD_ADDR")),
    SET_ADDRESS(List.of("SET_ADDR")),
    BASKET(List.of("basket", "корзина")),
    CHECKOUT(List.of("checkout", "оформить заказ")),
    ADD_ITEM(List.of("add Item", "Добавить товар")),
    VIEW_ORDERS(List.of("view orders", "Посмотреть заказы")),
    GET_ITEM_INFO(List.of("GET_INFO")),
    ADD_TO_CART(List.of("ADD_TO_CART")),
    FIND_CHILD(List.of("FIND_CHILD")),
    DELETE_ITEM(List.of("DELETE", "DEL_ITEM", "delete Item", "Удалить товар")),
    UNKNOWN(List.of());

    private final List<String> title;

    public static CommandType from(String name) {
        return Arrays.stream(CommandType.values()).filter(type -> type.title.contains(name)).findFirst().orElse(UNKNOWN);
    }
}
