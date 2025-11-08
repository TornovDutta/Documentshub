package org.example.documentshub.controller;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.DocumentVersionDTO;
import org.example.documentshub.DTO.DocumentsDTO;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.VersionNotFoundException;
import org.example.documentshub.model.DocumentVersion;
import org.example.documentshub.service.DocumentVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents/version")
public class DocumentVersionController {
    private final DocumentVersionService documentVersionService;

    @GetMapping("{docId}")
    public ResponseEntity<List<DocumentVersionDTO>> getAllVersions(@PathVariable String docId) throws DocumentNotFoundException {
        return new ResponseEntity<>(documentVersionService.getAllVersions(docId), HttpStatus.OK);
    }

    @GetMapping("{docId}/{versionNumber}")
    public ResponseEntity<DocumentsDTO> getVersionByNumber(@PathVariable String docId,
                                                              @PathVariable int versionNumber) throws DocumentNotFoundException, VersionNotFoundException {
        return new ResponseEntity<>(documentVersionService.getVersionByNumber(docId, versionNumber), HttpStatus.OK);
    }

    @PostMapping("{docId}/{userId}")
    public ResponseEntity<DocumentVersionDTO> addVersion(
            @PathVariable String docId,
            @PathVariable String userId,
            @RequestBody DocumentVersion version
    ) throws DocumentNotFoundException, VersionNotFoundException {
        return new ResponseEntity<>(documentVersionService.addVersion(docId, userId, version), HttpStatus.CREATED);
    }
}
