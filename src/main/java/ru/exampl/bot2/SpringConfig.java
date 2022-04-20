package ru.exampl.bot2;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import javax.websocket.MessageHandler;

@Configuration
@AllArgsConstructor
public class SpringConfig {

    @Bean
    public SetWebhook setWebhookInstance(){
        return SetWebhook.builder().build();
    }
    @Bean
    public WriteReadBot springWebhookBot(
            SetWebhook setWebhook,
            MessageHandler messageHandler){

    }

}
