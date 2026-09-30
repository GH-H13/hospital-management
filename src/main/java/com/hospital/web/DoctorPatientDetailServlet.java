package com.hospital.web;

import com.hospital.entity.Charge;
import com.hospital.entity.Patient;
import com.hospital.service.ChargeService;
import com.hospital.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 医生查看病人详情 Servlet
 */
@WebServlet("/doctor/patient/detail")
public class DoctorPatientDetailServlet extends HttpServlet {

    private PatientService patientService = new PatientService();
    private ChargeService chargeService = new ChargeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否以医生身份登录
        com.hospital.entity.Doctor doctor =
                (com.hospital.entity.Doctor) request.getSession().getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/doctor/main");
            return;
        }

        int patientId = Integer.parseInt(idStr);
        Patient patient = patientService.findById(patientId);

        // 确保该病人属于当前医生
        if (patient == null || !doctor.getDoctorId().equals(patient.getAttendingDoctorId())) {
            response.sendRedirect(request.getContextPath() + "/doctor/main");
            return;
        }

        // 查询该病人的收费记录
        List<Charge> charges = chargeService.findByPatientId(patientId);

        request.setAttribute("patient", patient);
        request.setAttribute("charges", charges);

        request.getRequestDispatcher("/WEB-INF/jsp/doctor/patient/detail.jsp")
                .forward(request, response);
    }
}
