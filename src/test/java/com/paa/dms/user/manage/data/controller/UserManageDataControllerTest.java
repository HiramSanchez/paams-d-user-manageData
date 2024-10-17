package com.paa.dms.user.manage.data.controller;
import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.model.RequestNewUserEntity;
import com.paa.dms.user.manage.data.service.UserManageDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserManageDataControllerTest {
    @InjectMocks
    private UserManageDataController userManageDataController;
    @Mock
    private UserManageDataService userManageDataServiceImpl;
    @Mock
    private APIConstants apiConstants;
    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("uid", "199705039");
    }

    @Test
    void testCreateUser() {
        // Arrange
        RequestNewUserEntity userRequest = new RequestNewUserEntity();
        Mockito.when(userManageDataServiceImpl.saveUser(Mockito.any(RequestNewUserEntity.class), Mockito.any(HttpHeaders.class)))
                .thenReturn(ResponseEntity.ok("User created"));

        ResponseEntity<String> response = userManageDataController.createUser(userRequest, httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).saveUser(userRequest, httpHeaders);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("User created", response.getBody());
    }

    @Test
    void testCreateUser2() {
        // Arrange
        RequestNewUserEntity userRequest = new RequestNewUserEntity();
        Mockito.when(userManageDataServiceImpl.saveUser(userRequest, httpHeaders)).thenReturn(ResponseEntity.ok("User created"));
        ResponseEntity<String> response = userManageDataController.createUser(userRequest, httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).saveUser(userRequest, httpHeaders);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("User created", response.getBody());
    }

}