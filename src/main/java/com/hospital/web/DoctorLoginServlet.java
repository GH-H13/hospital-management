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
 * 医生登录 Servlet
 */
@WebServlet({"/doctor/login", "/doctor/doLogin"})
public class DoctorLoginServlet extends HttpServlet {

    private DoctorService doctorService = new DoctorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 显示登录页
        request.getRequestDispatcher("/WEB-INF/jsp/doctor/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String doctorIdStr = request.getParameter("doctorId");
        String password = request.getParameter("password");

        if (doctorIdStr == null || password == null ||
            doctorIdStr.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "医生ID和密码不能为空");
            request.getRequestDispatcher("/WEB-INF/jsp/doctor/login.jsp").forward(request, response);
            return;
        }

        try {
            Integer doctorId = Integer.parseInt(doctorIdStr);
            Doctor doctor = doctorService.login(doctorId, password);

            if (doctor != null) {
                // 登录成功
                request.getSession().setAttribute("doctor", doctor);
                request.getSession().setAttribute("doctorId", doctor.getDoctorId());
                request.getSession().setAttribute("doctorName", doctor.getDoctorName());
                response.sendRedirect(request.getContextPath() + "/doctor/main");
            } else {
                request.setAttribute("error", "医生ID或密码错误");
                request.getRequestDispatcher("/WEB-INF/jsp/doctor/login.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "医生ID必须是数字");
            request.getRequestDispatcher("/WEB-INF/jsp/doctor/login.jsp").forward(request, response);
        }
    }
}
