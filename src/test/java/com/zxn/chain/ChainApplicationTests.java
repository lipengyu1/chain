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
        info.put("username", "admin");
        info.put("pass", "123456");
        //生成token
        String id = "186018293096776702";
        Long userId = Long.valueOf(id);
        String token = JwtUtils.sign(userId, info);
        System.out.println(token);;
    }

}
