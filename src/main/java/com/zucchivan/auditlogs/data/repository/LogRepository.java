package com.zucchivan.auditlogs.data.repository;

import com.zucchivan.auditlogs.model.Log;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = LogRepository.COLLECTION_NAME, path = LogRepository.COLLECTION_NAME)
public interface LogRepository extends MongoRepository<Log, String> {

    public final static String COLLECTION_NAME = "logs";

    List<Log> findByUserId(@Param("userId") String userId);

}
