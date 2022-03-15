package cn.plantlink.service.canal;

import cn.plantlink.common.BusinessConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author colddew
 * @Date 2022-01-26
 */
@Service
public class InfoCanalClientHandlerFactory {

    @Autowired
    private InfoCanalClientArticleHandler infoCanalClientArticleHandler;

    @Autowired
    private InfoCanalClientArticleStockHandler infoCanalClientArticleStockHandler;

    @Autowired
    private InfoCanalClientMomentsHandler infoCanalClientMomentsHandler;

    @Autowired
    private InfoCanalClientMomentsStockHandler infoCanalClientMomentsStockHandler;

    @Autowired
    private InfoCanalClientExpressHandler infoCanalClientExpressHandler;

    @Autowired
    private InfoCanalClientExpressStockHandler infoCanalClientExpressStockHandler;

    public InfoCanalClientHandler getInstance(String schema, String table) throws Exception {
        if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_ARTICLE.equalsIgnoreCase(table)) {
            return infoCanalClientArticleHandler;
        } else if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_ARTICLE_STOCK.equalsIgnoreCase(table)) {
            return infoCanalClientArticleStockHandler;
        } else if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_MOMENTS.equalsIgnoreCase(table)) {
            return infoCanalClientMomentsHandler;
        } else if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_MOMENTS_STOCK.equalsIgnoreCase(table)) {
            return infoCanalClientMomentsStockHandler;
        } else if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_EXPRESS.equalsIgnoreCase(table)) {
            return infoCanalClientExpressHandler;
        } else if (BusinessConstants.DB_SCHEMA_STORM.equalsIgnoreCase(schema) && BusinessConstants.DB_TABLE_EXPRESS_STOCK.equalsIgnoreCase(table)) {
            return infoCanalClientExpressStockHandler;
        } else {
            throw new Exception("不支持该消息处理类型");
        }
    }
}
