package cn.plantlink.service;

import cn.plantlink.common.BusinessConstants;
import cn.plantlink.pojo.Product;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@Service
public class ElasticsearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public List<Product> getProductList() throws Exception {
        SearchResponse<Product> search = elasticsearchClient.search(s -> s
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("bicycle"))
                                )),
                Product.class);

        return search.hits().hits().stream().map(p -> p.source()).collect(Collectors.toList());
    }

    public Product getProduct(String productId) throws Exception {

        GetResponse<Product> get = elasticsearchClient.get(GetRequest.of(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                        .id("kcTpYX4BECsCMcfqQuWb")),
                Product.class);

        return get.source();
    }

    public String addProduct(Product product) throws Exception {

        IndexResponse index = elasticsearchClient.index(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                .document(product)
                .refresh(Refresh.True)
        );

        return index.id();
    }

    public String modifyProduct(Product product, String productId) throws Exception {

        UpdateResponse<Product> update = elasticsearchClient.update(UpdateRequest.of(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                        .id(productId)
                        .doc(product)),
                Product.class);

        return update.id();
    }

    public String deleteProduct(String productId) throws Exception {

        DeleteResponse delete = elasticsearchClient.delete(DeleteRequest.of(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                .id(productId)));

        return delete.id();
    }

    public void sort() throws Exception {
        SearchRequest.of(r -> r
                .index("idx-d")
                .sort(s -> s.field(f -> f.field("foo").order(SortOrder.Asc)))
                .sort(s -> s.field(f -> f.field("bar").order(SortOrder.Desc)))
        );
    }
}
