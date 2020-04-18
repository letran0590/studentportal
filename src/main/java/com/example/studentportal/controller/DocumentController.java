package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.CreateDocumentRequest;
import com.example.studentportal.dto.request.filter.DocumentFilterRequest;
import com.example.studentportal.dto.response.ResponseDto;
import com.example.studentportal.dto.response.UploadDocumentResponseDto;
import com.example.studentportal.model.Document;
import com.example.studentportal.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiConstant.DOCUMENT) // This means URL's start with /demo (after Application path)
public class DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@PostMapping("/upload")
	public ResponseDto<Document> uploadFile(CreateDocumentRequest request) {
		Document document = documentService.storeFile(request);
		return new ResponseDto<>(document);
	}

//	@PostMapping("/uploadMultipleFiles")
//	public List<UploadDocumentResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		return Arrays.asList(files)
//				.stream()
//				.map(file -> uploadFile(file))
//				.collect(Collectors.toList());
//	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = documentService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/filter")
	public List<UploadDocumentResponseDto> filter(DocumentFilterRequest request){
		return documentService.findAllPaging(request);
	}

	@DeleteMapping("/{docId}")
	public ResponseEntity<Document> delete(@PathVariable int docId){
		Document document = documentService.delete(docId);
		return new ResponseEntity<>(document , HttpStatus.OK);
	}
}
