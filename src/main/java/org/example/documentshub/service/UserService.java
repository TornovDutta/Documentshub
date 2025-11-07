package org.example.documentshub.service;




import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.model.Users;

import java.util.List;


public interface UserService  {

    UsersDTO add(Users users);

    UsersDTO update(String id, Users users);
}
