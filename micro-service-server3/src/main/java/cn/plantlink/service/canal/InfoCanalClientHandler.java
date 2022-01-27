package cn.plantlink.service.canal;

import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

public interface InfoCanalClientHandler {

    void update(RowData rowData) throws Exception;

    void insert(RowData rowData) throws Exception;

    void delete(RowData rowData) throws Exception;
}
