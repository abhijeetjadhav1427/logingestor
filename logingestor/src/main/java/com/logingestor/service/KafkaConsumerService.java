package com.logingestor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private ElasticSearchService elasticSearchService;

	@KafkaListener(topics = "kafkaTopic", groupId = "my-group")
	public void consume(List<Map<String, Object>> logDocuments) {
		try {
			elasticSearchService.bulkIndex(logDocuments);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
