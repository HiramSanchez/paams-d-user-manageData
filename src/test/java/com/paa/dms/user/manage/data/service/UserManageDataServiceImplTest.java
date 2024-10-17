package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.model.MongoUsersContactInfoEntity;
import com.paa.dms.user.manage.data.model.RequestNewUserEntity;
import com.paa.dms.user.manage.data.repository.UsersContactInfoRepository;
import com.paa.dms.user.manage.data.repository.UsersNameRepository;
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
public class UserManageDataServiceImplTest {

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
    void checkDataZipCode() {

        RequestNewUserEntity userRequest = new RequestNewUserEntity();
        //Mockito.when(usersContactInfoRepository.save(Mockito.any(MongoUsersContactInfoEntity.class))).thenReturn(ResponseEntity.ok("User created"));
        Assertions.assertNotNull(userManageDataService.saveUser(userRequest, httpHeaders));
    }


}
