package cn.plantlink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@Configuration
public class ElasticsearchProperties {

    @Value("${elasticsearch.cluster.nodes}")
    private List<String> clusterNodes;

    public List<String> getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(List<String> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }
}
