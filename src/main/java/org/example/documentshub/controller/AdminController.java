package org.example.documentshub.controller;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.UsersDTO;
import org.example.documentshub.exception.AdminNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.Users;
import org.example.documentshub.service.AdminService;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @GetMapping
    public ResponseEntity<List<UsersDTO>> get(){
        return  new ResponseEntity<>(adminService.getAll(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<UsersDTO> getById(@PathVariable String id) throws UsersNotFoundException {
        return  new ResponseEntity<>(adminService.getID(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<UsersDTO> create(@RequestBody Users users){
        return  new ResponseEntity<>(adminService.add(users),HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<UsersDTO> update(@PathVariable String id, @RequestBody Users users) throws AdminNotFoundException{
        return new ResponseEntity<>(adminService.updateAdmin(id,users),HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws AdminNotFoundException {
        return new ResponseEntity<>(adminService.delete(id),HttpStatus.NO_CONTENT);

    }


}
