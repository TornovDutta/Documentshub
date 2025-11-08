package org.example.documentshub.service;

import org.example.documentshub.DTO.DocumentsDTO;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;

import java.util.List;

public interface DocumentService {


    List<DocumentsDTO> getAll();

    DocumentsDTO getById(String id) throws DocumentNotFoundException;

    DocumentsDTO create(String userId, DocumentsDTO document) throws UsersNotFoundException;

    DocumentsDTO update(String docId, String userId, DocumentsDTO document)
            throws UsersNotFoundException, DocumentNotFoundException;

    void delete(String docId, String userId)
            throws UsersNotFoundException, DocumentNotFoundException;
}
