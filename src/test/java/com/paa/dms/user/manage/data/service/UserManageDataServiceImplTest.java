package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.exception.custom.NoDataFoundException;
import com.paa.dms.user.manage.data.model.MongoUsersContactInfoEntity;
import com.paa.dms.user.manage.data.model.MongoUsersNameEntity;
import com.paa.dms.user.manage.data.model.ResponseUserDataEntity;
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
    }

@Test
void testFindUserData_Success() {
    // Arrange: Mock user info and name data
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

    // Mock repository behavior
    Mockito.when(usersContactInfoRepository.findUserInfoByUid("199705039")).thenReturn(Optional.of(userInfo));
    Mockito.when(usersNameRepository.findUserNameByUid("199705039")).thenReturn(Optional.of(userName));

    // Act: Call the service method with the mocked headers
    ResponseEntity<ResponseUserDataEntity> responseEntity = userManageDataService.findUserData(httpHeaders);

    // Assert: Verify the response contents
    Assertions.assertNotNull(responseEntity);
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());

    ResponseUserDataEntity responseBody = responseEntity.getBody();
    Assertions.assertNotNull(responseBody);
    Assertions.assertEquals("email@test.com", responseBody.getEmail());
    Assertions.assertEquals("John", responseBody.getName());

    // Verify that repository methods were called
    Mockito.verify(usersContactInfoRepository).findUserInfoByUid("199705039");
    Mockito.verify(usersNameRepository).findUserNameByUid("199705039");
}

    @Test
    void testFindUserData_NoDataFoundException() {
        // Arrange: Simulate empty Optional for user info
        Mockito.when(usersContactInfoRepository.findUserInfoByUid("199705039")).thenReturn(Optional.empty());

        // Act & Assert: Verify that NoDataFoundException is thrown
        Assertions.assertThrows(NoDataFoundException.class, () ->
                userManageDataService.findUserData(httpHeaders));
    }

}
