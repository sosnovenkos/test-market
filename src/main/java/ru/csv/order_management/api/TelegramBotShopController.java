package ru.csv.order_management.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.csv.order_management.domain.command.CommandFactory;
import ru.csv.order_management.domain.command.CommandType;
import ru.csv.order_management.service.OrderCommandService;


@Slf4j
@RestController
@AllArgsConstructor
public class TelegramBotShopController {
    private final OrderCommandService service;
    private final ObjectMapper objectMapper;

    @PostMapping("/1")
    public void onUpdateReceived(@RequestBody  Update update) {
        try {
            log.info("Received request:\n" + objectMapper.writeValueAsString(update));

            CommandType commandType = null;
            if (update.hasMessage() && update.getMessage().hasText()) {
                commandType = CommandType.from(update.getMessage().getText().toLowerCase());
            } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
                commandType = CommandType.from(update.getCallbackQuery().getData().split(":")[0]);
            }
            var command = CommandFactory.create(commandType, update);
            log.info("Handle: " + command.getClass().getSimpleName() + " with id " + command.getId());
            service.handle(command);
            log.info("Success: " + command.getClass().getSimpleName() + " with id " + command.getId());
        } catch (Exception e) {
            log.info("Всё сломалось");
            e.printStackTrace();
        }


}
}

