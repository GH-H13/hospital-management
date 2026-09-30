package com.hospital.web;

import com.hospital.entity.Charge;
import com.hospital.service.ChargeService;

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
 * 收费管理 Servlet
 */
@WebServlet("/charge/*")
public class ChargeServlet extends HttpServlet {
    
    private ChargeService chargeService = new ChargeService();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            listCharges(request, response);
        } else if (pathInfo.equals("/add")) {
            showAddForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deleteCharge(request, response);
        } else {
            listCharges(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/add")) {
            addCharge(request, response);
        } else if (pathInfo.equals("/edit")) {
            updateCharge(request, response);
        }
    }
    
    private void listCharges(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Charge> charges;
        if (keyword != null && !keyword.trim().isEmpty()) {
            charges = chargeService.findByPatientName(keyword);
        } else {
            charges = chargeService.findAll();
        }
        request.setAttribute("charges", charges);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/WEB-INF/jsp/charge/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 获取所有病人供选择
        com.hospital.service.PatientService patientService = new com.hospital.service.PatientService();
        List<com.hospital.entity.Patient> patients = patientService.findAll();
        request.setAttribute("patients", patients);
        
        // 获取所有收费项目
        com.hospital.service.ChargeItemService chargeItemService = new com.hospital.service.ChargeItemService();
        request.setAttribute("chargeItems", chargeItemService.findAll());
        
        request.getRequestDispatcher("/WEB-INF/jsp/charge/add.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            Charge charge = chargeService.findById(id);
            if (charge != null) {
                request.setAttribute("charge", charge);
                // 获取所有病人供选择
                com.hospital.service.PatientService patientService = new com.hospital.service.PatientService();
                List<com.hospital.entity.Patient> patients = patientService.findAll();
                request.setAttribute("patients", patients);
                
                // 获取所有收费项目
                com.hospital.service.ChargeItemService chargeItemService = new com.hospital.service.ChargeItemService();
                request.setAttribute("chargeItems", chargeItemService.findAll());
                
                request.getRequestDispatcher("/WEB-INF/jsp/charge/edit.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/charge/list");
    }
    
    private void addCharge(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Charge charge = new Charge();
            charge.setPatientId(Integer.parseInt(request.getParameter("patientId")));
            
            // 根据itemId查找收费项目名称
            String itemIdStr = request.getParameter("itemId");
            if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                int itemId = Integer.parseInt(itemIdStr);
                com.hospital.service.ChargeItemService chargeItemService = new com.hospital.service.ChargeItemService();
                com.hospital.entity.ChargeItem item = chargeItemService.findById(itemId);
                if (item != null) {
                    charge.setChargeItem(item.getItemName());
                    charge.setUnitPrice(item.getDefaultPrice());
                } else {
                    // 如果找不到，使用表单提交的值
                    charge.setChargeItem(request.getParameter("chargeItem"));
                    charge.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
                }
            } else {
                charge.setChargeItem(request.getParameter("chargeItem"));
                charge.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
            }
            
            charge.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            
            // 自动计算金额
            double amount = charge.getUnitPrice() * charge.getQuantity();
            charge.setAmount(amount);
            
            String chargeDateStr = request.getParameter("chargeDate");
            if (chargeDateStr != null && !chargeDateStr.trim().isEmpty()) {
                charge.setChargeDate(sdf.parse(chargeDateStr));
            } else {
                charge.setChargeDate(new Date());
            }
            
            boolean success = chargeService.add(charge);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/charge/list");
            } else {
                request.setAttribute("error", "添加失败");
                request.getRequestDispatcher("/WEB-INF/jsp/charge/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/charge/add.jsp").forward(request, response);
        }
    }
    
    private void updateCharge(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Charge charge = new Charge();
            charge.setChargeId(Integer.parseInt(request.getParameter("chargeId")));
            charge.setPatientId(Integer.parseInt(request.getParameter("patientId")));
            
            // 根据itemId查找收费项目名称
            String itemIdStr = request.getParameter("itemId");
            if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                int itemId = Integer.parseInt(itemIdStr);
                com.hospital.service.ChargeItemService chargeItemService = new com.hospital.service.ChargeItemService();
                com.hospital.entity.ChargeItem item = chargeItemService.findById(itemId);
                if (item != null) {
                    charge.setChargeItem(item.getItemName());
                    charge.setUnitPrice(item.getDefaultPrice());
                } else {
                    // 如果找不到，使用表单提交的值
                    charge.setChargeItem(request.getParameter("chargeItem"));
                    charge.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
                }
            } else {
                charge.setChargeItem(request.getParameter("chargeItem"));
                charge.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
            }
            
            charge.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            
            // 自动计算金额
            double amount = charge.getUnitPrice() * charge.getQuantity();
            charge.setAmount(amount);
            
            String chargeDateStr = request.getParameter("chargeDate");
            if (chargeDateStr != null && !chargeDateStr.trim().isEmpty()) {
                charge.setChargeDate(sdf.parse(chargeDateStr));
            }
            
            boolean success = chargeService.update(charge);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/charge/list");
            } else {
                request.setAttribute("error", "更新失败");
                request.getRequestDispatcher("/WEB-INF/jsp/charge/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/charge/edit.jsp").forward(request, response);
        }
    }
    
    private void deleteCharge(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            chargeService.delete(id);
        }
        response.sendRedirect(request.getContextPath() + "/charge/list");
    }
}
