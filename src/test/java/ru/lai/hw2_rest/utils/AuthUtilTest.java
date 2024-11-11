package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class AuthUtilTest {

    private static final String VALID_AUTH_KEY = "my_auth_key";

    @Test
    void testIsAuthorized_withValidAuthKey() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(VALID_AUTH_KEY);
        assertTrue(AuthUtil.isAuthorized(request));
    }

    @Test
    void testIsAuthorized_withInvalidAuthKey() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("invalid_key");
        assertFalse(AuthUtil.isAuthorized(request));
    }

    @Test
    void testIsAuthorized_withNoAuthKey() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);
        assertFalse(AuthUtil.isAuthorized(request));
    }
}