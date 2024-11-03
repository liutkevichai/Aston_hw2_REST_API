package ru.lai.hw2_rest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }


    public static <T> T parseJsonRequest(HttpServletRequest req, Class<T> clazz) throws IOException {
        return objectMapper.readValue(req.getReader(), clazz);
    }

    public static void writeJsonResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(resp.getWriter(), data);
    }

    public static void writeJsonResponse(HttpServletResponse resp, String key, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String json = String.format("{\"%s\": \"%s\"}", key, message);
        resp.getWriter().write(json);
    }
}