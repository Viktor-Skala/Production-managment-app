package com.example.factory.security;

import com.example.factory.georetriver.GeoRetriever;
import com.example.factory.georetriver.Location;
import com.example.factory.jwt.JwtUtils;
import com.example.factory.model.Employee;
import com.example.factory.service.EmployeeService;
import com.example.factory.service.RoleService;
import com.example.factory.telegrambot.CustomBot;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    private EmployeeService employeeService;
    private RoleService roleService;
    private JwtUtils jwtUtils;
    private GeoRetriever geoRetriever;
    private CustomBot telegramBot;


    public AuthHandler(EmployeeService employeeService, RoleService roleService, JwtUtils jwtUtils, GeoRetriever geoRetriever, CustomBot telegramBot) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.jwtUtils = jwtUtils;
        this.geoRetriever = geoRetriever;
        this.telegramBot = telegramBot;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User auth2User = auth2Token.getPrincipal();

        Map<String, Object> attributes = auth2User.getAttributes();

        Employee authEmployee = employeeService.findByEmail((String) attributes.get("email"));
        if (Objects.isNull(authEmployee)) {
            Employee employee = Employee.builder()
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("given_name"))
                    .lastName((String) attributes.get("family_name"))
                    .roles(roleService.getAll())
                    .build();
            authEmployee = employeeService.create(employee);
            Location location = geoRetriever.getLocation(request.getRemoteAddr());
            try {
                telegramBot.sendMessage("User " + authEmployee.getEmail() + " registered from " + location.toString());
            } catch (TelegramApiException e) {
                System.out.println("Telegram bot send message exception " + e.getMessage());
            }
        }
        String token = jwtUtils.generateJwtToken(authEmployee.getEmail());
        response.sendRedirect("/oauth2.html?token=" + token);
        Location location = geoRetriever.getLocation(request.getRemoteAddr());
        try {
            telegramBot.sendMessage("User " + authEmployee.getEmail() + " loged in from " + location.toString());
        } catch (TelegramApiException e) {
            System.out.println("Telegram bot send message exception " + e.getMessage());
        }
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////        OAuth2AuthenticationToken auth2Token = (OAuth2AuthenticationToken) authentication;
////        OAuth2User auth2User = auth2Token.getPrincipal();
////
////        Map<String, Object> attributes = auth2User.getAttributes();
////
////        Employee authEmployee = employeeService.findByEmail((String) attributes.get("email"));
////        if (Objects.isNull(authEmployee)) {
////            Employee employee = Employee.builder()
////                    .email((String) attributes.get("email"))
////                    .name((String) attributes.get("given_name"))
////                    .lastName((String) attributes.get("family_name"))
////                    .roles(roleService.getAll())
////                    .build();
////            authEmployee = employeeService.create(employee);
////            Location location = geoRetriever.getLocation(request.getRemoteAddr());
////            try {
////                telegramBot.sendMessage("User " + authEmployee.getEmail() + " registered from " + location.toString());
////            } catch (TelegramApiException e) {
////                System.out.println("Telegram bot send message exception " + e.getMessage());
////            }
////        }
////        String token = jwtUtils.generateJwtToken(authEmployee.getEmail());
//        response.sendRedirect("/oauth2.html");
////        Location location = geoRetriever.getLocation(request.getRemoteAddr());
////        try {
////            telegramBot.sendMessage("User " + authEmployee.getEmail() + " loged in from " + location.toString());
////        } catch (TelegramApiException e) {
////            System.out.println("Telegram bot send message exception " + e.getMessage());
////        }
//    }
}
