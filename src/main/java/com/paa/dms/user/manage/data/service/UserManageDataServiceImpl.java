package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.exception.custom.BadRequestException;
import com.paa.dms.user.manage.data.exception.custom.ForbiddenException;
import com.paa.dms.user.manage.data.exception.custom.NoDataFoundException;
import com.paa.dms.user.manage.data.model.*;
import com.paa.dms.user.manage.data.repository.UsersContactInfoRepository;
import com.paa.dms.user.manage.data.repository.UsersNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;
import java.util.function.Supplier;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserManageDataServiceImpl implements UserManageDataService {

    @Autowired
    private UsersContactInfoRepository usersContactInfoRepository;

    @Autowired
    private UsersNameRepository usersNameRepository;

    /**
     * Create User Service
     *
     * Saves a new user in the database using the provided user request data and HTTP headers.
     * @param userRequest the details of the new user
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */
    public ResponseEntity<String> saveUser(RequestNewUserEntity userRequest, HttpHeaders httpHeaders) {

        log.debug("REQUEST >>> " + userRequest.toString());
        String uid = httpHeaders.getFirst("uid").toString();

        MongoUsersContactInfoEntity userInfo = new MongoUsersContactInfoEntity();
        userInfo.setUid(uid);
        userInfo.setEmail(userRequest.getEmail());
        userInfo.setPhone(userRequest.getPhone());
        userInfo.setAddress1(userRequest.getAddress1());
        userInfo.setAddress2(userRequest.getAddress2());
        userInfo.setCity(userRequest.getCity());
        userInfo.setState(userRequest.getState());
        userInfo.setZipCode(userRequest.getZipCode());
        MongoUsersNameEntity userName = new MongoUsersNameEntity();
        userName.setUid(uid);
        userName.setFirstName(userRequest.getName());
        userName.setMiddleName(userRequest.getMiddleName());
        userName.setLastName(userRequest.getLastName());

        usersContactInfoRepository.save(userInfo);
        usersNameRepository.save(userName);
        ResponseEntity response = ResponseEntity.ok("User Created");
        log.debug("RESPONSE >>> User Created");
        return response;
    }

    /**
     * Read Data Service
     *
     * Retrieves user data based on the provided UID from the HTTP headers.
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity containing the user's data
     */
    public ResponseEntity<ResponseUserDataEntity> findUserData(HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid").toString();
        log.debug("REQUEST >>> " + uid + " data requested");
        var userInfo = findUsersInfoByUid(uid).orElseThrow(() -> new NoDataFoundException());
        var userName = findUserNameByUid(uid).orElseThrow(() -> new NoDataFoundException());

            ResponseUserDataEntity response = new ResponseUserDataEntity();
            response.setEmail(userInfo.getEmail());
            response.setPhone(userInfo.getPhone());
            response.setAddress1(userInfo.getAddress1());
            response.setAddress2(userInfo.getAddress2());
            response.setCity(userInfo.getCity());
            response.setState(userInfo.getState());
            response.setZipCode(userInfo.getZipCode());
            response.setName(userName.getFirstName());
            response.setMiddleName(userName.getMiddleName());
            response.setLastName(userName.getLastName());
            log.debug("RESPONSE >>> " + response);
            return ResponseEntity.ok(response);
    }

    /**
     * Finds user contact information by UID.
     *
     * @param uid the unique identifier for the user
     * @return Optional containing the user's contact info entity
     */
    public Optional<MongoUsersContactInfoEntity> findUsersInfoByUid(String uid) {
        return usersContactInfoRepository.findUserInfoByUid(uid);
    }

    /**
     * Finds user's name information by UID.
     *
     * @param uid the unique identifier for the user
     * @return Optional containing the user's name entity
     */
    public Optional<MongoUsersNameEntity> findUserNameByUid(String uid) {
        return usersNameRepository.findUserNameByUid(uid);
    }

    /**
     * Update Data Service
     *
     * Updates existing user data with the provided new user request data.
     * @param userRequest the new user data for the update
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */
    public ResponseEntity<String> updateUser(RequestUpdateUserEntity userRequest,HttpHeaders httpHeaders) {
        log.debug("REQUEST >>> " + userRequest.toString());
        String uid = httpHeaders.getFirst("uid").toString();
        //Checks which DB should be called according to request,then if needed updates data.
        boolean contactUpdated = tryUpdateContactInfo(userRequest, uid);
        boolean nameUpdated = tryUpdateNameInfo(userRequest, uid);
        if (!contactUpdated && !nameUpdated) {
            throw new BadRequestException();
        }
        ResponseEntity<String> response = ResponseEntity.ok("User Updated");
        log.debug("RESPONSE >>> " + response);
        return response;
    }

    /**
     * Updates user contact Info when there is info to update
     * @return true if something was updated, false if not.
     */
    private boolean tryUpdateContactInfo(RequestUpdateUserEntity userRequest, String uid) {
        if (!hasContactInfoToUpdate(userRequest)) return false;

        MongoUsersContactInfoEntity contactInfo = usersContactInfoRepository.findUserInfoByUid(uid)
                .orElseThrow(NoDataFoundException::new);

        updateIfPresent(userRequest.getEmail(), contactInfo::setEmail);
        updateIfPresent(userRequest.getPhone(), contactInfo::setPhone);
        updateIfPresent(userRequest.getAddress1(), contactInfo::setAddress1);
        updateIfPresent(userRequest.getAddress2(), contactInfo::setAddress2);
        updateIfPresent(userRequest.getCity(), contactInfo::setCity);
        updateIfPresent(userRequest.getState(), contactInfo::setState);
        updateIfPresent(userRequest.getZipCode(), contactInfo::setZipCode);

        usersContactInfoRepository.save(contactInfo);
        return true;
    }

    /**
     * Updates user full name Info when there is info to update
     * @return true if something was updated, false if not.
     */
    private boolean tryUpdateNameInfo(RequestUpdateUserEntity userRequest, String uid) {
        if (!hasNameInfoToUpdate(userRequest)) return false;

        MongoUsersNameEntity nameInfo = usersNameRepository.findUserNameByUid(uid)
                .orElseThrow(NoDataFoundException::new);

        updateIfPresent(userRequest.getName(), nameInfo::setFirstName);
        updateIfPresent(userRequest.getMiddleName(), nameInfo::setMiddleName);
        updateIfPresent(userRequest.getLastName(), nameInfo::setLastName);

        usersNameRepository.save(nameInfo);
        return true;
    }

    /**
     * Verifies contact data on request.
     */
    private boolean hasContactInfoToUpdate(RequestUpdateUserEntity userRequest) {
        return Stream.of(
                userRequest.getEmail(), userRequest.getPhone(),
                userRequest.getAddress1(), userRequest.getAddress2(),
                userRequest.getCity(), userRequest.getState(),
                userRequest.getZipCode()
        ).anyMatch(value -> value != null && !value.isEmpty());
    }

    /**
     * Verifies name data on request.
     */
    private boolean hasNameInfoToUpdate(RequestUpdateUserEntity userRequest) {
        return Stream.of(
                userRequest.getName(),
                userRequest.getMiddleName(),
                userRequest.getLastName()
        ).anyMatch(value -> value != null && !value.isEmpty());
    }
    /**
     * Updates a specific field of a user entity if the provided new value is not empty or null.
     * @param newValue the new value for the field
     * @param setter function to set the new value
     */
    private void updateIfPresent(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }

    /**
     * Delete User Service
     *
     * Deletes a user from the database based on the provided request and HTTP headers.
     * @param userRequest contains the email of the user to be deleted
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */
    public ResponseEntity<String> deleteUser(RequestDeleteUserEntity userRequest, HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid");
        log.debug("REQUEST >>> " + uid + " delete data requested with this email: " + userRequest.getEmail().toString());
        // Fetch stored user data to validate deletion
        MongoUsersContactInfoEntity storedUserData = findUsersInfoByUid(uid).orElseThrow(() -> new NoDataFoundException());
        MongoUsersNameEntity storedUserName = findUserNameByUid(uid).orElseThrow(() -> new NoDataFoundException());
        // Validate email before deleting the user
        if (userRequest.getEmail().equals(storedUserData.getEmail())) {
                usersContactInfoRepository.deleteById(storedUserData.get_id().toString());
                usersNameRepository.deleteById(storedUserName.get_id().toString());
                ResponseEntity response = ResponseEntity.ok("User deleted successfully");
                log.debug("RESPONSE >>> " + response);
                return response;
            }else{
                throw new ForbiddenException();
            }
    }
}
