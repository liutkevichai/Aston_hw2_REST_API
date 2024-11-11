package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class RequestParserTest {

    @Test
    void testGetParameterMapSuccess() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String requestBody = "param1=value1&param2=value2";

        when(request.getInputStream()).thenReturn(new jakarta.servlet.ServletInputStream() {
            private final StringReader stringReader = new StringReader(requestBody);
            @Override
            public int read() throws IOException {
                return stringReader.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return true;
            }
            @Override
            public void setReadListener(jakarta.servlet.ReadListener readListener) {}
        });

        Map<String, String> result = RequestParser.getParameterMap(request);

        assertEquals(2, result.size());
        assertEquals("value1", result.get("param1"));
        assertEquals("value2", result.get("param2"));
    }

    @Test
    void testGetParameterMapIOException() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getInputStream()).thenThrow(new IOException("Test IOException"));

        Map<String, String> result = RequestParser.getParameterMap(request);
        assertNull(result);
    }
}
