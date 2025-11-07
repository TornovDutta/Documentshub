package org.example.documentshub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class DocumentEnitity {
    @Id
    private  String id;
    private String title;
    private  String description;

    @DBRef
    private Users createBy;

    private LocalDateTime createDate;

    private List<DocumentVersion> documentVersionList;

    public DocumentEnitity(String title,String description,Users createBy){
        this.title=title;
        this.description=description;
        this.createBy=createBy;
        this.createDate= LocalDateTime.now();
    }
}
