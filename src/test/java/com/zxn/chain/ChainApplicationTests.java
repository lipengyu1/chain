package com.zxn.chain;

import com.zxn.chain.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ChainApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getTokenTest(){
        Map<String, Object> info = new HashMap<>();
        //生成token
        String id = "187813355694720001";
        Long userId = Long.valueOf(id);
        String token = JwtUtils.sign(userId, info);
        System.out.println(token);;
    }
    @Test
    void getTokenTest2(){
        Map<String, Object> info = new HashMap<>();
        //生成token
        String member= "1004";
//        String member= "1001";
        Long memberNum = Long.valueOf(member);
//        Long memberNum = null;
        String token = JwtUtils.sign(memberNum, info);
        System.out.println(token);;
    }

}
