package com.logingestor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	@Autowired
	private RestHighLevelClient elasticsearchClient;

	public List<Map<String, Object>> searchLogs(String level, String message, String resourceId, String timestamp, String fromDate, String toDate,
			String traceId, String spanId, String commit, String parentResourceId) throws IOException {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		if (level != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("level", level));

		}
		if (message != null) {
		    boolQueryBuilder.should(QueryBuilders.regexpQuery("message", "(?i).*" + message + ".*"));
		}
		if (resourceId != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("resourceId", resourceId));
		}
		if (fromDate != null && toDate != null) {
			RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("timestamp").gte(fromDate)
					.lte(toDate);
			boolQueryBuilder.filter(rangeQuery);
		}
		if(timestamp != null) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("timestamp", timestamp));
		}
		if (traceId != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("traceId", traceId));
		}
		if (spanId != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("spanId", spanId));
		}
		if (commit != null) {
			boolQueryBuilder.must(QueryBuilders.termQuery("commit", commit));
		}
		if (parentResourceId != null) {
			boolQueryBuilder.must(QueryBuilders.regexpQuery("metadata.parentResourceId", "(?i).*" + parentResourceId + ".*"));
		}
		
		boolQueryBuilder.should(QueryBuilders.matchAllQuery());

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQueryBuilder);

		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

		List<Map<String, Object>> results = new ArrayList<>();
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			results.add(hit.getSourceAsMap());
		}

		return results;
	}
}
