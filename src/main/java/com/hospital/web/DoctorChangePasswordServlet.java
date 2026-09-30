package com.hospital.web;

import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 医生修改密码 Servlet
 */
@WebServlet("/doctor/changePassword")
public class DoctorChangePasswordServlet extends HttpServlet {

    private DoctorService doctorService = new DoctorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否以医生身份登录
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // 验证当前密码
        if (!doctor.getPassword().equals(oldPassword)) {
            request.getSession().setAttribute("passwordError", "当前密码错误！");
            response.sendRedirect(request.getContextPath() + "/doctor/profile");
            return;
        }

        // 更新密码
        boolean success = doctorService.updatePassword(doctor.getDoctorId(), newPassword);

        if (success) {
            // 更新session中的doctor对象
            doctor.setPassword(newPassword);
            request.getSession().setAttribute("doctor", doctor);
            request.getSession().setAttribute("passwordSuccess", "密码修改成功！下次登录请使用新密码。");
        } else {
            request.getSession().setAttribute("passwordError", "密码修改失败，请稍后重试！");
        }

        response.sendRedirect(request.getContextPath() + "/doctor/profile");
    }
}
