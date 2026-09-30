package com.hospital.web;

import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 医生管理 Servlet
 */
@WebServlet("/doctor/*")
public class DoctorServlet extends HttpServlet {
    
    private DoctorService doctorService = new DoctorService();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            listDoctors(request, response);
        } else if (pathInfo.equals("/add")) {
            showAddForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deleteDoctor(request, response);
        } else {
            listDoctors(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/add")) {
            addDoctor(request, response);
        } else if (pathInfo.equals("/edit")) {
            updateDoctor(request, response);
        }
    }
    
    private void listDoctors(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Doctor> doctors;
        if (keyword != null && !keyword.trim().isEmpty()) {
            doctors = doctorService.findByName(keyword);
        } else {
            doctors = doctorService.findAll();
        }
        request.setAttribute("doctors", doctors);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/WEB-INF/jsp/doctor/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 传递下拉选项
        request.setAttribute("titles", new String[]{"主任医师", "副主任医师", "主治医师", "医师", "住院医师"});
        request.setAttribute("duties", new String[]{"科主任", "科副主任", ""});
        request.setAttribute("departments", new String[]{"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"});
        request.getRequestDispatcher("/WEB-INF/jsp/doctor/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            Doctor doctor = doctorService.findById(id);
            if (doctor != null) {
                request.setAttribute("doctor", doctor);
                // 传递下拉选项
                request.setAttribute("titles", new String[]{"主任医师", "副主任医师", "主治医师", "医师", "住院医师"});
                request.setAttribute("duties", new String[]{"科主任", "科副主任", ""});
                request.setAttribute("departments", new String[]{"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"});

                // 格式化日期供表单使用
                if (doctor.getBirthDate() != null) {
                    doctor.setBirthDateStr(sdf.format(doctor.getBirthDate()));
                }
                if (doctor.getWorkDate() != null) {
                    doctor.setWorkDateStr(sdf.format(doctor.getWorkDate()));
                }

                request.getRequestDispatcher("/WEB-INF/jsp/doctor/edit.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/doctor/list");
    }
    
    private void addDoctor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Doctor doctor = new Doctor();
            doctor.setDoctorName(request.getParameter("doctorName"));
            doctor.setGender(request.getParameter("gender"));
            doctor.setTitle(request.getParameter("title"));
            doctor.setDuty(request.getParameter("duty"));
            doctor.setDepartment(request.getParameter("department"));
            
            String birthDateStr = request.getParameter("birthDate");
            if (birthDateStr != null && !birthDateStr.trim().isEmpty()) {
                doctor.setBirthDate(sdf.parse(birthDateStr));
            }
            
            String workDateStr = request.getParameter("workDate");
            if (workDateStr != null && !workDateStr.trim().isEmpty()) {
                doctor.setWorkDate(sdf.parse(workDateStr));
            }
            
            doctor.setPassword(request.getParameter("password"));
            
            boolean success = doctorService.add(doctor);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/doctor/list");
            } else {
                request.setAttribute("error", "添加失败");
                request.getRequestDispatcher("/WEB-INF/jsp/doctor/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/doctor/add.jsp").forward(request, response);
        }
    }
    
    private void updateDoctor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(Integer.parseInt(request.getParameter("doctorId")));
            doctor.setDoctorName(request.getParameter("doctorName"));
            doctor.setGender(request.getParameter("gender"));
            doctor.setTitle(request.getParameter("title"));
            doctor.setDuty(request.getParameter("duty"));
            doctor.setDepartment(request.getParameter("department"));
            
            String birthDateStr = request.getParameter("birthDate");
            if (birthDateStr != null && !birthDateStr.trim().isEmpty()) {
                doctor.setBirthDate(sdf.parse(birthDateStr));
            }
            
            String workDateStr = request.getParameter("workDate");
            if (workDateStr != null && !workDateStr.trim().isEmpty()) {
                doctor.setWorkDate(sdf.parse(workDateStr));
            }
            
            String password = request.getParameter("password");
            // 如果密码为空，则保持原密码不变
            if (password == null || password.trim().isEmpty()) {
                Doctor originalDoctor = doctorService.findById(doctor.getDoctorId());
                doctor.setPassword(originalDoctor.getPassword());
            } else {
                doctor.setPassword(password);
            }
            
            boolean success = doctorService.update(doctor);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/doctor/list");
            } else {
                request.setAttribute("error", "更新失败");
                request.getRequestDispatcher("/WEB-INF/jsp/doctor/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/doctor/edit.jsp").forward(request, response);
        }
    }
    
    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            doctorService.delete(id);
        }
        response.sendRedirect(request.getContextPath() + "/doctor/list");
    }
}
