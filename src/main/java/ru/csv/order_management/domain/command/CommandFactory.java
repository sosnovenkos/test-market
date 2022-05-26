package ru.csv.order_management.domain.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class CommandFactory {
    private static final String DELIMITER = ":";

    public static Command create(CommandType commandType, Update update) {
        switch (commandType) {
            case START:
                return StartCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .firstName(update.getMessage().getFrom().getFirstName())
                        .userName(update.getMessage().getFrom().getUserName())
                        .build();
            case HISTORY:
                return ActionHistoryCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .build();
            case PRICE:
                return PriceCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .build();
            case BASKET:
                return BasketCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .build();
            case CHECKOUT:
                return CheckoutCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .userName(update.getMessage().getFrom().getUserName())
                        .build();
            case FIND_CHILD:
                return FindChildCommand.builder()
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .parentId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[1]))
                        .build();
            case GET_ITEM_INFO:
                return GetItemInfoCommand.builder()
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .itemId(update.getCallbackQuery().getData().split(DELIMITER)[1])
                        .build();
            case ADD_TO_CART:
                return AddItemCommand.builder()
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .itemId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[2]))
                        .build();
            case ADD_TO_CART_FOR_ENTRY:
                return AddItemCommandForEntry.builder()
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .timeslotId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[3]))
                        .date(update.getCallbackQuery().getData().split(DELIMITER)[1])
                        .time(update.getCallbackQuery().getData().split(DELIMITER)[2])
                        .build();
            case DELETE_ITEM:
                return DelItemCommand.builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .itemId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[1]))
                        .build();
            case CHOOSE_DATE:
                return ChooseDateCommand.builder()
                        .userId(update.getMessage().getFrom().getId())
                        .chatId(update.getMessage().getChatId().toString())
                        .build();
            case CHOOSE_TIME:
                return ChooseTimeCommand.builder()
                        .userId(update.getCallbackQuery().getFrom().getId())
                        .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .date(OffsetDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(update.getCallbackQuery().getData().split(DELIMITER)[1])), ZoneId.systemDefault()))
                        .parentId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[2]))
                        .build();
            default:
                return null;
        }
    }
}
