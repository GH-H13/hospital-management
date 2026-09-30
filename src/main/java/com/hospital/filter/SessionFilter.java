package com.hospital.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 会话过滤器：统一验证登录状态
 * 未登录用户自动跳转到登录页
 */
@WebFilter("/*")
public class SessionFilter implements Filter {

    // 不需要登录即可访问的路径
    private static final String[] EXCLUDED_PATHS = {
            "/login",
            "/doctor/login",
            "/doctor/doLogin",
            "/doctor/logout",
            "/logout",
            "/css/",
            "/js/",
            "/images/",
            ".css",
            ".js",
            ".png",
            ".jpg",
            ".gif",
            ".ico"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化，无需特殊处理
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 统一设置请求和响应编码为 UTF-8，防止中文乱码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        // 检查是否为排除路径
        for (String excluded : EXCLUDED_PATHS) {
            if (path.startsWith(excluded) || path.endsWith(excluded)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 检查登录状态（管理员或医生）
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null &&
                (session.getAttribute("admin") != null || session.getAttribute("doctor") != null);

        if (isLoggedIn) {
            // 已登录，禁止浏览器缓存，然后放行
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            // 未登录，跳转至登录页
            response.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
        // 销毁，无需特殊处理
    }
}
