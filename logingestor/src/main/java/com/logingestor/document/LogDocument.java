package com.logingestor.document;

import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "logs")
public class LogDocument {
	private String level;
	private String message;
	private String resourceId;
	private ZonedDateTime timestamp;
	private String traceId;
	private String spanId;
	private String commit;
	private Map<String, Object> metadata;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return "LogDocument [level=" + level + ", message=" + message + ", resourceId=" + resourceId + ", timestamp="
				+ timestamp + ", traceId=" + traceId + ", spanId=" + spanId + ", commit=" + commit + ", metadata="
				+ metadata + "]";
	}

}
