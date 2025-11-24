package com.paa.dms.user.manage.data.repository;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import com.paa.dms.user.manage.data.model.MongoAccessEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
/**
 * Repository interface for managing users access info.
 * Extends MongoRepository to leverage Spring Data MongoDB functionalities.
 */
public interface AccessRepository extends MongoRepository<MongoAccessEntity, String>{
        /**
         * Custom query to find a specific access info.
         *
         * @param tokenN The unique identifier of the user.
         * @param tokenP The unique identifier of the user.
         * @return Confirmation of the user data.
         */
        @Query(value = MongoConstants.FIND_BY_TOKENS)
        Optional<MongoAccessEntity> findUserByTokens(String tokenN, String tokenP);
}
