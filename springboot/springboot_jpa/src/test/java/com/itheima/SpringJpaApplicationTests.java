package com.itheima;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJpaApplication.class)
public class SpringJpaApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void userTest() {
        List<User> users = userDao.findAll();
        System.out.println(users);
    }

    @Test
    public void testRedis() throws JsonProcessingException {
        // 从redis中获取数据，使用key=user.findAll查询
        String userData = redisTemplate.boundValueOps("user.findAll").get();
        // 如果没有查询到数据，就从数据库中查询，将查询的结果存放到redis中
        if (userData==null) {
            List<User> list = userDao.findAll();
            // 需要将List集合转换成json的字符串（借助Jackson）
            ObjectMapper objectMapper = new ObjectMapper();
            userData = objectMapper.writeValueAsString(list);
            //查询结果放入redis
            redisTemplate.boundValueOps("user.findAll").set(userData);
            System.out.println("从数据库中获取数据");
        }else{
            System.out.println("redis中获取数据");
        }
        System.out.println("获取数据为:"+userData);
    }
}

