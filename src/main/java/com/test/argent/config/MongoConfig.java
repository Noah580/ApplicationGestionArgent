package com.test.argent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration technique de MongoDB.
 * Les repositories Spring Data ne sont cherchés que dans l'adapter de persistance :
 * le domaine reste ignorant de MongoDB et n'y accède qu'à travers ses ports (domain/port/out).
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.test.argent.adapter.out.persistence")
public class MongoConfig {
}
