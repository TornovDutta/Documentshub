package org.example.documentshub.service;

import org.example.documentshub.model.DocumentEnitity;
import org.example.documentshub.model.DocumentVersion;

import java.util.List;

public interface DocumentVersionService {
    List<DocumentVersion> getAllVersions(String docId);

    DocumentVersion getVersionByNumber(String docId, int versionNumber);

    DocumentEnitity addVersion(String docId, String userId, DocumentVersion version);
}
