package com.paa.dms.user.manage.data.repository;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import com.paa.dms.user.manage.data.model.MongoUsersContactInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
/**
 * Repository interface for managing user's contact info.
 * Extends MongoRepository to leverage Spring Data MongoDB functionalities.
 */
public interface UsersContactInfoRepository extends MongoRepository <MongoUsersContactInfoEntity,String>{
    @Query(value = MongoConstants.FIND_BY_UID_QUERY)
    Optional<MongoUsersContactInfoEntity> findUserInfoByUid(String uid);
}
