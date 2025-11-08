package org.example.documentshub.service;

import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.exception.AdminNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.Users;

import java.util.List;

public interface AdminService  {
    List<UsersDTO> getAll();

    UsersDTO getID(String id) throws UsersNotFoundException;

    UsersDTO add(Users users);

    UsersDTO updateAdmin(String id, Users users) throws AdminNotFoundException;

    Void delete(String id) throws AdminNotFoundException;
}
