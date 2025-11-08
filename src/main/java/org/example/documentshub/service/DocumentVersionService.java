package org.example.documentshub.service;

import org.example.documentshub.DTO.DocumentVersionDTO;
import org.example.documentshub.DTO.DocumentsDTO;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.VersionNotFoundException;

import org.example.documentshub.model.DocumentVersion;

import java.util.List;

public interface DocumentVersionService {
    List<DocumentVersionDTO> getAllVersions(String docId) throws DocumentNotFoundException;

    DocumentsDTO getVersionByNumber(String docId, int versionNumber)
            throws DocumentNotFoundException, VersionNotFoundException;

    DocumentVersionDTO addVersion(String docId, String userId, DocumentVersion version)
            throws DocumentNotFoundException;
}
