package com.example.studentportal.service.impl;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.config.FileStorageProperties;
import com.example.studentportal.dto.request.CreateDocumentRequest;
import com.example.studentportal.dto.request.filter.DocumentFilterRequest;
import com.example.studentportal.dto.response.UploadDocumentResponseDto;
import com.example.studentportal.enumeration.Role;
import com.example.studentportal.exception.FileStorageException;
import com.example.studentportal.exception.MyFileNotFoundException;
import com.example.studentportal.exception.ResourceNotFoundException;
import com.example.studentportal.model.Document;
import com.example.studentportal.model.User;
import com.example.studentportal.repository.DocumentRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final UserRepository userRepository;
    private final Path fileStorageLocation;
    private final DocumentRepository documentRepository;
    private final FileStorageProperties fileStorageProperties;
    @Autowired
    public DocumentServiceImpl(final UserRepository userRepository,
                               final FileStorageProperties fileStorageProperties,
                               final DocumentRepository documentRepository, FileStorageProperties fileStorageProperties1) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDocumentDir())
                .toAbsolutePath().normalize();
        this.fileStorageProperties = fileStorageProperties1;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public Document storeFile(CreateDocumentRequest request) {
        MultipartFile file = request.getFile();
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Check student id
        User student = userRepository.findAllByIdAndRole_Id(request.getStudentId(), Role.STUDENT.getValue());
        if (student == null) {
            throw new ResourceNotFoundException("StudentId " + request.getStudentId() + " not found");
        }
        // Check tutor id
        User tutor = userRepository.findAllByIdAndRole_Id(request.getTutorId(), Role.TUTOR.getValue());
        if (tutor == null) {
            throw new ResourceNotFoundException("TutorId " + request.getTutorId() + " not found");
        }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Save document info
            Document document = new Document();
            document.setStudent(student);
            document.setTutor(tutor);
            document.setName(fileName);
            document.setUploadedDate(new Date());

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(ApiConstant.DOCUMENT + ApiConstant.DOWNLOAD + "/")
                    .path(fileName)
                    .toUriString();
            document.setFileDownloadUri(fileDownloadUri);
            document.setFileType(request.getFile().getContentType());
            document.setSize(request.getFile().getSize());
            documentRepository.save(document);
            // create document file
            createDocumentFile(file, fileName, document);
            return document;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private void createDocumentFile(MultipartFile file, String fileName, Document document) throws IOException {
        //createDirectory(document);
        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String viewURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ApiConstant.CONTENT + "/")
                .path(fileName)
                .toUriString();
        document.setViewDocumentUrl(viewURL);
        documentRepository.save(document);
    }

//    private void createDirectory(Document document) {
//        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDocumentDir() + "/")
//                .toAbsolutePath().normalize();
//        try {
//            Files.createDirectories(fileStorageLocation);
//        } catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
//        }
//    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public List<UploadDocumentResponseDto> findAllPaging(DocumentFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getStart(), request.getLimit());
        Page<Document> documents;
        long total = 0;
        if (null != request.getTutorId()) {
            documents = documentRepository.findAllByTutor_Id(request.getTutorId(), pageable);
        } else if (null != request.getStudentId()){
            documents = documentRepository.findAllByStudent_Id(request.getStudentId(), pageable);
        } else {
            documents = documentRepository.findAll(pageable);
        }
        total = documents.getTotalElements();
        List<UploadDocumentResponseDto> responseDtos;
        if (total > 0) {
            responseDtos = documents.stream().map(document -> UploadDocumentResponseDto.fromDocument(document)).collect(Collectors.toList());
            return responseDtos;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Document delete(int docId) {
        Optional<Document> documentOptional = documentRepository.findById(docId);
        Document document = new Document();
        if(documentOptional.isPresent()){
            document = documentOptional.get();
            document.setDeletedFlag(true);
            documentRepository.save(document);
            log.info("Soft delete document with id: " + docId);

        } else {
            log.error("Document not found with id: " + docId);
            throw new ResourceNotFoundException("Document not found with id: " + docId);
        }
        return document;
    }
}
