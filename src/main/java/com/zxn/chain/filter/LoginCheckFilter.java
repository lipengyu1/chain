//package com.zxn.chain.filter;//package com.lpy.news.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.zxn.chain.common.BaseContext;
//import com.zxn.chain.model.Response;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 检查用户是否已经完成登录
// */
//@Slf4j
//@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
//public class LoginCheckFilter implements Filter {
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //获取请求路径
//        String requestURI = request.getRequestURI();
//        log.info("拦截到请求：{}",requestURI);
//        //定义无需请求的路径
//        String[] urls = new String[]{
////                "/*"
//                "/back/register",
//                "/back/login",
//                "/back/sendMsg",
//                "/back/find",
//                "/user/logout",
//                "/storemanager/logout",
//                "/common/**",
//                "/doc.html",
//                "/webjars/**",
//                "/swagger-resources",
//                "/v2/api-docs"
//        };
//        //判断请求是否需要处理
//        boolean check = check(urls, requestURI);
//        //若不需处理，放行
//        if (check){
//            log.info("本次请求{}不需要处理",requestURI);
//            filterChain.doFilter(request,response);
//            return;
//        }
//        //判断后台登录状态
//        if (request.getSession().getAttribute("employee")!= null){
//            log.info("员工已登录,登录id为：{}",request.getSession().getAttribute("employee"));
////            long id = Thread.currentThread().getId();
////            log.info("线程id为：{}",id);
//            Long empId = (Long) request.getSession().getAttribute("employee");
//            //将id设置到threadlocal中
//            BaseContext.setCurrentId(empId);
//
//            filterChain.doFilter(request,response);
//            return;
//        }
//        //判断用户登录状态
//        if (request.getSession().getAttribute("user")!= null){
//            log.info("用户已登录,登录id为：{}",request.getSession().getAttribute("user"));
////            long id = Thread.currentThread().getId();
////            log.info("线程id为：{}",id);
//            Long userId = (Long) request.getSession().getAttribute("user");
//            //将id设置到threadlocal中
//            BaseContext.setCurrentId(userId);
//
//            filterChain.doFilter(request,response);
//            return;
//        }
//        //未登录返回未登录结果，通过输出流方式向客户端页面响应数据
//        log.info("用户未登录");
//        response.getWriter().write(JSON.toJSONString(Response.error("NOTLOGIN")));
//        return;
//    }
//    public boolean check(String[] urls, String requestURI){
//        for (String url:urls){
//            boolean match =  PATH_MATCHER.match(url,requestURI);
//            if (match){
//                return true;
//            }
//        }
//        return false;
//    }
//}
