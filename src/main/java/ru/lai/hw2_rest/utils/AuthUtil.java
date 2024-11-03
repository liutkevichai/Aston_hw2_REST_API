package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthUtil {
    private static final String AUTH_KEY = "my_auth_key"; // Заменить на реальный ключ (переменную среды)

    public static boolean isAuthorized(HttpServletRequest req) {
        String authKey = req.getHeader("Authorization");
        return AUTH_KEY.equals(authKey);
    }
}