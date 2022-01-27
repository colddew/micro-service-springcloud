package cn.plantlink.service.canal;

import cn.plantlink.common.BusinessConstants;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Service
public class InfoCanalClientMomentsStockHandler extends AbstractInfoCanalClientHandler {

    @Override
    public void update(RowData rowData) throws Exception {
        String momentsId = getMomentsId(rowData.getAfterColumnsList());
        // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
    }

    private String getMomentsId(List<Column> columns) throws Exception {

        Map<String, String> momentsStockMap = columns.stream().filter(p -> null != p)
                .collect(Collectors.toMap(p -> p.getName().toLowerCase(), p -> p.getValue(), (k1, k2) -> k1));

        if (String.valueOf(BusinessConstants.NON_TEXT_CONTENT_TYPE_MOMENTS).equals(momentsStockMap.get("content_type"))) {
            return momentsStockMap.get("content_id");
        }

        return null;
    }

    @Override
    public void insert(RowData rowData) throws Exception {
        String momentsId = getMomentsId(rowData.getAfterColumnsList());
        // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
    }

    @Override
    public void delete(RowData rowData) throws Exception {
        String momentsId = getMomentsId(rowData.getBeforeColumnsList());
        // TODO 查询DB同步ES，删除（物理）
    }
}
