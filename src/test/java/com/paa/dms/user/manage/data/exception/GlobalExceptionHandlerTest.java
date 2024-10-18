package com.paa.dms.user.manage.data.exception;

import com.paa.dms.user.manage.data.constants.APIConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {
    @Mock
    private APIConstants apiConstants;
    @Mock
    private WebRequest webRequest;
    @Mock
    private MethodArgumentNotValidException validationException;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleValidationException() {
        // Arrange
        FieldError fieldError = new FieldError("userRequest", "email", " must not be empty");
        when(validationException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(webRequest.getDescription(false)).thenReturn("URI=/api/user");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationException(validationException, webRequest);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        assertEquals("email must not be empty", response.getBody().getMessage());
        assertEquals("URI=/api/user", response.getBody().getDetails());
    }

    @Test
    void testHandleResourceNotFoundException() {
        // Arrange
        when(apiConstants.getEXCEPTION_MSG_NO_DATA_FOUND()).thenReturn("No data found");
        when(webRequest.getDescription(false)).thenReturn("URI=/api/data");
        ResponseEntity<?> response = globalExceptionHandler.handleResourceNotFoundException(webRequest);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatusCode());
        assertEquals("No data found", errorResponse.getMessage());
        assertEquals("URI=/api/data", errorResponse.getDetails());
    }

    @Test
    void testHandleForbiddenException() {
        // Arrange
        when(apiConstants.getEXCEPTION_MSG_FORBIDDEN()).thenReturn("Action forbidden");
        when(webRequest.getDescription(false)).thenReturn("URI=/api/restricted");
        ResponseEntity<?> response = globalExceptionHandler.handleForbiddenException(webRequest);
        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.FORBIDDEN.value(), errorResponse.getStatusCode());
        assertEquals("Action forbidden", errorResponse.getMessage());
        assertEquals("URI=/api/restricted", errorResponse.getDetails());
    }

}