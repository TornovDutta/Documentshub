package org.example.documentshub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsDTO {
    private String id;
    private int versionNumber;
    private String content;

    private LocalDateTime timestamp;
}
