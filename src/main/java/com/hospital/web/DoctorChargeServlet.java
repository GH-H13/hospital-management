package com.hospital.web;

import com.hospital.entity.Charge;
import com.hospital.entity.ChargeItem;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.service.ChargeItemService;
import com.hospital.service.ChargeService;
import com.hospital.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 医生查看和录入收费记录 Servlet
 */
@WebServlet("/doctor/charges")
public class DoctorChargeServlet extends HttpServlet {

    private PatientService patientService = new PatientService();
    private ChargeService chargeService = new ChargeService();
    private ChargeItemService chargeItemService = new ChargeItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检查是否以医生身份登录
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/login");
            return;
        }

        // 查询该医生的所有病人
        List<Patient> patients = patientService.findByAttendingDoctor(doctor.getDoctorId());

        // 查询所有病人的收费记录
        List<Charge> allCharges = new ArrayList<>();
        double totalAmount = 0;

        if (patients != null) {
            for (Patient patient : patients) {
                List<Charge> charges = chargeService.findByPatientId(patient.getPatientId());
                if (charges != null) {
                    for (Charge charge : charges) {
                        allCharges.add(charge);
                        totalAmount += charge.getAmount();
                    }
                }
            }
        }

        request.setAttribute("charges", allCharges);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("patients", patients);
        request.setAttribute("doctor", doctor);
        request.setAttribute("chargeItems", chargeItemService.findAll());

        request.getRequestDispatcher("/WEB-INF/jsp/doctor/charges.jsp").forward(request, response);
    }

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

        String patientIdStr = request.getParameter("patientId");
        String chargeItem = request.getParameter("chargeItem");
        String unitPriceStr = request.getParameter("unitPrice");
        String quantityStr = request.getParameter("quantity");

        // 验证输入
        if (patientIdStr == null || chargeItem == null || unitPriceStr == null || quantityStr == null) {
            request.getSession().setAttribute("chargeError", "请填写完整的收费信息！");
            response.sendRedirect(request.getContextPath() + "/doctor/charges");
            return;
        }

        try {
            Integer patientId = Integer.parseInt(patientIdStr);
            Double unitPrice = Double.parseDouble(unitPriceStr);
            Integer quantity = Integer.parseInt(quantityStr);
            Double amount = unitPrice * quantity;

            // 验证该病人是否属于当前医生
            List<Patient> patients = patientService.findByAttendingDoctor(doctor.getDoctorId());
            boolean isMyPatient = false;
            for (Patient p : patients) {
                if (p.getPatientId().equals(patientId)) {
                    isMyPatient = true;
                    break;
                }
            }

            if (!isMyPatient) {
                request.getSession().setAttribute("chargeError", "只能为自己的病人添加收费记录！");
                response.sendRedirect(request.getContextPath() + "/doctor/charges");
                return;
            }

            // 创建收费记录
            Charge charge = new Charge();
            charge.setPatientId(patientId);
            charge.setChargeItem(chargeItem);
            charge.setUnitPrice(unitPrice);
            charge.setQuantity(quantity);
            charge.setAmount(amount);
            charge.setChargeDate(new Date());

            boolean success = chargeService.add(charge);

            if (success) {
                request.getSession().setAttribute("chargeSuccess", "收费记录添加成功！");
            } else {
                request.getSession().setAttribute("chargeError", "收费记录添加失败！");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("chargeError", "输入格式错误！");
        }

        response.sendRedirect(request.getContextPath() + "/doctor/charges");
    }
}
