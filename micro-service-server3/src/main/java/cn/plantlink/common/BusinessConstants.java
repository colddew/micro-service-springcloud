package cn.plantlink.common;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
public interface BusinessConstants {

    int PAGINATION_PAGE_NO_DEFAULT = 1;
    int PAGINATION_PAGE_SIZE_DEFAULT = 10;

    String ELASTICSEARCH_INDEX_PRODUCT = "product";
    String ELASTICSEARCH_INDEX_INFO = "info";

    int CANAL_CLIENT_SWITCH_CLOSE = 0;
    int CANAL_CLIENT_SWITCH_OPEN = 1;

    String DB_SCHEMA_STORM = "shizhifengyundb";
    String DB_TABLE_ARTICLE = "tb_article";
    String DB_TABLE_ARTICLE_STOCK = "t_article_stock";
    String DB_TABLE_MOMENTS = "tb_moments";
    String DB_TABLE_MOMENTS_STOCK = "t_non_text";
    String DB_TABLE_EXPRESS = "t_express";
    String DB_TABLE_EXPRESS_STOCK = "t_express_stock";

    int NON_TEXT_CONTENT_TYPE_MOMENTS_COMMENT = 1;
    int NON_TEXT_CONTENT_TYPE_MOMENTS = 2;
}
