package com.paa.dms.user.manage.data.exception.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenExceptionTest {
    @Test
    void testForbiddenException() {
        // Act
        Exception exception = assertThrows(ForbiddenException.class, () -> {throw new ForbiddenException();});

        // Assert
        assertNotNull(exception);
        assertEquals(ForbiddenException.class, exception.getClass());
    }

}