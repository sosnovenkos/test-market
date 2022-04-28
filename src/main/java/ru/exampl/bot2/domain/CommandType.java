package ru.exampl.bot2.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public enum CommandType {
    START(List.of("start", "старт")),
    HISTORY(List.of("history", "история")),
    MENU(List.of("menu", "меню")),
    ADDITEM(List.of("addItem", "Добавить товар")),
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
