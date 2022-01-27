package cn.plantlink.service.canal;

import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author colddew
 * @Date 2022-01-27
 */
public abstract class AbstractInfoCanalClientHandler implements InfoCanalClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractInfoCanalClientHandler.class);

    @Override
    public void update(RowData rowData) throws Exception {

    }

    @Override
    public void insert(RowData rowData) throws Exception {

    }

    @Override
    public void delete(RowData rowData) throws Exception {

    }

    protected String getKey(List<Column> columns, String table, String key) throws Exception {

        String value = columns.stream().filter(p -> null != p && p.getName().equalsIgnoreCase(key))
                .findFirst().get().getValue();

        logger.info("[{}][{}]为空", table, key);

        return value;
    }
}
