package cn.plantlink.service;

import cn.plantlink.common.BusinessConstants;
import cn.plantlink.common.InfoSearchType;
import cn.plantlink.pojo.Info;
import cn.plantlink.pojo.Product;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
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

    // ---------- product start ----------
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
                        .id(productId)),
                Product.class);

        return get.source();
    }

    public String addProduct(Product product) throws Exception {

        IndexResponse create = elasticsearchClient.index(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                .document(product)
                .refresh(Refresh.True)
        );

//        CreateResponse create = elasticsearchClient.create(b -> b
//                .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
//                .document(product)
//                .id("product-id-" + RandomUtils.nextLong())
//                .refresh(Refresh.True)
//        );

        return create.id();
    }

    public String modifyProduct(Product product, String productId) throws Exception {

        UpdateResponse<Product> update = elasticsearchClient.update(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                        .id(productId)
                        .doc(product),
                Product.class);

        return update.id();
    }

    public String deleteProduct(String productId) throws Exception {

        DeleteResponse delete = elasticsearchClient.delete(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_PRODUCT)
                .id(productId));

        return delete.id();
    }

    // ---------- product end ----------


    // ---------- info start ----------

    public List<Info> getInfoList() throws Exception {

//        SearchResponse<Info> search = elasticsearchClient.search(s -> s
//                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
//                        .query(q -> q
//                                .range(r -> r
//                                        .field("releaseTime")
//                                        .gt(JsonData.of("now-10d/d"))
//                                        .lt(JsonData.of("2022-01-27 20:08:10"))
//                                        .format("yyyy-MM-dd HH:mm:ss")
//                                        .timeZone("+08:00")
//                                )),
//                Info.class);

//        SearchResponse<Info> search = elasticsearchClient.search(s -> s
//                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
//                        .query(q -> q
//                                .range(r -> r
//                                        .field("releaseTime")
//                                        .gt(JsonData.of("now-1d/d"))
//                                        .lt(JsonData.of("now"))
//                                )),
//                Info.class);

//        SearchResponse<Info> search = elasticsearchClient.search(s -> s
//                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
//                        .query(q -> q
//                                .bool(b -> b
//                                    .must(m -> m
//                                            .terms(t -> t
//                                                    .field("type")
//                                                    .terms(tf -> tf
//                                                        .value(Arrays.asList(FieldValue.of(3), FieldValue.of(2)))
//                                                    )
//                                            )
//                                    )
//                                )
//                        ),
//                Info.class);

        String keyword = "喜欢";
        List<FieldValue> types = Arrays.asList(InfoSearchType.COLUMN, InfoSearchType.USER_ARTICLE)
                .stream().map(p -> FieldValue.of(p.getValue())).collect(Collectors.toList());

        SearchResponse<Info> search = elasticsearchClient.search(s -> s
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                        .query(q -> q
                                .functionScore(f -> f
                                        .query(qy -> qy
                                                .bool(b -> b
                                                        .must(Query.of(mq -> mq
                                                                        .terms(t -> t
                                                                                .field("type")
                                                                                .terms(tf -> tf.value(types)))),
                                                                Query.of(mq -> mq
                                                                        .multiMatch(mm -> mm.query(keyword)
                                                                                .fields("title", "content", "stockCodes",
                                                                                        "stockNames", "stockSpellCodes"))))
                                                        .boost(1.0f)))


//                                        .functions(fn -> fn.fieldValueFactor(ff -> ff
//                                                .field("likeQuantity")
//                                                .factor(1.2)
//                                                .modifier(FieldValueFactorModifier.Log1p)
//                                                .missing(1.0)
//                                        ).weight(1.0))


//                                        .functions(fs -> fs
//                                                .scriptScore(sf -> sf
//                                                        .script(ss -> ss
//                                                                .inline(i -> i
//                                                                        .source("1.0")))
//                                                ).weight(1.0))


//                                        .functions(FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .terms(t -> t
//                                                                        .field("type")
//                                                                        .terms(tf -> tf.value(types))))
//                                                        .weight(1.0).new ContainerBuilder()),
//                                                FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .match(m -> m.field("title")
//                                                                        .query(FieldValue.of(keyword))))
//                                                        .weight(100.0).new ContainerBuilder()),
//                                                FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .match(m -> m.field("stockCodes")
//                                                                        .query(FieldValue.of(keyword))))
//                                                        .weight(10.0).new ContainerBuilder()),
//                                                FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .match(m -> m.field("stockNames")
//                                                                        .query(FieldValue.of(keyword))))
//                                                        .weight(10.0).new ContainerBuilder()),
//                                                FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .match(m -> m.field("stockSpellCodes")
//                                                                        .query(FieldValue.of(keyword))))
//                                                        .weight(10.0).new ContainerBuilder()),
//                                                FunctionScore.of(fs -> fs
//                                                        .filter(ff -> ff
//                                                                .match(m -> m.field("content")
//                                                                        .query(FieldValue.of(keyword))))
//                                                        .weight(5.0).new ContainerBuilder()))


                                        .functions(FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .terms(t -> t
                                                                        .field("type")
                                                                        .terms(tf -> tf.value(types))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(1.0)),
                                                FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .match(m -> m.field("title")
                                                                        .query(FieldValue.of(keyword))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(100.0)),
                                                FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .match(m -> m.field("stockCodes")
                                                                        .query(FieldValue.of(keyword))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(10.0)),
                                                FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .match(m -> m.field("stockNames")
                                                                        .query(FieldValue.of(keyword))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(10.0)),
                                                FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .match(m -> m.field("stockSpellCodes")
                                                                        .query(FieldValue.of(keyword))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(10.0)),
                                                FunctionScore.of(fs -> fs
                                                        .filter(ff -> ff
                                                                .match(m -> m.field("content")
                                                                        .query(FieldValue.of(keyword))))
                                                        .scriptScore(sf -> sf
                                                                .script(ss -> ss
                                                                        .inline(i -> i
                                                                                .source("1.0"))))
                                                        .weight(5.0)))


                                        .scoreMode(FunctionScoreMode.Sum)
                                        .boostMode(FunctionBoostMode.Multiply)
                                        .maxBoost(130.0)
                                        .boost(1.0f)))
                        .sort(SortOptions.of(so -> so
                                        .field(fs -> fs
                                                .field("_score")
                                                .order(SortOrder.Desc))),
                                SortOptions.of(so -> so
                                        .field(fs -> fs
                                                .field("releaseTime")
                                                .order(SortOrder.Desc))))
                        .from(BusinessConstants.PAGINATION_PAGE_NO_DEFAULT - 1)
                        .size(BusinessConstants.PAGINATION_PAGE_SIZE_DEFAULT - 1),
                Info.class);

        return search.hits().hits().stream().map(p -> p.source()).collect(Collectors.toList());
    }

    public Info getInfo(String infoId) throws Exception {

        GetResponse<Info> get = elasticsearchClient.get(GetRequest.of(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                        .id(infoId)),
                Info.class);

        return get.source();
    }

    public String addInfo() throws Exception {

        Info info = new Info();
        info.setContentId("00006214238e41b4a17188b95d2571d5");
        info.setType(InfoSearchType.COLUMN.getValue());
        info.setTitle("同学老是喜欢说教别人");
        info.setContent("<p style=\"word-break: break-all; text-indent: 0px; font-family: 思源黑体 CN Regular; font-size: 16px; line-height: 30px; margin: 20px 0px 20px 0px;\">&nbsp; &nbsp; &nbsp;首先他开了一个小公司，我借钱给他，他忙不过来，叫我回来帮忙，回来一个月，发现他说教的毛病严重，经常看他从抖音，电视剧里面学各种鸡汤。</p>\n" +
                "<div>&nbsp; &nbsp; &nbsp; 他喜欢吃猪脑汤，我不喜欢吃，没吃完被他说一顿，他说:瘦的人吃猪脑会有恶心的感觉，营养都在猪脑里，傻！点菜的时候都没问我喜欢吃什么，非要跟他一样喜欢吃猪脑，我忍了，不说话。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; &nbsp; 叫我用货车练车的时候说一大堆，刚练一两天就要达到他水平的意思，不满意就对我大吼大叫，这次跟他吵了起来，回来不说话，关灯睡觉。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; 走在路上时，太阳大，我有点眯眼睛，他又说:你走路颠簸颠簸的，要这样子，这样子才好，走路不要眯眼睛，这样太阳才能照到眼睛，黑的部分跟黑，白的更白，我走在他后面不说话。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; 叫我去他家的时候，我没有随手关门的，又被他说一顿，语气还很难听，说:你怎么这样子，在别人家要注意习俗，要随手关门，要小声，看他自大的样子，说了一大堆，从五楼说到二楼，他妈妈也在，心里苦了一逼，心想:是你叫我来你家，能不能尊重一下我，对我大呼小叫，要我服从你们家？在他家感觉就是他家的下人。&nbsp; &nbsp;&nbsp;</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; 第二天去跟他见客户的时候，唠唠叨叨又说:见人不要怕死，人还没去，心里已经紧张要死，这个没必要，该做什么就做什么，要大胆，你试过台下上百人的的不，跟他杠上了，我说:我没有你说的这些问题，你说这些干什么，他说:说了你都不一定能进化。</div>\n" +
                "<div>&nbsp; &nbsp; 他还说:我们市里面的女人都不能要，有点颠，会说我们家族各种不好。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; 在他自大，目中无人喜欢教化别人之下，感觉他疯了，还是我自己快疯了，他说教的毛病一来，我就生气，说我各种不好，这儿，那儿，天天唠唠叨叨说个不停，没有一样合他眼。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; &nbsp;他说给我股份跟他一起创业，借给他的钱当做股份，是什么股份对我来说不重要，想跟他远离，受不了他那种气，不然必成噩梦。</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp; &nbsp;</div>\n" +
                "<div>&nbsp; &nbsp; &nbsp;</div>\n" +
                "<div>&nbsp;</div>");
        info.setNickname("refresh");
        info.setAuthorId("0f511f3e96bd4c228c245b1d7d745758");
        info.setCreateTime(new Date());
        info.setReleaseTime(new Date());
        info.setStockCodes(Arrays.asList("万科A", "平安银行"));
        info.setStockNames(Arrays.asList("000002.SZ", "000001.SZ"));
        info.setStockSpellCodes(Arrays.asList("wankeA,wkA", "pinganyinhang,payh"));
        info.setTags(Arrays.asList("专栏", "港股"));
        info.setIsHide(0);

        return addInfo(info);
    }

    public String addInfo(Info info) throws Exception {

        CreateResponse create = elasticsearchClient.create(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                .document(info)
                .id(info.getType() + ":" + info.getContentId())
                .refresh(Refresh.True)
        );

        return create.id();
    }

    public String modifyInfo() throws Exception {

        Info info = new Info();
        info.setTitle("同学老是喜欢说教别人改个标题2");
        info.setReleaseTime(new Date());

        return modifyInfo(info, "1:00006214238e41b4a17188b95d2571d5");
    }

    public String modifyInfo(Info info, String infoId) throws Exception {

        UpdateResponse<Info> update = elasticsearchClient.update(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                        .id(infoId)
                        .doc(info),
                Info.class);

        return update.id();
    }

    public String upsert() throws Exception {

        Info info = new Info();
        info.setTitle("同学老是喜欢说教别人改个标题3");
        info.setReleaseTime(new Date());
        upsert(info, "1:00006214238e41b4a17188b95d2571d5");

        Info info2 = new Info();
        info2.setIsHide(1);
        info2.setReleaseTime(new Date());

        return upsert(info2, "1:00006214238e41b4a17188b95d2571d5");
    }

    public String upsert(Info info, String infoId) throws Exception {

        UpdateResponse<Info> update = elasticsearchClient.update(b -> b
                        .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                        .id(infoId)
                        .docAsUpsert(true)
                        .doc(info),
                Info.class);

        return update.id();
    }

    public String deleteInfo(String infoId) throws Exception {

        DeleteResponse delete = elasticsearchClient.delete(b -> b
                .index(BusinessConstants.ELASTICSEARCH_INDEX_INFO)
                .id(infoId));

        return delete.id();
    }

    // ---------- info end ----------
}
