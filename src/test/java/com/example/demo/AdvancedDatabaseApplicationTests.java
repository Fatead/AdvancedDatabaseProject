package com.example.demo;

import com.example.demo.utils.Neo4jUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdvancedDatabaseApplicationTests {

    /**
     * 在links表中插入一条数据
     */
    @Test
    public void insertDataTest(){
        String cql = "create (c:CAST {id:000001, gender:2, name:'zhangsan'});";
        Neo4jUtil.add(cql);
    }



    /**
     * 查询影片"Toy Story"的revenue
     */
    @Test
    public void selectRevenueTest(){
        String cql = "MATCH(n:MOVIE{original_title:'Toy Story'}) return n.revenue;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        System.out.println(result.get(0).get("n.revenue"));
    }

}
