package com.example.demo.controller;

import com.example.demo.utils.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class NeoController {


    @Autowired
    private Neo4jUtil neo4jUtil;


    @GetMapping("getRevenue")
    public String getRevenue(){
        String cql = "MATCH(n:MOVIE{original_title:'Toy Story'}) return n.revenue;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        return result.get(0).get("n.revenue").toString();
    }


    @GetMapping("insertCast")
    public void insertCast(){
        String cql = "create (c:CAST {id:000001, gender:2, name:'zhangsan'});";
        Neo4jUtil.add(cql);
    }


}
