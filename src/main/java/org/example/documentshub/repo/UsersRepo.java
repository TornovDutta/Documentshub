package org.example.documentshub.repo;


import org.example.documentshub.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UsersRepo extends MongoRepository<Users,String> {
    Optional<Users> findByUserName(String username);

}
