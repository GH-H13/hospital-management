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
 * 医生更新个人资料 Servlet
 */
@WebServlet("/doctor/updateProfile")
public class DoctorUpdateProfileServlet extends HttpServlet {

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

        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        // 更新数据库
        boolean success = doctorService.updateProfile(doctor.getDoctorId(), phone, email);

        if (success) {
            // 更新session中的doctor对象
            doctor.setPhone(phone);
            doctor.setEmail(email);
            request.getSession().setAttribute("doctor", doctor);
        }

        // 重定向回个人信息页面
        response.sendRedirect(request.getContextPath() + "/doctor/profile");
    }
}
