package org.example.documentshub.controller;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.model.Users;
import org.example.documentshub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UsersDTO> addUsers(@RequestBody Users users){
        return new ResponseEntity<>(userService.add(users), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UsersDTO> updateUsers(@PathVariable String id,@RequestBody Users users){
        return new ResponseEntity<>(userService.update(id,users),HttpStatus.OK);
    }
}
