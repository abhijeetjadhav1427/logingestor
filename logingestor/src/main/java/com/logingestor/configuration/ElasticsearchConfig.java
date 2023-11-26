package com.logingestor.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "*")
public class ElasticsearchConfig extends AbstractFactoryBean {
	
	private RestHighLevelClient restHighLevelClient;
	
	@Override
	public Class getObjectType() {
		return RestHighLevelClient.class;
	}

	@Override
	protected RestHighLevelClient createInstance() throws Exception {
		try {
			restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9300, "http")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return restHighLevelClient;
	}

	@Override
	public void destroy() throws Exception {
		try {
			restHighLevelClient.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		super.destroy();
	}
	
}
