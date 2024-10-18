package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.exception.custom.ForbiddenException;
import com.paa.dms.user.manage.data.exception.custom.NoDataFoundException;
import com.paa.dms.user.manage.data.model.*;
import com.paa.dms.user.manage.data.repository.UsersContactInfoRepository;
import com.paa.dms.user.manage.data.repository.UsersNameRepository;
import org.bson.types.ObjectId;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserManageDataServiceImplTest {

    @InjectMocks
    UserManageDataServiceImpl userManageDataService;
    @Mock
    private UsersContactInfoRepository usersContactInfoRepository;
    @Mock
    private UsersNameRepository usersNameRepository;

    private HttpHeaders httpHeaders;

    @BeforeEach
    void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("uid", "199705039");
        RequestDeleteUserEntity deleteUserRequest = new RequestDeleteUserEntity();
        deleteUserRequest.setEmail("user@email.com");
    }

    @Test
    void testSaveUser_Success() {
        // Arrange
        RequestNewUserEntity userRequest = new RequestNewUserEntity();
        userRequest.setEmail("test@email.com");
        userRequest.setPhone("1234567890");
        userRequest.setAddress1("123 Street");
        userRequest.setAddress2("Apt 1");
        userRequest.setCity("City");
        userRequest.setState("State");
        userRequest.setZipCode("00000");
        userRequest.setName("John");
        userRequest.setMiddleName("M.");
        userRequest.setLastName("Doe");
        // Act
        ResponseEntity<String> response = userManageDataService.saveUser(userRequest, httpHeaders);
        // Assert
        verify(usersContactInfoRepository, times(1)).save(any(MongoUsersContactInfoEntity.class));
        verify(usersNameRepository, times(1)).save(any(MongoUsersNameEntity.class));
        assertEquals(OK, response.getStatusCode());
        assertEquals("User Created", response.getBody());
    }
    @Test
    void testSaveUser_MissingUidHeader() {
        // Arrange
        RequestNewUserEntity userRequest = new RequestNewUserEntity();
        HttpHeaders headersWithoutUid = new HttpHeaders();
        // Act
        Exception exception = assertThrows(NullPointerException.class, () -> {userManageDataService.saveUser(userRequest, headersWithoutUid);});
        //Assert
        assertTrue(exception.getMessage().contains("Cannot invoke"));
    }


    @Test
    void testFindUserData_Success() {
        // Arrange
        ObjectId id = new ObjectId();
        MongoUsersContactInfoEntity userInfo = new MongoUsersContactInfoEntity();
        userInfo.set_id(id);
        userInfo.setEmail("email@test.com");
        userInfo.setUid("199705039");
        userInfo.setPhone("3230000101");
        userInfo.setAddress1("Street 1");
        userInfo.setAddress2("Street 2");
        userInfo.setCity("City");
        userInfo.setState("State");
        userInfo.setZipCode("45016");
        MongoUsersNameEntity userName = new MongoUsersNameEntity();
        userName.set_id(id);
        userName.setUid("199705039");
        userName.setFirstName("John");
        userName.setMiddleName("M");
        userName.setLastName("Doe");
        // Mock
        Mockito.when(usersContactInfoRepository.findUserInfoByUid("199705039")).thenReturn(Optional.of(userInfo));
        Mockito.when(usersNameRepository.findUserNameByUid("199705039")).thenReturn(Optional.of(userName));
        // Act
        ResponseEntity<ResponseUserDataEntity> responseEntity = userManageDataService.findUserData(httpHeaders);
        // Assert
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        ResponseUserDataEntity responseBody = responseEntity.getBody();
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals("email@test.com", responseBody.getEmail());
        Assertions.assertEquals("John", responseBody.getName());
        // Verify
        Mockito.verify(usersContactInfoRepository).findUserInfoByUid("199705039");
        Mockito.verify(usersNameRepository).findUserNameByUid("199705039");
    }
    @Test
    void testFindUserData_NoDataFoundException() {
        // Arrange
        Mockito.when(usersContactInfoRepository.findUserInfoByUid("199705039")).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NoDataFoundException.class, () ->
                userManageDataService.findUserData(httpHeaders));
    }



    @Test
    void testDeleteUser_Success() {
        // Arrange
        RequestDeleteUserEntity deleteUserRequest = new RequestDeleteUserEntity();
        deleteUserRequest.setEmail("user@email.com");
        ObjectId mongoId = new ObjectId();
        MongoUsersContactInfoEntity contactInfo = new MongoUsersContactInfoEntity();
        contactInfo.setUid("199705039");
        contactInfo.setEmail("user@email.com");
        contactInfo.set_id(mongoId);
        MongoUsersNameEntity nameInfo = new MongoUsersNameEntity();
        nameInfo.setUid("199705039");
        nameInfo.set_id(mongoId);
        when(userManageDataService.findUsersInfoByUid("199705039")).thenReturn(Optional.of(contactInfo));
        when(userManageDataService.findUserNameByUid("199705039")).thenReturn(Optional.of(nameInfo));
        // Act
        ResponseEntity<String> response = userManageDataService.deleteUser(deleteUserRequest, httpHeaders);
        // Assert
        verify(usersContactInfoRepository, times(1)).deleteById(contactInfo.get_id().toString());
        verify(usersNameRepository, times(1)).deleteById(nameInfo.get_id().toString());
        assertEquals(OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    void testDeleteUser_NoDataFoundException() {
        // Arrange
        RequestDeleteUserEntity deleteUserRequest = new RequestDeleteUserEntity();
        deleteUserRequest.setEmail("user@email.com");
        when(userManageDataService.findUsersInfoByUid("199705039")).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> {
            userManageDataService.deleteUser(deleteUserRequest, httpHeaders);});
        // Verify
        verify(usersContactInfoRepository, never()).deleteById(anyString());
        verify(usersNameRepository, never()).deleteById(anyString());
    }

    @Test
    void testDeleteUser_ForbiddenException() {
        // Arrange
        RequestDeleteUserEntity deleteUserRequest = new RequestDeleteUserEntity();
        deleteUserRequest.setEmail("user@email.com");
        ObjectId mongoId = new ObjectId();
        MongoUsersContactInfoEntity contactInfo = new MongoUsersContactInfoEntity();
        contactInfo.setUid("199705039");
        contactInfo.setEmail("another@email.com");
        contactInfo.set_id(mongoId);
        MongoUsersNameEntity nameInfo = new MongoUsersNameEntity();
        nameInfo.setUid("199705039");
        nameInfo.set_id(mongoId);
        when(userManageDataService.findUsersInfoByUid("199705039")).thenReturn(Optional.of(contactInfo));
        when(userManageDataService.findUserNameByUid("199705039")).thenReturn(Optional.of(nameInfo));
        // Act & Assert
        assertThrows(ForbiddenException.class, () -> {
            userManageDataService.deleteUser(deleteUserRequest, httpHeaders);});
        // Verify
        verify(usersContactInfoRepository, never()).deleteById(anyString());
        verify(usersNameRepository, never()).deleteById(anyString());
    }


}
