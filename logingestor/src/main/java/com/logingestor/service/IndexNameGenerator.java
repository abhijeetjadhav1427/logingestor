package com.logingestor.service;

import org.springframework.stereotype.Service;

@Service
public class IndexNameGenerator {
	
	public String generateIndexName(String level, String resourceId) {
		// Replace "unknown" or empty values with "*"
		level = replaceUnknown(level);
		resourceId = replaceUnknown(resourceId);

		return ".ds-logs-" + level.toLowerCase() + "-" + resourceId.toLowerCase() + "-*";
	}

	private String replaceUnknown(String value) {
		return (value != null && !value.isEmpty()) ? value : "*";
	}
	
}
