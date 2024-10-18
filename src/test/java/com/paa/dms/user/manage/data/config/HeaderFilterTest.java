package com.paa.dms.user.manage.data.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

class HeaderFilterTest {
    private HeaderFilter headerFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    @BeforeEach
    void setUp() {
        headerFilter = new HeaderFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void testDoFilter_MissingUidHeader() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("uid")).thenReturn(null);
        when(request.getServletPath()).thenReturn("/test-path");
        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        // Act
        headerFilter.doFilter(request, response, chain);
        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).setContentType("application/json");
        String responseBody = responseWriter.toString();
        assertTrue(responseBody.contains("\"statusCode\":400"));
        assertTrue(responseBody.contains("\"message\":\"Missing uid header\""));
        assertTrue(responseBody.contains("\"details\": \"uri="));
    }

    @Test
    void testDoFilter_EmptyUidHeader() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("uid")).thenReturn("");
        when(request.getServletPath()).thenReturn("/test-path");
        StringWriter responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        // Act
        headerFilter.doFilter(request, response, chain);
        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).setContentType("application/json");
        String responseBody = responseWriter.toString();
        assertTrue(responseBody.contains("\"statusCode\":400"));
        assertTrue(responseBody.contains("\"message\":\"Missing uid header\""));
        assertTrue(responseBody.contains("\"details\": \"uri="));
    }

    @Test
    void testDoFilter_ValidUidHeader() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("uid")).thenReturn("12345");
        // Act
        headerFilter.doFilter(request, response, chain);
        // Assert
        verify(chain).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


}