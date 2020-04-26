package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    @Autowired
    RestHighLevelClient client;


    /**
     * 搜索type下的全部记录
     */
    @Test
    public void testSearchAll() throws IOException {
        //获取搜索请求对象，并且设置类型
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置搜索方式
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //配置source源字段过虑，1显示的，2排除的
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"}, new String[]{});

        //将搜索源配置到搜索请求中，执行搜索，获取搜索响应结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        //获取所有搜索结果、总匹配数量
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();

        //筛选出匹配度高的文档记录
        SearchHit[] searchHits = hits.getHits();

        //遍历结果
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void testSearchPage() throws IOException {
        //获取搜索请求对象，并且设置类型
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置搜索方式
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //ES这里是按起始坐标来实现分页查询,所以我们要指定一个页码
        int pageNum = 1;
        int size = 2;
        //通过页码和查询数量得出起始位置
        int fromNum = (pageNum - 1) * size;
        //分页查询，设置起始下标，从0开始
        searchSourceBuilder.from(0);
        //每页显示个数
        searchSourceBuilder.size(size);
        //配置source源字段过虑，1显示的，2排除的
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"}, new String[]{});

        //将搜索源配置到搜索请求中，执行搜索，获取搜索响应结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        //获取所有搜索结果、总匹配数量
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();

        //筛选出匹配度高的文档记录
        SearchHit[] searchHits = hits.getHits();

        //遍历结果
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    /**
     * Term Query 精确查询
     */
    @Test
    public void TestSearchTermQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置查询类型为termQuery,精确匹配name中包含spring的文档
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring"));//source源字段过虑

        //不指定过滤条件则默认显示查询到的文档的所有字段
        searchSourceBuilder.fetchSource(new String[]{}, new String[]{});

        //设置搜索源并获取搜索结果
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest);

        //获取搜索结果
        //  .getHits() 取本次所有匹配结果
        //  .getHits().getHits() 筛选出匹配度高的文档记录
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        //筛选匹配度最高的结果
        SearchHit[] searchHits = hits.getHits();

        System.out.println("结果数量为: " + totalHits);
        //输出搜索结果
        for(SearchHit hit : searchHits){
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 根据关键字搜索
     * @throws IOException
     */
    @Test
    public void testMatchQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name"}, new String[]{});
        //设置过滤器，匹配关键字
        searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发").operator(Operator.OR));
        searchRequest.source(searchSourceBuilder);

        //执行搜索请求
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        //输出搜索结果
        for(SearchHit hit : searchHits){
            System.out.println(hit.getSourceAsMap());
        }
    }
}
