package ru.lai.hw2_rest.utils;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JsonUtilTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testParseJsonRequest() throws IOException {
        String jsonContent = "{\"name\":\"John\",\"age\":30}";
        BufferedReader reader = new BufferedReader(new StringReader(jsonContent));

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getReader()).thenReturn(reader);

        TestUser user = JsonUtil.parseJsonRequest(request, TestUser.class);
        assertEquals("John", user.getName());
        assertEquals(30, user.getAge());
    }

    @Test
    void testWriteJsonResponseWithObject() throws IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        TestUser user = new TestUser("Jane", 25);

        JsonUtil.writeJsonResponse(response, user);
        writer.flush();

        String expectedJson = objectMapper.writeValueAsString(user);
        assertEquals(expectedJson, stringWriter.toString());

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    @Test
    void testWriteJsonResponseWithMessage() throws IOException {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        JsonUtil.writeJsonResponse(response, "status", "success");
        writer.flush();

        String expectedJson = "{\"status\": \"success\"}";
        assertEquals(expectedJson, stringWriter.toString());

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    static class TestUser {
        private String name;
        private int age;

        public TestUser() {}

        public TestUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public int getAge() { return age; }

        public void setAge(int age) { this.age = age; }
    }
}
