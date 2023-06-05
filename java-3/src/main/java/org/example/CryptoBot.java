package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CryptoBot extends TelegramLongPollingBot {
    public CryptoBot() {
        super("6142678927:AAFwmITPeJ3ect0NLh6AKGWgQr6uu4VzTpw");
    }

    private double getPriceDouble(String type) {
        CryptoPrice price = null;
        var amount = 0.0;
        try {
            price = CryptoPrice.spotPrice(type);
            amount = price.getAmount().doubleValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return amount;
    }

    private String getMessagePriceFromCoinType(String type) {
        return type + " price is: " + getPriceDouble(type);
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();
        var textUpperCase = text.toUpperCase();

        try {
            if (text.equals("/start")) {
                var toSend = "Hello, you can use /all or btc, eth, doge commands";
                sendTextMessage(chatId, toSend);
            } else if (text.equals("/all")) {
                // The case to show all the supported coin prices
                for (var crypto : Cryptos.values()) {
                    var toSend = getMessagePriceFromCoinType(crypto.toString()) + "\n";
                    sendMessagePhotoCaption(chatId, crypto + ".png", toSend);
                }
            } else if (text.split(" ").length > 1) {
                // Check the cryptocurrency amount for dollars from (BTC 100) format
                var separated = textUpperCase.split(" ");
                var crypto = separated[0].toLowerCase();
                var dollars = Double.parseDouble(separated[1]);
                var price = getPriceDouble(crypto);
                var cryptoForDollars = dollars / price;
                var toSend = "For " + dollars + " dollars you can buy " + cryptoForDollars + " " + crypto;
                sendMessagePhotoCaption(chatId, crypto.toUpperCase() + ".png", toSend);
            } else if (text.split(",").length > 1) {
                // The case if user enter coin types in one line that separated with comma
                var separated = textUpperCase.split(",");
                for (var crypto : separated) {
                    var toSend = getMessagePriceFromCoinType(crypto) + "\n";
                    sendMessagePhotoCaption(chatId, crypto + ".png", toSend);
                }
            } else {
                // Checking when user enter the definite coin name
                for (var crypto : Cryptos.values()) {
                    var cryptoString = crypto.toString();
                    if (cryptoString.equals(textUpperCase)) {
                        var toSend = getMessagePriceFromCoinType(cryptoString);
                        sendMessagePhotoCaption(chatId, cryptoString + ".png", toSend);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Chat is updated");
        }
    }

    private void sendMessagePhotoCaption(Long chatId, String name, String caption) throws TelegramApiException {
        var photo = getClass().getClassLoader().getResourceAsStream(name);
        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setCaption(caption != null ? caption : "");
        message.setPhoto(new InputFile(photo, name));
        execute(message);
    }

    private void sendTextMessage(Long chatId, String text) throws TelegramApiException {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }

    @Override
    public String getBotUsername() {
        return "crypto_currency_java_course_bot";
    }
}
