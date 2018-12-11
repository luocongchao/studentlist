package com.example.springboot.common;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    // 日志对象
    private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);
    private static SessionManage sessionManage = new SessionManage();

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/user/login", "/user/register","/img"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        // session中包含user对象,则是登录状态
        String session = request.getHeader("sessionToken");
        String session2 = request.getHeader("sessiontoken");
        System.out.println("filter url:" + uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);

        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            chain.doFilter(req, res);
        } else { //需要过滤器
            //OPTIONS请求不拦截
            if(request.getMethod().equals("OPTIONS")){
                chain.doFilter(req, res);
            }else{
                if (sessionManage.getUserBySessionToken(session)!=null) {
                    chain.doFilter(request, response);
                } else {
                    request.getRequestDispatcher("/user/noLogin").forward(request, response);
                }
            }
        }
    }


    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if (uri.startsWith(includeUrl)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
