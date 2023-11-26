package com.logingestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.logingestor.service.KafkaProducerService;
import com.logingestor.service.SearchService;

@RestController
@CrossOrigin("http://localhost:3001/")
public class LogIngestController {
	@Autowired
	private KafkaProducerService producerService;
	@Autowired
	private SearchService searchService;
	
	@PostMapping("/uploadlogs")
	public ResponseEntity<?> uploadLogs(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
		}

		try {
			int size = producerService.uploadlogs(file);

			return ResponseEntity.ok("File uploaded successfully. Processed " + size + " log entries.");
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/logs/search")
	public ResponseEntity<?> searchLogs(
			@RequestParam(required = false) String level,
	        @RequestParam(required = false) String message,
	        @RequestParam(required = false) String resourceId,
	        @RequestParam(required = false) String timestamp,
	        @RequestParam(required = false) String fromDate,
	        @RequestParam(required = false) String toDate,
	        @RequestParam(required = false) String traceId,
	        @RequestParam(required = false) String spanId,
	        @RequestParam(required = false) String commit,
	        @RequestParam(required = false) String parentResourceId) {
		try {
			return ResponseEntity.ok(searchService.searchLogs(level, message, resourceId, timestamp, fromDate, toDate,
	                traceId, spanId, commit, parentResourceId));
		}
		catch (Exception e) {
			return new ResponseEntity<>("Failed to search: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
