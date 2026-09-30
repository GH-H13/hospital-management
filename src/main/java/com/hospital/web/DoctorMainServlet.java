package com.hospital.web;

import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import com.hospital.service.ChargeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 医生首页 Servlet
 */
@WebServlet("/doctor/main")
public class DoctorMainServlet extends HttpServlet {

    private PatientService patientService = new PatientService();
    private ChargeService chargeService = new ChargeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否以医生身份登录
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        // 查询该医生的病人
        List<Patient> patients = patientService.findByAttendingDoctor(doctor.getDoctorId());
        int patientCount = patients != null ? patients.size() : 0;

        // 计算该医生病人的总费用
        double totalAmount = 0;
        int totalCharges = 0;
        if (patients != null) {
            for (Patient p : patients) {
                List<com.hospital.entity.Charge> charges = chargeService.findByPatientId(p.getPatientId());
                if (charges != null) {
                    totalCharges += charges.size();
                    for (com.hospital.entity.Charge c : charges) {
                        totalAmount += c.getAmount();
                    }
                }
            }
        }

        request.setAttribute("patients", patients);
        request.setAttribute("patientCount", patientCount);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("totalCharges", totalCharges);
        request.setAttribute("doctor", doctor);

        request.getRequestDispatcher("/WEB-INF/jsp/doctor/main.jsp").forward(request, response);
    }
}
