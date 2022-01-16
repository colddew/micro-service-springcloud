package cn.plantlink.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@Configuration
public class ElasticsearchConfig {

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    public static final String DELIMITER_COLON = ":";

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        RestClient restClient = RestClient.builder(getHttpHostArray())
//                .setHttpClientConfigCallback(getHttpClientConfigCallback())
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

    private HttpHost[] getHttpHostArray() {

        List<String> clusterNodes = elasticsearchProperties.getClusterNodes();
        if (CollectionUtils.isEmpty(clusterNodes)) {
            throw new RuntimeException("load elasticsearch properties error");
        }

        Set<HttpHost> nodes = new HashSet<>();
        for (String node : clusterNodes) {

            String[] hostAndPort = node.split(DELIMITER_COLON);
            if (null != hostAndPort && hostAndPort.length == 2) {
                nodes.add(new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
            }
        }

        return nodes.stream().toArray(HttpHost[]::new);
    }

    private RestClientBuilder.HttpClientConfigCallback getHttpClientConfigCallback() {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("userName", "password"));

        return httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }
}
