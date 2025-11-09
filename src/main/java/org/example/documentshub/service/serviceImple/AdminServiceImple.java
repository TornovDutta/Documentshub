package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.exception.AdminNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.Users;
import org.example.documentshub.repo.UsersRepo;
import org.example.documentshub.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImple implements AdminService {
    private final UsersRepo usersRepo;

    private final BCryptPasswordEncoder encoder;

    public List<UsersDTO> getAll() {
        List<Users> users = usersRepo.findAll();

        return users.stream()
                .map(user -> new UsersDTO(user.getUserName(), user.getRole()))
                .collect(Collectors.toList());
    }


    public UsersDTO getID(String id) throws UsersNotFoundException{
        Users user=usersRepo.findById(id).orElseThrow(()->
                new UsersNotFoundException("wrong user id"));
        UsersDTO returnUser=new UsersDTO(user.getUserName(),user.getRole());
        return returnUser;

    }

    public UsersDTO add(Users users){
        users.setUserName(users.getUserName());
        users.setPassword(encoder.encode(users.getPassword()));
        users.setRole(Set.of("ADMIN"));

        Users saveUser=usersRepo.save(users);
        UsersDTO returnUser=new UsersDTO(saveUser.getUserName(),users.getRole());

        return returnUser;



    }

    public UsersDTO updateAdmin(String id, Users users) throws AdminNotFoundException{
        Users existedUser=usersRepo.findById(id).orElseThrow(()->
                new AdminNotFoundException("admin not found"));

        if(!existedUser.getRole().contains("ADMIN")){
            throw new AdminNotFoundException("admin not found");
        }
        existedUser.setUserName(users.getUserName());
        existedUser.setPassword(encoder.encode(users.getPassword()));

        usersRepo.save(existedUser);

        UsersDTO returnUser=new UsersDTO(existedUser.getUserName(),existedUser.getRole());
        return  returnUser;

    }

    public Void delete(String id) throws AdminNotFoundException{
        Users admin = usersRepo.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + id));

        if (!admin.getRole().contains("ADMIN")) {
            throw new AdminNotFoundException("User is not an admin");
        }

        usersRepo.deleteById(id);
        return null;
    }


}
