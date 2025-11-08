package org.example.documentshub.controller;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.DocumentEnitity;
import org.example.documentshub.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {
    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<DocumentEnitity>> getAll() {
        return new ResponseEntity<>(documentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<DocumentEnitity> getById(@PathVariable String id) throws DocumentNotFoundException {
        return new ResponseEntity<>(documentService.getById(id), HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<DocumentEnitity> create(@PathVariable String userId, @RequestBody DocumentEnitity document) throws UsersNotFoundException {
        return new ResponseEntity<>(documentService.create(userId, document), HttpStatus.CREATED);
    }

    @PutMapping("{docId}/{userId}")
    public ResponseEntity<DocumentEnitity> update(
            @PathVariable String docId,
            @PathVariable String userId,
            @RequestBody DocumentEnitity document
    ) throws UsersNotFoundException,DocumentNotFoundException{
        return new ResponseEntity<>(documentService.update(docId, userId, document), HttpStatus.OK);
    }

    @DeleteMapping("{docId}/{userId}")
    public ResponseEntity<String> delete(@PathVariable String docId, @PathVariable String userId) throws UsersNotFoundException,DocumentNotFoundException{
        documentService.delete(docId, userId);
        return new ResponseEntity<>("Document deleted successfully", HttpStatus.NO_CONTENT);
    }

}
