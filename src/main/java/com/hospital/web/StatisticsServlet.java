package com.hospital.web;

import com.hospital.entity.Patient;
import com.hospital.entity.Bed;
import com.hospital.service.PatientService;
import com.hospital.service.DoctorService;
import com.hospital.service.BedService;
import com.hospital.service.ChargeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 统计报表 Servlet
 */
@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    private PatientService patientService = new PatientService();
    private DoctorService doctorService = new DoctorService();
    private BedService bedService = new BedService();
    private ChargeService chargeService = new ChargeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 基础统计数据
        List<Patient> patients = patientService.findAll();
        List<com.hospital.entity.Doctor> doctors = doctorService.findAll();
        List<Bed> beds = bedService.findAll();
        List<com.hospital.entity.Charge> charges = chargeService.findAll();

        int patientCount = patients != null ? patients.size() : 0;
        int doctorCount = doctors != null ? doctors.size() : 0;
        int bedCount = beds != null ? beds.size() : 0;
        int chargeCount = charges != null ? charges.size() : 0;

        // 计算总收入
        double totalIncome = 0;
        if (charges != null) {
            for (com.hospital.entity.Charge charge : charges) {
                if (charge.getAmount() != null) {
                    totalIncome += charge.getAmount();
                }
            }
        }

        // 计算空闲床位数
        int freeBedCount = 0;
        if (beds != null) {
            for (Bed bed : beds) {
                if ("空闲".equals(bed.getUseStatus())) {
                    freeBedCount++;
                }
            }
        }

        // 统计各科室病人分布
        Map<String, Integer> patientDeptCount = new LinkedHashMap<>();
        if (patients != null) {
            for (Patient patient : patients) {
                String dept = patient.getDepartment();
                patientDeptCount.put(dept, patientDeptCount.getOrDefault(dept, 0) + 1);
            }
        }

        // 统计各科室床位情况
        Map<String, Integer> bedDeptTotal = new LinkedHashMap<>();
        Map<String, Integer> bedDeptUsed = new LinkedHashMap<>();
        if (beds != null) {
            for (Bed bed : beds) {
                String dept = bed.getDepartment();
                bedDeptTotal.put(dept, bedDeptTotal.getOrDefault(dept, 0) + 1);
                if ("占用".equals(bed.getUseStatus())) {
                    bedDeptUsed.put(dept, bedDeptUsed.getOrDefault(dept, 0) + 1);
                }
            }
        }

        // 计算各科室床位利用率
        Map<String, Double> bedUtilization = new LinkedHashMap<>();
        for (String dept : bedDeptTotal.keySet()) {
            int total = bedDeptTotal.get(dept);
            int used = bedDeptUsed.getOrDefault(dept, 0);
            double rate = total > 0 ? (double) used / total * 100 : 0;
            bedUtilization.put(dept, Math.round(rate * 100) / 100.0);
        }

        request.setAttribute("patientCount", patientCount);
        request.setAttribute("doctorCount", doctorCount);
        request.setAttribute("bedCount", bedCount);
        request.setAttribute("freeBedCount", freeBedCount);
        request.setAttribute("chargeCount", chargeCount);
        request.setAttribute("totalIncome", totalIncome);

        // 新增统计数据
        request.setAttribute("patientDeptCount", patientDeptCount);
        request.setAttribute("bedDeptTotal", bedDeptTotal);
        request.setAttribute("bedDeptUsed", bedDeptUsed);
        request.setAttribute("bedUtilization", bedUtilization);

        request.getRequestDispatcher("/WEB-INF/jsp/statistics.jsp").forward(request, response);
    }
}
