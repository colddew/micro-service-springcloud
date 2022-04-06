package cn.plantlink.service.canal;

import cn.plantlink.common.BusinessConstants;
import cn.plantlink.config.CanalClientProperties;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Service
public class ExampleCanalClientService {

    private static final Logger logger = LoggerFactory.getLogger(ExampleCanalClientService.class);

    private static final int BATCH_SIZE = 20;

    @Autowired
    private CanalClientProperties canalClientProperties;

    private CanalConnector canalConnector;

    @PostConstruct
    private void init() throws Exception {

        if (BusinessConstants.CANAL_CLIENT_SWITCH_OPEN == canalClientProperties.getCanalClientSwitch()) {

            // support server failover locally
//        canalConnector = CanalConnectors.newClusterConnector(Arrays.asList(new InetSocketAddress("59.110.124.213", 11111),
//                new InetSocketAddress("39.106.229.248", 11111)),
//                canalClientProperties.getCanalDestinationExample(), "", "");

            // support server/client failover by zookeeper
            canalConnector = CanalConnectors.newClusterConnector(canalClientProperties.getZkServers(),
                    canalClientProperties.getCanalDestinationExample(), "", "");
            canalConnector.connect();
            canalConnector.subscribe(".*\\..*");

            CompletableFuture.runAsync(() -> {
                try {
                    fetch();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            });
        }
    }

    public void fetch() throws Exception {

        while (true) {

            try {
                Message message = canalConnector.getWithoutAck(BATCH_SIZE);
                long batchId = message.getId();
                int size = message.getEntries().size();

                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                } else {
                    logger.info("fetch message from {}, batchId: {}, size: {}", canalClientProperties.getCanalDestinationExample(), batchId, size);
                    dispatch(message.getEntries());
                    Thread.sleep(1000);
                }

                if (batchId != -1) {
                    canalConnector.ack(batchId);
                }
            } catch (Exception e) {

                logger.error(e.getMessage());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    // do nothing
                }

                canalConnector.rollback();
            }
        }
    }

    private void dispatch(List<Entry> entries) throws Exception {

        if (!CollectionUtils.isEmpty(entries)) {

            for (Entry entry : entries) {

                if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                    continue;
                }

                RowChange rowChange = null;
                try {
                    rowChange = RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data: " + entry.toString(), e);
                }

                EventType eventType = rowChange.getEventType();
                long executeTime = entry.getHeader().getExecuteTime();

                logger.info("binlog[{}:{}], name[{},{}], eventType: {}, executeTime: {}({}), gtid: ({}), delay: {}ms",
                        entry.getHeader().getLogfileName(),
                        entry.getHeader().getLogfileOffset(),
                        entry.getHeader().getSchemaName(),
                        entry.getHeader().getTableName(),
                        eventType,
                        executeTime,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executeTime),
                        entry.getHeader().getGtid(),
                        (new Date().getTime() - executeTime));

                for (RowData rowData : rowChange.getRowDatasList()) {
                    if (eventType == EventType.UPDATE) {
                        logger.info("==========> before");
                        printColumn(rowData.getBeforeColumnsList());
                        logger.info("==========> after");
                        printColumn(rowData.getAfterColumnsList());
                    } else if (eventType == EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else if (eventType == EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    }
                }
            }
        }
    }

    private void printColumn(List<Column> columns) {
        for (Column column : columns) {
            logger.info("{}: {}, update: {}", column.getName(), column.getValue(), column.getUpdated());
        }
    }

    @PreDestroy
    private void destroy() throws Exception {
        canalConnector.disconnect();
    }
}
