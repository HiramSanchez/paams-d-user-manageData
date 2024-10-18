package com.paa.dms.user.manage.data.exception.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {
    @Test
    void testBadRequestException() {
        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> {throw new BadRequestException();});
        // Assert
        assertNotNull(exception);
        assertEquals(BadRequestException.class, exception.getClass());
    }

}