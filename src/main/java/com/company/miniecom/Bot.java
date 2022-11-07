package com.company.miniecom;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${my.chat-id}")
    private String chatId;


    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage();

            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Hi");

            System.out.println("update.getMessage().getChatId() = " + update.getMessage().getChatId());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String ipAddress, String action) {
        try {
            SendMessage sendMessage = new SendMessage();

            sendMessage.setChatId(chatId);

            String s = "";
            if (ipAddress != null) s = "ip: " + ipAddress;
            sendMessage.setText(s + "\naction: " + action);

            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}