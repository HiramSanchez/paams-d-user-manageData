package com.paa.dms.user.manage.data.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    private ErrorResponse errorResponse;
    private Date timestamp;
    private int statusCode;
    private String message;
    private String details;

    @BeforeEach
    void setup() {
        timestamp = new Date();
        statusCode = 404;
        message = "Resource not found";
        details = "The requested resource could not be found on the server.";
        errorResponse = new ErrorResponse(statusCode, message, details);
    }

    @Test
    void testConstructorInitialization() {
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(statusCode, errorResponse.getStatusCode());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(details, errorResponse.getDetails());
    }

    @Test
    void testTimestampIsCloseToCurrentTime() {
        long timeDifference = Math.abs(errorResponse.getTimestamp().getTime() - timestamp.getTime());
        assertTrue(timeDifference < 1000, "Timestamp should be close to the current time");
    }

    @Test
    void testMessageSetterBehavior() {
        String newMessage = "New error message";
        errorResponse = new ErrorResponse(statusCode, newMessage, details);
        assertEquals(newMessage, errorResponse.getMessage());
    }

}