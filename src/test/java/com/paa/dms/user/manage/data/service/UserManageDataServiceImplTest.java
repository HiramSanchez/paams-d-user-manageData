package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.exception.custom.BadRequestException;
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
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.*;

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
    void testUpdateUser_ShouldUpdateContactInfo_WhenContactInfoProvided() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        MongoUsersContactInfoEntity contactInfo = new MongoUsersContactInfoEntity();
        userRequest.setEmail("test@example.com");
        userRequest.setPhone("1234567890");
        when(usersContactInfoRepository.findUserInfoByUid(anyString())).thenReturn(Optional.of(contactInfo));
        // Act
        ResponseEntity<String> response = userManageDataService.updateUser(userRequest, httpHeaders);
        // Assert
        assertEquals(ResponseEntity.ok("User Updated"), response);
        assertEquals("test@example.com", contactInfo.getEmail());
        assertEquals("1234567890", contactInfo.getPhone());
        verify(usersContactInfoRepository).save(contactInfo);
    }
    @Test
    void testUpdateUser_ShouldUpdateNameInfo_WhenNameInfoProvided() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        MongoUsersNameEntity nameInfo = new MongoUsersNameEntity();
        userRequest.setName("John");
        userRequest.setMiddleName("A.");
        userRequest.setLastName("Doe");
        when(usersNameRepository.findUserNameByUid(anyString())).thenReturn(Optional.of(nameInfo));
        // Act
        ResponseEntity<String> response = userManageDataService.updateUser(userRequest, httpHeaders);
        // Assert
        assertEquals(ResponseEntity.ok("User Updated"), response);
        assertEquals("John", nameInfo.getFirstName());
        assertEquals("A.", nameInfo.getMiddleName());
        assertEquals("Doe", nameInfo.getLastName());
        verify(usersNameRepository).save(nameInfo);
    }
    @Test
    void testUpdateUser_ShouldThrowBadRequestException_WhenNoDataProvided() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        // Act & Assert
        assertThrows(BadRequestException.class, () -> userManageDataService.updateUser(userRequest, httpHeaders));
    }
    @Test
    void testTryUpdateContactInfo_ShouldReturnFalse_WhenNoContactInfo() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        MongoUsersContactInfoEntity contactInfo = new MongoUsersContactInfoEntity();
        lenient().when(usersContactInfoRepository.findUserInfoByUid("199705039")).thenReturn(Optional.of(contactInfo));
        // Act
        boolean result = userManageDataService.tryUpdateContactInfo(userRequest, "199705039");
        // Assert
        assertFalse(result);
        verify(usersContactInfoRepository, never()).save(any());
    }

    @Test
    void testTryUpdateNameInfo_ShouldReturnFalse_WhenNoNameInfo() {
        // Arrange
        MongoUsersNameEntity nameInfo = new MongoUsersNameEntity();
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        lenient().when(usersNameRepository.findUserNameByUid("199705039")).thenReturn(Optional.of(nameInfo));
        // Act
        boolean result = userManageDataService.tryUpdateNameInfo(userRequest, "199705039");
        // Assert
        assertFalse(result);
        verify(usersNameRepository, never()).save(any());
    }
    @Test
    void testHasContactInfoToUpdate_ShouldReturnTrue_WhenAtLeastOneFieldIsNotEmpty() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        userRequest.setEmail("test@example.com");
        // Act
        boolean result = userManageDataService.hasContactInfoToUpdate(userRequest);
        // Assert
        assertTrue(result);
    }
    @Test
    void testHasContactInfoToUpdate_ShouldReturnFalse_WhenAllFieldsAreEmpty() {
        // Arrange
        RequestUpdateUserEntity userRequest = new RequestUpdateUserEntity();
        // Act
        boolean result = userManageDataService.hasContactInfoToUpdate(userRequest);
        // Assert
        assertFalse(result);
    }
    @Test
    void testUpdateIfPresent_ShouldCallSetter_WhenNewValueIsNotEmpty() {
        // Arrange
        Consumer<String> setter = mock(Consumer.class);
        String newValue = "newValue";
        // Act
        userManageDataService.updateIfPresent(newValue, setter);
        // Assert
        verify(setter).accept(newValue);
    }

    @Test
    void testUpdateIfPresent_ShouldNotCallSetter_WhenNewValueIsEmpty() {
        // Arrange
        Consumer<String> setter = mock(Consumer.class);
        String newValue = "";
        // Act
        userManageDataService.updateIfPresent(newValue, setter);
        // Assert
        verify(setter, never()).accept(anyString());
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
