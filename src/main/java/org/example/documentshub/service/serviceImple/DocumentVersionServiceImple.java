package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class DocumentVersionServiceImple implements DocumentVersionService {
    private final DocumentRepo documentRepo;
    private final UsersRepo usersRepo;

    @Override
    public List<DocumentVersion> getAllVersions(String docId) {
        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + docId));

        return document.getDocumentVersionList();
    }

    @Override
    public DocumentVersion getVersionByNumber(String docId, int versionNumber) {
        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + docId));

        return document.getDocumentVersionList().stream()
                .filter(v -> v.getVersionNumber() == versionNumber)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Version not found: " + versionNumber));
    }

    @Override
    public DocumentEnitity addVersion(String docId, String userId, DocumentVersion version) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        boolean isAdmin = user.getRole().contains("ADMIN");
        boolean isOwner = user.getRole().contains("EDITOR") &&
                document.getCreateBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Access denied: only Admin or document owner (Editor) can add a new version");
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

        return documentRepo.save(document);
    }
}
