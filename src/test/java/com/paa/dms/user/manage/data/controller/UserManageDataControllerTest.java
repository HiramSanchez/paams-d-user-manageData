package com.paa.dms.user.manage.data.controller;
import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.model.RequestDeleteUserEntity;
import com.paa.dms.user.manage.data.model.RequestNewUserEntity;
import com.paa.dms.user.manage.data.model.RequestUpdateUserEntity;
import com.paa.dms.user.manage.data.model.ResponseUserDataEntity;
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
import org.springframework.http.HttpStatus;
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
        Mockito.when(userManageDataServiceImpl.saveUser(userRequest, httpHeaders)).thenReturn(ResponseEntity.ok("User created"));
        ResponseEntity<String> response = userManageDataController.createUser(userRequest, httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).saveUser(userRequest, httpHeaders);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals("User created", response.getBody());
    }

    @Test
    void testReadUser() {
        // Arrange
        ResponseUserDataEntity responseUserData = new ResponseUserDataEntity();
        responseUserData.setName("John");
        Mockito.when(userManageDataServiceImpl.findUserData(httpHeaders)).thenReturn(new ResponseEntity<>(responseUserData, HttpStatus.OK));
        ResponseEntity<ResponseUserDataEntity> response = userManageDataController.getUser(httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).findUserData(httpHeaders);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals("John", response.getBody().getName());
    }


    @Test
    void testUpdateUser() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        Mockito.when(userManageDataServiceImpl.updateUser(userRequest, httpHeaders)).thenReturn(ResponseEntity.ok("User Updated"));
        ResponseEntity<String> response = userManageDataController.updateUser(userRequest, httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).updateUser(userRequest, httpHeaders);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals("User Updated", response.getBody());
    }

    @Test
    void testDeleteUser() {
        // Arrange
        RequestDeleteUserEntity userRequest = new RequestDeleteUserEntity();
        Mockito.when(userManageDataServiceImpl.deleteUser(userRequest, httpHeaders)).thenReturn(ResponseEntity.ok("User Deleted"));
        ResponseEntity<String> response = userManageDataController.deleteUser(userRequest, httpHeaders);

        // Assert
        Mockito.verify(userManageDataServiceImpl, Mockito.times(1)).deleteUser(userRequest, httpHeaders);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals("User Deleted", response.getBody());
    }

}