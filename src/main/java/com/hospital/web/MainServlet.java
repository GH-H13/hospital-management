package com.hospital.web;

import com.hospital.entity.Patient;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 主页面 Servlet - 首页仪表盘
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private PatientService patientService = new PatientService();
    private DoctorService doctorService = new DoctorService();
    private BedService bedService = new BedService();
    private ChargeService chargeService = new ChargeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否登录
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 统计数据
        List<Patient> patients = patientService.findAll();
        List<com.hospital.entity.Doctor> doctors = doctorService.findAll();
        List<com.hospital.entity.Bed> beds = bedService.findAll();
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
            for (com.hospital.entity.Bed bed : beds) {
                if ("空闲".equals(bed.getUseStatus())) {
                    freeBedCount++;
                }
            }
        }

        request.setAttribute("patientCount", patientCount);
        request.setAttribute("doctorCount", doctorCount);
        request.setAttribute("bedCount", bedCount);
        request.setAttribute("freeBedCount", freeBedCount);
        request.setAttribute("chargeCount", chargeCount);
        request.setAttribute("totalIncome", totalIncome);

        // 仪表盘数据（基于现有表字段，不新增字段）
        // 1. 最近入院病人：按入院日期倒序，取前5条
        List<Patient> recentPatients = new ArrayList<>(patients != null ? patients : new ArrayList<>());
        recentPatients.sort((p1, p2) -> {
            Date d1 = p1.getAdmissionDate();
            Date d2 = p2.getAdmissionDate();
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;
            return d2.compareTo(d1);
        });
        if (recentPatients.size() > 5) {
            recentPatients = recentPatients.subList(0, 5);
        }
        request.setAttribute("recentPatients", recentPatients);

        // 2. 今日收入：按收费日期判断
        double todayIncome = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        if (charges != null) {
            for (com.hospital.entity.Charge charge : charges) {
                if (charge.getChargeDate() != null && today.equals(sdf.format(charge.getChargeDate()))) {
                    if (charge.getAmount() != null) {
                        todayIncome += charge.getAmount();
                    }
                }
            }
        }
        request.setAttribute("todayIncome", todayIncome);

        // 3. 床位使用概况：各科室总床位、已占用、使用率
        Map<String, int[]> bedStatsByDept = new LinkedHashMap<>(); // dept -> [total, used]
        if (beds != null) {
            for (com.hospital.entity.Bed bed : beds) {
                String dept = bed.getDepartment();
                int[] stats = bedStatsByDept.getOrDefault(dept, new int[]{0, 0});
                stats[0]++; // 总床位+1
                if ("占用".equals(bed.getUseStatus())) {
                    stats[1]++; // 已占用+1
                }
                bedStatsByDept.put(dept, stats);
            }
        }
        // 计算各科室使用率
        Map<String, Double> bedUtilizationByDept = new LinkedHashMap<>();
        for (Map.Entry<String, int[]> entry : bedStatsByDept.entrySet()) {
            int total = entry.getValue()[0];
            int used = entry.getValue()[1];
            double rate = total > 0 ? Math.round((double) used / total * 1000) / 10.0 : 0;
            bedUtilizationByDept.put(entry.getKey(), rate);
        }
        request.setAttribute("bedStatsByDept", bedStatsByDept);
        request.setAttribute("bedUtilizationByDept", bedUtilizationByDept);

        // 4. 最近收费记录：按收费日期倒序，取前5条
        List<com.hospital.entity.Charge> recentCharges = new ArrayList<>(charges != null ? charges : new ArrayList<>());
        recentCharges.sort((c1, c2) -> {
            Date d1 = c1.getChargeDate();
            Date d2 = c2.getChargeDate();
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;
            return d2.compareTo(d1);
        });
        if (recentCharges.size() > 5) {
            recentCharges = recentCharges.subList(0, 5);
        }
        request.setAttribute("recentCharges", recentCharges);

        request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
    }
}
