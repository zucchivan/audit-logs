package com.zucchivan.auditlogs.data.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableMongoRepositories(basePackages = "com.zucchivan.auditlogs.data.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "audit-logs";
    }

    @Override
    public MongoClient mongoClient() {
        /* TODO: move DB configuration to configuration file
           TODO: PASSWORD SHOULD NOT USUALLY BE EXPOSED AND HARDCODED */
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://ivan:admin@zucchivan-rigox.mongodb.net/test?retryWrites=true&w=majority");
        return new MongoClient(uri);
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.zucchivan.auditlogs.data";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        Package mappingBasePackage = getClass().getPackage();
        return Collections.singleton(mappingBasePackage == null ?
                null : mappingBasePackage.getName());
    }

}