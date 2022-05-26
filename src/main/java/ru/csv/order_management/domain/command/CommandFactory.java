package ru.csv.order_management.domain.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandFactory {
    private static final String DELIMITER = ":";

    public static Command create(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (CommandType.from(update.getMessage().getText().toLowerCase())) {
                case START:
                    return StartCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .messageId(update.getMessage().getMessageId())
                            .firstName(update.getMessage().getFrom().getFirstName())
                            .userName(update.getMessage().getFrom().getUserName())
                            .build();
                case HISTORY:
                    return OrderHistoryCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .messageId(update.getMessage().getMessageId())
                            .build();
                case PRICE:
                    return PriceCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .messageId(update.getMessage().getMessageId())
                            .build();
                case BASKET:
                    return BasketCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .messageId(update.getMessage().getMessageId())
                            .build();
                case CHECKOUT:
                    return CheckoutCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .userName(update.getMessage().getFrom().getUserName())
                            .messageId(update.getMessage().getMessageId())
                            .build();
                default:
                    return WaitingForActionCommand.builder()
                            .userId(update.getMessage().getFrom().getId())
                            .chatId(update.getMessage().getChatId().toString())
                            .messageId(update.getMessage().getMessageId())
                            .text(update.getMessage().getText())
                            .build();
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
            switch (CommandType.from(update.getCallbackQuery().getData().split(":")[0])) {
                case FIND_CHILD:
                    return FindChildCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .parentId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[1]))
                            .build();
                case GET_ITEM_INFO:
                    return GetItemInfoCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .itemId(update.getCallbackQuery().getData().split(DELIMITER)[1])
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .build();
                case ADD_TO_CART:
                    return AddItemCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .itemId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[2]))
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .build();
                case DELETE_ITEM:
                    return DelItemCommand.builder()
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .itemId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[1]))
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .build();
                case PRICE:
                    return PriceCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .build();
                case ADD_ADDRESS:
                    return AddAddressCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .build();
                case SET_ADDRESS:
                    return SetAddressCommand.builder()
                            .userId(update.getCallbackQuery().getFrom().getId())
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
//                            .messageId(update.getCallbackQuery().getMessage().getMessageId())
                            .orderId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[1]))
                            .addressId(Long.valueOf(update.getCallbackQuery().getData().split(DELIMITER)[2]))
                            .build();
            }
        }
        return null;
    }
}
