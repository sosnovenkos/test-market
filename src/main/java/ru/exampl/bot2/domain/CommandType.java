package ru.exampl.bot2.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public enum CommandType {
    START(List.of("start", "старт", "/start")),
    HISTORY(List.of("history", "история")),
    PRISE(List.of("price", "прайс")),
    BASKET(List.of("basket", "корзина")),
    CHECKOUT(List.of("checkout", "оформить заказ")),
    ADDITEM(List.of("add Item", "Добавить товар")),
    TIMESLOT(List.of("timeslot", "записаться на приём")),
    DELETEITEM(List.of("delete Item", "Удалить товар")),
    VIEWORDERS (List.of("view orders", "Посмотреть заказы")),
    NUMBEROFVISIT (List.of("Кол-во посещений", "Number of visits")),
    UNKNOWN(List.of());

    private final List<String> title;

    public static CommandType findById(String name) {
        var listOfEnums = CommandType.values();
        for (CommandType command : listOfEnums) {
            var list = command.title;
            if (list.contains(name)) {
                return command;
            }
        }
        return UNKNOWN;
//        return Arrays.stream(Command.values()).filter(value -> value.title.contains(name)).findFirst().orElse(UNKNOWN);
    }
}
