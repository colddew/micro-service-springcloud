package cn.plantlink.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.Version;
import co.elastic.clients.transport.rest_client.RestClientOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@Configuration
public class ElasticsearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    public static final String DELIMITER_COLON = ":";

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        RestClient restClient = RestClient.builder(getHttpHostArray())
                // disable this line if you don't use username/password
                .setHttpClientConfigCallback(getHttpClientConfigCallback())
                .build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        recordElasticsearchRequestHeaders(transport);

        return new ElasticsearchClient(transport);
    }

    private void recordElasticsearchRequestHeaders(ElasticsearchTransport transport) {

        List<Map.Entry<String, String>> headers = (List) transport.options().headers();
        Iterator<Map.Entry<String, String>> iterator = headers.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            logger.info("Elasticsearch request header, {}: {}", next.getKey(), next.getValue());
        }
    }

    private RestClientOptions buildCustomizedRestClientOptions() throws Exception {

        String ua = String.format(
                Locale.ROOT,
                "elastic-java/%s (Java/%s)",
                Version.VERSION == null ? "Unknown" : Version.VERSION.toString(),
                System.getProperty("java.version")
        );

        RequestOptions requestOptions = RequestOptions.DEFAULT.toBuilder()
                .addHeader("User-Agent", ua)
                .addHeader("Accept", ContentType.APPLICATION_JSON.toString())
                .addHeader("Content-Type", ContentType.APPLICATION_JSON.toString())
                .build();

        return new RestClientOptions(requestOptions);
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
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchProperties.getUserName(),
                elasticsearchProperties.getPassword()));

        return httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }
}
