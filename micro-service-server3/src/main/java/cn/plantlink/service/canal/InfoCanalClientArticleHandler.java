package cn.plantlink.service.canal;

import cn.plantlink.common.BusinessConstants;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Service
public class InfoCanalClientArticleHandler extends AbstractInfoCanalClientHandler {

    @Override
    public void update(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getAfterColumnsList(), BusinessConstants.DB_TABLE_ARTICLE, "article_id");
        if (StringUtils.isNotBlank(articleId)) {
            // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
        }
    }

    @Override
    public void insert(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getAfterColumnsList(), BusinessConstants.DB_TABLE_ARTICLE, "article_id");
        // TODO 查询DB同步ES，更新（隐藏）、删除（逻辑）
    }

    @Override
    public void delete(RowData rowData) throws Exception {
        String articleId = getKey(rowData.getBeforeColumnsList(), BusinessConstants.DB_TABLE_ARTICLE, "article_id");
        // TODO 查询DB同步ES，删除（物理）
    }
}
