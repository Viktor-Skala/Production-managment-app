package com.example.factory.controller;

import com.example.factory.dto.MessageDto;
import com.example.factory.georetriver.GeoRetriever;
import com.example.factory.georetriver.Location;
import com.example.factory.telegrambot.CustomBot;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Controller
public class WelcomeController {

    private GeoRetriever geoRetriever;
    private CustomBot customBot;

    public WelcomeController(GeoRetriever geoRetriever, CustomBot customBot) {
        this.geoRetriever = geoRetriever;
        this.customBot = customBot;
    }

    @GetMapping("/")
    public void welcome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/welcome.html");
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto, HttpServletRequest request) {
        Location location = geoRetriever.getLocation(request.getRemoteAddr());
        try {
            customBot.sendMessage("User from " + location.toString() + " send message:\n" + messageDto.getMessage());
            return ResponseEntity.ok().build();
        } catch (TelegramApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
