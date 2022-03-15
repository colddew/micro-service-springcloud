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
public class InfoCanalClientService {

    private static final Logger logger = LoggerFactory.getLogger(InfoCanalClientService.class);

    private static final int BATCH_SIZE = 20;

    @Autowired
    private CanalClientProperties canalClientProperties;

    @Autowired
    private InfoCanalClientHandlerFactory infoCanalClientHandlerFactory;

    private CanalConnector canalConnector;

    @PostConstruct
    private void init() throws Exception {

        canalConnector = CanalConnectors.newClusterConnector(canalClientProperties.getZkServers(),
                canalClientProperties.getCanalDestinationInfo(), "", "");
        canalConnector.connect();
        canalConnector.subscribe("shizhifengyundb.tb_article," +
                "shizhifengyundb.t_article_stock," +
                "shizhifengyundb.tb_moments," +
                "shizhifengyundb.t_non_text," +
                "shizhifengyundb.t_express," +
                "shizhifengyundb.t_express_stock");

        CompletableFuture.runAsync(() -> {
            try {
                fetch();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        });
    }

    public void fetch() throws Exception {

        while (BusinessConstants.CANAL_CLIENT_SWITCH_OPEN == canalClientProperties.getCanalClientSwitch()) {

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
                    logger.info("fetch message from {}, batchId: {}, size: {}", canalClientProperties.getCanalDestinationInfo(), batchId, size);
                    dispatch(message.getEntries());
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

                if (EntryType.TRANSACTIONBEGIN == entry.getEntryType() || EntryType.TRANSACTIONEND == entry.getEntryType()) {
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
                String schemaName = entry.getHeader().getSchemaName();
                String tableName = entry.getHeader().getTableName();

                logger.info("binlog[{}:{}], name[{},{}], eventType: {}, executeTime: {}({}), gtid: ({}), delay: {}ms",
                        entry.getHeader().getLogfileName(),
                        entry.getHeader().getLogfileOffset(),
                        schemaName,
                        tableName,
                        eventType,
                        executeTime,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executeTime),
                        entry.getHeader().getGtid(),
                        (new Date().getTime() - executeTime));

                if (EventType.QUERY == eventType || rowChange.getIsDdl()) {
                    logger.info("sql --> {}", rowChange.getSql());
                    continue;
                }

                InfoCanalClientHandler handler = infoCanalClientHandlerFactory.getInstance(schemaName, tableName);

                for (RowData rowData : rowChange.getRowDatasList()) {
                    if (eventType == EventType.UPDATE) {
                        handler.update(rowData);
                    } else if (eventType == EventType.INSERT) {
                        handler.insert(rowData);
                    } else if (eventType == EventType.DELETE) {
                        handler.delete(rowData);
                    }
                }
            }
        }
    }

    @PreDestroy
    private void destroy() throws Exception {
        canalConnector.disconnect();
    }
}
