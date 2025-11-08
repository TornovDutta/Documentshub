package org.example.documentshub.service.serviceImple;

import lombok.RequiredArgsConstructor;
import org.example.documentshub.DTO.DocumentsDTO;
import org.example.documentshub.exception.DocumentNotFoundException;
import org.example.documentshub.exception.UsersNotFoundException;
import org.example.documentshub.model.DocumentEnitity;
import org.example.documentshub.model.DocumentVersion;
import org.example.documentshub.model.Users;
import org.example.documentshub.repo.DocumentRepo;
import org.example.documentshub.repo.UsersRepo;
import org.example.documentshub.service.DocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImple implements DocumentService {
    private final DocumentRepo documentRepo;
    private final UsersRepo usersRepo;

    @Override
    public List<DocumentsDTO> getAll() {
        List<DocumentEnitity> documents = documentRepo.findAll();

        return documents.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentsDTO getById(String id) throws DocumentNotFoundException {
        DocumentEnitity document = documentRepo.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + id));

        return mapToDTO(document);
    }

    @Override
    public DocumentsDTO create(String userId, DocumentsDTO documentDto) throws UsersNotFoundException {
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new UsersNotFoundException("User not found with id: " + userId));

        if (!user.getRole().contains("ADMIN") && !user.getRole().contains("EDITOR")) {
            throw new UsersNotFoundException("Access denied: only Admin or Editor can create documents");
        }

        DocumentEnitity document = new DocumentEnitity();
        document.setTitle("Untitled Document");
        document.setDescription(documentDto.getContent());
        document.setCreateBy(user);
        document.setCreateDate(LocalDateTime.now());


        DocumentVersion version = new DocumentVersion(1, documentDto.getContent(), user.getUserName());
        document.setDocumentVersionList(List.of(version));

        DocumentEnitity saved = documentRepo.save(document);

        return new DocumentsDTO(
                saved.getId(),
                1,
                documentDto.getContent(),
                version.getTimestamp()
        );
    }

    @Override
    public DocumentsDTO update(String docId, String userId, DocumentsDTO documentDto)
            throws UsersNotFoundException, DocumentNotFoundException {

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new UsersNotFoundException("User not found with id: " + userId));

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + docId));

        boolean isAdmin = user.getRole().contains("ADMIN");
        boolean isOwner = user.getRole().contains("EDITOR")
                && document.getCreateBy().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new UsersNotFoundException("Access denied: you can update only your own documents");
        }

        int newVersionNumber = document.getDocumentVersionList().size() + 1;
        DocumentVersion newVersion = new DocumentVersion(
                newVersionNumber,
                documentDto.getContent(),
                user.getUserName()
        );
        document.getDocumentVersionList().add(newVersion);
        documentRepo.save(document);

        return new DocumentsDTO(
                document.getId(),
                newVersionNumber,
                documentDto.getContent(),
                newVersion.getTimestamp()
        );
    }

    @Override
    public void delete(String docId, String userId)
            throws UsersNotFoundException, DocumentNotFoundException {

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new UsersNotFoundException("User not found with id: " + userId));

        if (!user.getRole().contains("ADMIN")) {
            throw new UsersNotFoundException("Access denied: only Admin can delete documents");
        }

        DocumentEnitity document = documentRepo.findById(docId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found with id: " + docId));

        documentRepo.delete(document);
    }

    private DocumentsDTO mapToDTO(DocumentEnitity document) {
        if (document.getDocumentVersionList() == null || document.getDocumentVersionList().isEmpty()) {
            return new DocumentsDTO(document.getId(), 0, "No content", document.getCreateDate());
        }

        DocumentVersion latest = document.getDocumentVersionList()
                .get(document.getDocumentVersionList().size() - 1);

        return new DocumentsDTO(
                document.getId(),
                latest.getVersionNumber(),
                latest.getContent(),
                latest.getTimestamp()
        );
    }
}
