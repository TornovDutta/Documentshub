package org.example.documentshub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersion {
    private int versionNumber;
    private String content;
    private String modifiedBy;
    private LocalDateTime timestamp = LocalDateTime.now();

    public DocumentVersion(int versionNumber, String content, String modifiedBy) {
        this.versionNumber = versionNumber;
        this.content = content;
        this.modifiedBy = modifiedBy;
    }

}
