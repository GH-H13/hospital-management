package com.hospital.web;

import com.hospital.entity.Admin;
import com.hospital.service.AdminService;
import com.hospital.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理员修改密码 Servlet
 */
@WebServlet("/admin/changePassword")
public class AdminChangePasswordServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 显示修改密码页面
        request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String adminName = (String) session.getAttribute("adminName");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // 验证输入
        if (oldPassword == null || newPassword == null || confirmPassword == null ||
            oldPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "所有密码字段都不能为空");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "新密码和确认密码不一致");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "新密码长度不能少于6位");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
            return;
        }

        // 修改密码
        boolean success = adminService.changePassword(adminName, oldPassword, newPassword);

        if (success) {
            request.setAttribute("message", "密码修改成功");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "旧密码错误，修改失败");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/changePassword.jsp").forward(request, response);
        }
    }
}
