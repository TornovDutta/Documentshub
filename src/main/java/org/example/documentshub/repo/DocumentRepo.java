package org.example.documentshub.repo;

import org.example.documentshub.model.DocumentEnitity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepo extends MongoRepository<DocumentEnitity, String> {
}
