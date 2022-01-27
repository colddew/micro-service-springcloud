package cn.plantlink.service.canal;

import cn.plantlink.common.BusinessConstants;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.springframework.stereotype.Service;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Service
public class InfoCanalClientArticleStockHandler extends AbstractInfoCanalClientHandler {

    @Override
    public void update(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getAfterColumnsList(), BusinessConstants.DB_TABLE_ARTICLE_STOCK, "article_id");
        // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
    }

    @Override
    public void insert(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getAfterColumnsList(), BusinessConstants.DB_TABLE_ARTICLE_STOCK, "article_id");
        // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
    }

    @Override
    public void delete(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getBeforeColumnsList(), BusinessConstants.DB_TABLE_ARTICLE_STOCK, "article_id");
        // TODO 查询DB同步ES，删除（物理）
    }
}
