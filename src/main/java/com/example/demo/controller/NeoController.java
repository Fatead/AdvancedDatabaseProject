package com.example.demo.controller;

import com.example.demo.utils.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NeoController {


    @Autowired
    private Neo4jUtil neo4jUtil;


    /**
     * 根据电影名得到票房信息
     */
    @GetMapping("getRevenue")
    public String getRevenue(){
        String cql = "MATCH(n:MOVIE{original_title:'Toy Story'}) return n.revenue;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        return result.get(0).get("n.revenue").toString();
    }


    /**
     * 插入一条cast数据
     */
    @GetMapping("insertCast")
    public void insertCast(){
        String cql = "create (c:CAST {id:000001, gender:2, name:'zhangsan'});";
        Neo4jUtil.add(cql);
    }

    /**
     * 更新cast的信息
     */
    @GetMapping("updateCast")
    public void updateCast(){
        String cql = "MATCH (c:CAST {name:'zhangsan'}) SET c={gender :1};";
        Neo4jUtil.add(cql);
    }


    /**
     * 删除一条cast数据
     */
    @GetMapping("deleteCast")
    public void deleteCast(){
        String cql = "MATCH (c:CAST {name:'zhangsan'}) DELETE c;";
        Neo4jUtil.add(cql);
    }


    /**
     * 查询revenue和budget相差不超过300000的影片名称
     */
    @GetMapping("getRevenueInCondition")
    public String getMovieInCondition(){
        String cql = "MATCH(n:MOVIE) where n.revenue-n.budget<300000 OR n.budget-n.revenue<300000 return n.original_title;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        List<String> movieTitle = new ArrayList<>();
        for(Map<String,Object> map:result){
            movieTitle.add(map.get("n.original_title").toString());
        }
        return movieTitle.toString();
    }

    /**
     * 查询影片《 Toy Story 》的rating
     */
    @GetMapping("getRating")
    public String getRating(){
        String cql = "MATCH (u:USER) - [rel: RATE] -> (n:MOVIE{original_title :'Toy Story'}) return rel.rating;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        List<String> ratings = new ArrayList<>();
        for(Map<String,Object> map:result){
            ratings.add(map.get("rel.rating").toString());
        }
        return ratings.toString();
    }


    /**
     * 查询演员id=2216的演员所参演的全部影片名称
     */
    @GetMapping("getMovieByPlayer")
    public String getMovieByPlayer(){
        String cql = "MATCH(c:CAST)-[rel:ACTED_IN]->(m:MOVIE) WHERE id(c)=2747 return m.original_title;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        List<String> movieName = new ArrayList<>();
        for(Map<String,Object> map:result){
            movieName.add(map.get("m.original_title").toString());
        }
        return movieName.toString();
    }

    /**
     * 查询电影的全部genres数
     */
    @GetMapping("getGenresNums")
    public String getGenresNums(){
        String cql = "MATCH (g:GENRE)return count(g);";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        return result.get(0).toString();
    }


    /**
     * 查询revenueTOP10影片
     */
    @GetMapping("getRevenueTop")
    public String getRevenueTop(){
        String cql = "MATCH (n:MOVIE) RETURN n.original_title ORDER BY n.revenue DESC LIMIT 10;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        List<String> movieName = new ArrayList<>();
        for(Map<String,Object> map:result){
            movieName.add(map.get("m.original_title").toString());
        }
        return movieName.toString();
    }

    /**
     * 查询revenueTOP10影片中工作人员job为Director的人员Id和name
     */
    @GetMapping("getDirector")
    public String getDirector(){
        String cql = "MATCH (c:CREW)-[rel: WORKED_IN {job:'Director'}]->(m:MOVIE) RETURN id(c), c.name ORDER BY m.revenue DESC LIMIT 10;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        return result.toString();
    }

    /**
     * 查询参演影片票房之和排TOP10的cast的id
     */
    @GetMapping("getTopCast")
    public String getTopCast(){
        String cql = "MATCH (c:CAST)-[rel: ACTED_IN]->(m:MOVIE) RETURN id(c), sum(m.revenue) as revenue ORDER BY revenue DESC LIMIT 10;";
        List<Map<String, Object>> result =  Neo4jUtil.getFields(cql);
        return result.toString();
    }


}
