//package cn.plantlink.health;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.HealthStatus;
//import co.elastic.clients.elasticsearch.cluster.HealthResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.health.AbstractHealthIndicator;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.stereotype.Component;
//
///**
// * @Author colddew
// * @Date 2022-02-23
// */
//@Component
//@ConditionalOnBean(ElasticsearchClient.class)
//public class ElasticsearchHealthIndicator extends AbstractHealthIndicator {
//
//    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchHealthIndicator.class);
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//
//    @Override
//    protected void doHealthCheck(Health.Builder builder) throws Exception {
//
//        try {
//            HealthResponse response = elasticsearchClient.cluster().health();
//
//            if (!response.status().equals(HealthStatus.Red)) {
//                logger.info("elasticsearch is working");
//                builder.up().withDetail("description", "elasticsearch is working");
//            }
//        } catch (Exception e) {
//            logger.error("elasticsearch launch has error : {}, caused by : {}", e.getMessage(), e.getCause());
//            builder.outOfService().withDetail("description", "elasticsearch launch has error, " + e.getMessage());
//        }
//    }
//}
