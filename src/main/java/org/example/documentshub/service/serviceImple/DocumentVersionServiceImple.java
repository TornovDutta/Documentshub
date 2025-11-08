package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.DocumentVersionDTO;
import org.example.documentshub.DTO.DocumentsDTO;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.VersionNotFoundException;
import org.example.documentshub.model.DocumentEnitity;
import org.example.documentshub.model.DocumentVersion;
import org.example.documentshub.model.Users;
import org.example.documentshub.repo.DocumentRepo;
import org.example.documentshub.repo.UsersRepo;
import org.example.documentshub.service.DocumentVersionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentVersionServiceImple implements DocumentVersionService {

    private final DocumentRepo documentRepo;
    private final UsersRepo usersRepo;

    @Override
    public List<DocumentVersionDTO> getAllVersions(String docId) throws DocumentNotFoundException {
        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + docId));

        return document.getDocumentVersionList().stream()
                .map(v -> new DocumentVersionDTO(
                        docId,
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreateDate(),
                        null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentsDTO getVersionByNumber(String docId, int versionNumber)
            throws DocumentNotFoundException, VersionNotFoundException {

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + docId));

        DocumentVersion version = document.getDocumentVersionList().stream()
                .filter(v -> v.getVersionNumber() == versionNumber)
                .findFirst()
                .orElseThrow(() -> new VersionNotFoundException("Version not found: " + versionNumber));

        return new DocumentsDTO(
                docId,
                version.getVersionNumber(),
                version.getContent(),
                version.getTimestamp()
        );
    }

    @Override
    public DocumentVersionDTO addVersion(String docId, String userId, DocumentVersion version)
            throws DocumentNotFoundException {

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new DocumentNotFoundException("User not found"));

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        boolean isAdmin = user.getRole().contains("ADMIN");
        boolean isOwner = user.getRole().contains("EDITOR") &&
                document.getCreateBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Access denied: only Admin or document owner (Editor) can add a version");
        }

        List<DocumentVersion> versionList = document.getDocumentVersionList();
        if (versionList == null) {
            versionList = new ArrayList<>();
        }

        int newVersionNumber = versionList.size() + 1;
        DocumentVersion newVersion = new DocumentVersion(
                newVersionNumber,
                version.getContent(),
                user.getUserName(),
                LocalDateTime.now()
        );

        versionList.add(newVersion);
        document.setDocumentVersionList(versionList);
        documentRepo.save(document);

        return new DocumentVersionDTO(
                document.getId(),
                document.getTitle(),
                document.getDescription(),
                document.getCreateDate(),
                null
        );
    }
}
