package com.paa.dms.user.manage.data.config;

import com.paa.dms.user.manage.data.constants.APIConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;
@ExtendWith(MockitoExtension.class)
class StartUpConfigTest {
    @Mock
    private APIConstants apiConstants;
    @InjectMocks
    private StartUpConfig startUpConfig;

    @Test
    void testOnApplicationEvent() {
        Mockito.when(apiConstants.getSERVICE_NAME()).thenReturn("TestService");

        ApplicationStartedEvent event = Mockito.mock(ApplicationStartedEvent.class);
        startUpConfig.onApplicationEvent(event);

        Mockito.verify(apiConstants).getSERVICE_NAME();
        Mockito.verifyNoMoreInteractions(apiConstants);
    }

}