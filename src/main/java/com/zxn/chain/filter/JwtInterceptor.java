package com.zxn.chain.filter;


import com.zxn.chain.common.CustomException;
import com.zxn.chain.utils.JwtUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws CustomException {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //从 http 请求头中取出 token
        String token = request.getHeader("token");

        if (token == null) {
            throw new CustomException("无token,请重新登录");
        }

        //验证 token
        JwtUtils.checkSign(token);

        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtils.getUserId(token);
        System.out.println("id : " + userId);

        //获取 token 中的其他数据
        Map<String, Object> info = JwtUtils.getInfo(token);
        info.forEach((k, v) -> System.out.println(k + ":" + v));
        return true;
    }
}


