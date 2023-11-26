package com.logingestor.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logingestor.document.LogDocument;

@Service
public class KafkaProducerService {
	@Autowired
	private KafkaTemplate<String, List<LogDocument>> kafkaTemplate;
	private final String TOPIC = "kafkaTopic";

	public int uploadlogs(MultipartFile file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		List<LogDocument> logDocuments = new ArrayList<>();

		while ((line = br.readLine()) != null) {
			LogDocument logDocument = parseLogLine(line);
			if (logDocument != null) {
				logDocuments.add(logDocument);
			}
		}
		sendMessage(logDocuments);

		return logDocuments.size();
	}

	private LogDocument parseLogLine(String line) {
		String[] parts = line.split(",");
		if (parts.length != 8) {
			return null;
		}

		try {
			LogDocument logDocument = new LogDocument();
			logDocument.setLevel(parts[0]);
			logDocument.setMessage(parts[1]);
			logDocument.setResourceId(parts[2]);

			ZonedDateTime timestamp = ZonedDateTime.parse(parts[3]);
			logDocument.setTimestamp(timestamp);

			logDocument.setTraceId(parts[4]);
			logDocument.setSpanId(parts[5]);
			logDocument.setCommit(parts[6]);

			String metadataStr = parts[7];

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> metadata = objectMapper.readValue(metadataStr,
					new TypeReference<Map<String, Object>>() {
					});
			logDocument.setMetadata(metadata);

			return logDocument;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void sendMessage(List<LogDocument> logDocuments) {
		this.kafkaTemplate.send(TOPIC, logDocuments);
		kafkaTemplate.flush();
	}
}