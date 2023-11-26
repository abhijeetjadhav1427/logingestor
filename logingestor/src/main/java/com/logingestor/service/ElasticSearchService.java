package com.logingestor.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchService {

	@Autowired
	private RestHighLevelClient client;

	public void bulkIndex(List<Map<String, Object>> logDocuments) throws IOException {
		StringBuilder bulkRequestBody = new StringBuilder();
		BulkRequest bulkRequest = new BulkRequest();

		for (Map<String, Object> doc : logDocuments) {
			String indexName = "logs-" + doc.get("level").toString().toLowerCase() + "-"
					+ doc.get("resourceId").toString().toLowerCase(); // Example: logs-error-server-1234

			IndexRequest indexRequest = new IndexRequest(indexName).source(doc).opType(OpType.CREATE);
			bulkRequest.add(indexRequest);
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
	}

	private String convertMapToJsonString(Map<String, Object> map) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// Handle JSON processing exception
			e.printStackTrace();
			return null;
		}
	}
	
}
