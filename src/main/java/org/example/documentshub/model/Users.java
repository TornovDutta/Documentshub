package org.example.documentshub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String id;
    private String userName;
    private String password;
    private Set<String> role=new HashSet<>();
    @DBRef
    private List<DocumentEnitity> documentList;

    public Users(String userName,String password){
        this.userName=userName;
        this.password=password;
        this.role.add("VIEWER");
    }

}
