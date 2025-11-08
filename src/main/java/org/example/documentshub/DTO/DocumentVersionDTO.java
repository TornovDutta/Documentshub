package org.example.documentshub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionDTO {
    private String id;
    private String title;
    private String description;

    private LocalDateTime createdAt;
    private List<DocumentVersionDTO> versions;
}
