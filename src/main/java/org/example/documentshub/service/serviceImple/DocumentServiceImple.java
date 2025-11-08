package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.model.DocumentEnitity;
import org.example.documentshub.model.Users;
import org.example.documentshub.repo.DocumentRepo;
import org.example.documentshub.repo.UsersRepo;
import org.example.documentshub.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImple implements DocumentService {
    private final DocumentRepo documentRepo;
    private final UsersRepo usersRepo;

    @Override
    public List<DocumentEnitity> getAll() {
        return documentRepo.findAll();
    }

    @Override
    public DocumentEnitity getById(String id) {
        return documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    @Override
    public DocumentEnitity create(String userId, DocumentEnitity document) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().contains("ADMIN") && !user.getRole().contains("EDITOR")) {
            throw new RuntimeException("Access denied: only Admin or Editor can create documents");
        }

        document.setCreateBy(user);
        document.setCreateDate(java.time.LocalDateTime.now());
        return documentRepo.save(document);
    }

    @Override
    public DocumentEnitity update(String docId, String userId, DocumentEnitity document) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DocumentEnitity existing = documentRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        boolean isAdmin = user.getRole().contains("ADMIN");
        boolean isOwner = user.getRole().contains("EDITOR") &&
                existing.getCreateBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Access denied: you can update only your own documents");
        }

        existing.setTitle(document.getTitle());
        existing.setDescription(document.getDescription());
        return documentRepo.save(existing);
    }

    @Override
    public void delete(String docId, String userId) {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().contains("ADMIN")) {
            throw new RuntimeException("Access denied: only Admin can delete documents");
        }

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        documentRepo.delete(document);
    }
}
