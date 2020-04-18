package com.example.studentportal.service;

import com.example.studentportal.dto.request.CreateDocumentRequest;
import com.example.studentportal.dto.request.filter.DocumentFilterRequest;
import com.example.studentportal.dto.response.UploadDocumentResponseDto;
import com.example.studentportal.model.Document;
import org.springframework.core.io.Resource;

import java.util.List;

public interface DocumentService {
    Document storeFile(CreateDocumentRequest request);

    Resource loadFileAsResource(String fileName);

    List<UploadDocumentResponseDto> findAllPaging(DocumentFilterRequest request);

    Document delete(int docId);
}
