package com.hospital.web;

import com.hospital.entity.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 医生个人信息 Servlet
 */
@WebServlet("/doctor/profile")
public class DoctorProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否以医生身份登录
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        request.setAttribute("doctor", doctor);
        request.getRequestDispatcher("/WEB-INF/jsp/doctor/profile.jsp").forward(request, response);
    }
}
