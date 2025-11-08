package org.example.documentshub.service;

import org.example.documentshub.model.DocumentEnitity;

import java.util.List;

public interface DocumentService {

    List<DocumentEnitity> getAll();

    DocumentEnitity getById(String id);

    DocumentEnitity create(String userId, DocumentEnitity document);

    DocumentEnitity update(String docId, String userId, DocumentEnitity document);

    void delete(String docId, String userId);
}
