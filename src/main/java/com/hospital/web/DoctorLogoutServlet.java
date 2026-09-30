package com.hospital.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 医生退出登录 Servlet
 */
@WebServlet("/doctor/logout")
public class DoctorLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 禁止浏览器缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 销毁 session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 跳转到医生登录页面
        response.sendRedirect(request.getContextPath() + "/doctor/login");
    }
}
