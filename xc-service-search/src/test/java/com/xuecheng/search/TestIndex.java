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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
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
public class TestIndex {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    RestClient restClient;

    /**
     * 创建索引库
     * @throws IOException
     */
    @Test
    public void testCreateIndex() throws IOException {
        //创建索引请求对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");

        //设置索引参数
        createIndexRequest.settings(Settings.builder()
                .put("number_of_shards",1)
                .put("number_of_replicas",0)
        );

        //设置索引库的映射
        createIndexRequest.mapping("doc","{\n" +
                "    \"properties\": {\n" +
                "        \"name\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\",\n" +
                "            \"search_analyzer\": \"ik_smart\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\",\n" +
                "            \"search_analyzer\": \"ik_smart\"\n" +
                "        },\n" +
                "        \"studymodel\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"price\": {\n" +
                "            \"type\": \"float\"\n" +
                "        },\n" +
                "        \"timestamp\": {\n" +
                "            \"type\": \"date\",\n" +
                "            \"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
                "        }\n" +
                "    }\n" +
                "}",XContentType.JSON);
        //操作客户端
        IndicesClient indices = restHighLevelClient.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应结果
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }

    /**
     * 添加文档
     */
    @Test
    public void testAddDoc() throws IOException {
        //准备json数据
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "spring cloud实战");
        jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
        jsonMap.put("studymodel", "201001");
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
        jsonMap.put("timestamp", dateFormat.format(new Date()));
        jsonMap.put("price", 5.6f);
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest("xc_course","doc");
        //指定索引文档内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
        //获取响应结果
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
    }

    /**
     * 查询文档
     */
    @Test
    public void getDoc() throws IOException {
        GetRequest getRequest = new GetRequest(
                "xc_course",
                "doc",
                "eWb_kXEB39nW0xAzs9Pd"
        );
        GetResponse getResponse = restHighLevelClient.get(getRequest);
        boolean exists = getResponse.isExists();
        if(!exists){
            System.out.println("文档不存在");
        }else{
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    /**
     * 更新文档
     */
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("xc_course", "doc","eWb_kXEB39nW0xAzs9Pd");
        Map<String, String> map = new HashMap<>();
        map.put("name", "spring cloud alibaba");
        updateRequest.doc(map);
        UpdateResponse update = restHighLevelClient.update(updateRequest);
        RestStatus status = update.status();
        System.out.println(status);
    }

    /**
     * 删除文档
     */
    @Test
    public void testDelDoc() throws IOException {
        //删除文档id
        String id = "eWb_kXEB39nW0xAzs9Pd";
        //删除索引请求对象
        DeleteRequest deleteRequest = new DeleteRequest("xc_course","doc", id);
        //响应对象
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        //获取响应结果
        DocWriteResponse.Result result = deleteResponse.getResult();

        System.out.println(result);
    }


}
