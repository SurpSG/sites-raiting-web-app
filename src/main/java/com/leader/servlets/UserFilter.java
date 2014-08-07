package com.leader.servlets;

import com.leader.beans.UserBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ruki on 23.06.2014.
 */
@WebFilter("/*")
public class UserFilter implements Filter {

    public static final String SESSION_USER_ID_KEY = "id";
    public static final String REQUEST_USER_ID_KEY = "id";

    private static final int TTL = 60 * 60 * 24 * 7;// one week in seconds

    public void init(FilterConfig config) throws ServletException {}
    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("UserFilter");
        checkSession(request);

        chain.doFilter(req, resp);
    }

    private HttpSession initNewSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(TTL);

        String userIP = getClientIpAddr(request);

        UserBean user = new UserBean(userIP);

        session.setAttribute(SESSION_USER_ID_KEY,user.getId());
        request.setAttribute(""+REQUEST_USER_ID_KEY,user.getId());
        return session;
    }

    private void checkSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session == null){
            initNewSession(request);
        }else{
            request.setAttribute(""+REQUEST_USER_ID_KEY, Integer.parseInt(session.getAttribute(SESSION_USER_ID_KEY)+""));
        }
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
