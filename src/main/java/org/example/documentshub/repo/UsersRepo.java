package org.example.documentshub.repo;


import org.example.documentshub.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends MongoRepository<Users,String> {
}
