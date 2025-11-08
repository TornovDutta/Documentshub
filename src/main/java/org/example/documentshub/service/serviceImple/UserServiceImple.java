package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.Users;
import org.example.documentshub.repo.UsersRepo;
import org.example.documentshub.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService {
    private final UsersRepo usersRepo;

    public UsersDTO add(Users users){
        users.setUserName(users.getUserName());
        users.setPassword(users.getPassword());
        users.setRole(Set.of("EDITOR"));
        usersRepo.save(users);
        UsersDTO usersDTO=new UsersDTO(users.getUserName(),users.getRole());
        return usersDTO;
    }
    public UsersDTO update(String id, Users users) throws UsersNotFoundException{
        Users repoUser=usersRepo.findById(id).orElseThrow(()->
                new UsersNotFoundException("wrong id"));
        repoUser.setUserName(users.getUserName());
        repoUser.setPassword(users.getPassword());
        usersRepo.save(repoUser);

        UsersDTO returnUser=new UsersDTO(users.getUserName(),users.getRole());
        return returnUser;

    }
}
