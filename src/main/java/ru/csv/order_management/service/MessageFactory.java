package ru.csv.order_management.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.csv.order_management.domain.context.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageFactory {
    private final List<String> PRODUCT_CODE_AS_BACK = List.of("14", "15", "16");

    @Value("${bot.admin.chat-ids}")
    private List<String> adminChatIds;

    @SneakyThrows
    public List<SendMessage> createMessages(Context context) {
        return context.handle(this);
    }

    public List<SendMessage> createMessages(AddItemCommandContext context) {
        var sendMessageBuilder = SendMessage.builder();
        sendMessageBuilder.chatId(context.command.chatId);
        if (context.order != null) {
            sendMessageBuilder.text("Товар добавлен в заказ");
        } else {
            sendMessageBuilder.text("Заказ не создан");
        }

        return List.of(sendMessageBuilder.build());
    }

    public List<SendMessage> createMessages(PriceCommandContext context) {
        log.info("Create Messages For Groups: ." + context.command.getClass().getSimpleName() + " - " + context.command.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите раздел");
        sendMessage.setChatId(context.command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        context.items.forEach(item -> {
            var inlineKeyboardButton = new InlineKeyboardButton(item.getName());
            var callbackData = "FIND_CHILD:" + item.getProductCode();
            inlineKeyboardButton.setCallbackData(callbackData);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
                }
        );
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .FIND_CHILD command: " + sendMessageList.size());
        return sendMessageList;
    }

    public List<SendMessage> createMessages(FindChildCommandContext context) {
        log.info("Create Messages For Items");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Нажимайте на позиции, чтобы ДОБАВИТЬ их в корзину");
        sendMessage.setChatId(context.command.chatId);
        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        context.items.forEach(item -> {
            InlineKeyboardButton inlineKeyboardButton;
            String callbackData;
            if (PRODUCT_CODE_AS_BACK.contains(item.getProductCode())) {
                inlineKeyboardButton = new InlineKeyboardButton(item.getName());
                callbackData = "price:";
            } else {
                inlineKeyboardButton = new InlineKeyboardButton(item.getName() + " " + item.getWeight() + " гр. " + item.getPrice() + " руб.");
                callbackData = "ADD_TO_CART:" + context.order.getId() + ":" + item.getId();
            }
            inlineKeyboardButton.setCallbackData(callbackData);
            inlineKeyboardButton.getSwitchInlineQuery();
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
                }
        );
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .ADD_TO_CART command: " + sendMessageList.size());
        return sendMessageList;
    }




    public List<SendMessage> createMessages(DelItemCommandContext context) {
        return List.of(new SendMessage(context.command.chatId, "Товар удалён из корзины"));
    }

    public List<SendMessage> createMessages(BasketCommandContext context) {
        log.info("Create message for Basket: " + context.command.userId);
        if (context.order != null && context.items != null && context.items.size() > 0) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Нажимайте на позиции вашей корзины, чтобы УДАЛИТЬ их");
            sendMessage.setChatId(context.command.chatId);
            List<SendMessage> sendMessageList = new ArrayList<>();
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            context.items.forEach(item -> {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                        item.getName() + " " + item.getWeight() + " гр, " + item.getPrice() + " руб.");
                var cbd = "DEL_ITEM:" + item.getId().toString();
                inlineKeyboardButton.setCallbackData(cbd);
                inlineKeyboardButton.getSwitchInlineQuery();
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                keyboardButtonsRow.add(inlineKeyboardButton);
                rowList.add(keyboardButtonsRow);
            });

            inlineKeyboardMarkup.setKeyboard(rowList);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            sendMessageList.add(sendMessage);
            log.info("Created Messages: .DEL_ITEM command: " + sendMessageList.size());

            SendMessage sendInfoMessage = new SendMessage();
            sendInfoMessage.setText("Сумма заказа: " + context.order.getAmount() + " руб. Вес заказа: " + context.order.getWeight() / 1000.0 + " кг.");
            sendInfoMessage.setChatId(context.command.chatId);
            sendMessageList.add(sendInfoMessage);

            return sendMessageList;
        } else {
            return List.of(new SendMessage(context.command.chatId, "Ваша корзина пуста"));
        }
    }

    public List<SendMessage> createMessages(StartCommandContext context) {
        SendMessage sendMessage = new SendMessage(context.command.chatId, context.command.firstName + ", привет! " +
                "Мы сами производим орехи и сухофрукты и продаем их по приятным для вас ценам. Жми на ПРАЙС.");
        KeyboardButton history = new KeyboardButton("История");
        KeyboardButton price = new KeyboardButton("Прайс");
        KeyboardButton basket = new KeyboardButton("Корзина");
        KeyboardButton checkout = new KeyboardButton("Оформить заказ");
        List<KeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(price);
        keyboardButtons1.add(basket);
        List<KeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(checkout);
        keyboardButtons2.add(history);
        KeyboardRow keyboardRow1 = new KeyboardRow(keyboardButtons1);
        KeyboardRow keyboardRow2 = new KeyboardRow(keyboardButtons2);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        var messages = new ArrayList<>(List.of(sendMessage));
        adminChatIds.forEach(id -> messages.add(new SendMessage(id, "Дана команда .start от @" + context.command.userName)));

        return messages;
    }

    public List<SendMessage> createMessages(CheckoutCommandContext context) {
        log.info("Create message for Ordering: " + context.command.getUserId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите ваши адреса доставки или создайте новый: ");
        sendMessage.setChatId(context.command.getChatId());

        List<SendMessage> sendMessageList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        context.addresses.forEach(address -> {
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(address.getPhone() + " : " + address.getDescription());
                    var cbd = "SET_ADDR:" + context.order.getId() + ":" + address.getId();
                    inlineKeyboardButton.setCallbackData(cbd);
                    inlineKeyboardButton.getSwitchInlineQuery();
                    List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                    keyboardButtonsRow.add(inlineKeyboardButton);
                    rowList.add(keyboardButtonsRow);
                }
        );

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(".Новый адрес");
        var cbd = "ADD_ADDR:";
        inlineKeyboardButton.setCallbackData(cbd);
        inlineKeyboardButton.getSwitchInlineQuery();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessageList.add(sendMessage);
        log.info("Created Messages: .SET_ADDR command: " + sendMessageList.size());

        adminChatIds.forEach(id -> sendMessageList.add(new SendMessage(id, "Пытался оформить заказ @" + context.command.userName)));
        return sendMessageList;
    }

    public List<SendMessage> createMessages(OrderHistoryCommandContext context) {
        var messages = new ArrayList<SendMessage>();
        if (context.orders == null || context.orders.isEmpty()) {
            messages.add(new SendMessage(context.command.getChatId(), "У вас пока нет истории заказов"));
        } else {
            var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            messages.add(new SendMessage(context.command.getChatId(), "Ваши заказы:"));
            context.orders.forEach(order -> messages.add(new SendMessage(context.command.getChatId(), ". Заказ " + order.getId() +
                    " от " + order.getCreatedAt().format(dateTimeFormatter) + " на " + order.getAmount() +
                    " руб, " + order.getWeight() / 1000.0 + " кг"))
            );
        }

        return messages;
    }

    public List<SendMessage> createMessages(AddAddressCommandContext context) {
        return List.of(new SendMessage(context.command.chatId, context.text));
    }

    public List<SendMessage> createMessages(WaitingForActionCommandContext context) {
        return List.of(new SendMessage(context.command.chatId, context.text));
    }

    public List<SendMessage> createMessages(SetAddressCommandContext context) {
        var messages = new ArrayList<>(List.of(new SendMessage(context.command.getChatId(), "Спасибо за заказ. " +
                "В ближайшее время мы свяжемся с вами для уточнения деталей доставки.")));
        adminChatIds.forEach(id -> {
            messages.add(new SendMessage(id, "Поступил заказ (" + context.order.getWeight() / 1000.0 +
                    " кг, " + context.order.getAmount() + " руб.):"));

            context.items.forEach(item -> messages.add(new SendMessage(id, ". " + item.getName() + " " + item.getWeight() + " гр. " + item.getPrice() + " руб.")));

            messages.add(new SendMessage(id, "Доставка по адресу: " + context.address.getDescription() +
                    ", звонить для согласования времени доставки: " + context.address.getPhone()));
                }
        );

        return messages;
    }

    public List<SendMessage> createMessages(GetItemInfoCommandContext context) {
        return List.of(SendMessage.builder()
                .chatId(context.command.chatId)
                .text(context.item.getName() + " " + context.item.getWeight() + " " + context.item.getPrice() + " руб.")
                .build());
    }
}
