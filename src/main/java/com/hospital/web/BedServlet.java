package com.hospital.web;

import com.hospital.entity.Bed;
import com.hospital.service.BedService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 床位管理 Servlet
 */
@WebServlet("/bed/*")
public class BedServlet extends HttpServlet {
    
    private BedService bedService = new BedService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            listBeds(request, response);
        } else if (pathInfo.equals("/add")) {
            showAddForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deleteBed(request, response);
        } else {
            listBeds(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/add")) {
            addBed(request, response);
        } else if (pathInfo.equals("/edit")) {
            updateBed(request, response);
        }
    }
    
    private void listBeds(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Bed> beds = bedService.findAll();
        request.setAttribute("beds", beds);
        request.getRequestDispatcher("/WEB-INF/jsp/bed/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 传递科室列表
        String[] departments = {"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"};
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/WEB-INF/jsp/bed/add.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            Bed bed = bedService.findById(id);
            if (bed != null) {
                request.setAttribute("bed", bed);
                // 传递科室列表
                String[] departments = {"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"};
                request.setAttribute("departments", departments);
                request.getRequestDispatcher("/WEB-INF/jsp/bed/edit.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/bed/list");
    }
    
    private void addBed(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String department = request.getParameter("department");
            String bedNo = request.getParameter("bedNo");
            
            // 检查同一科室下床位号是否已存在
            Bed existing = bedService.findByBedNoAndDept(bedNo, department);
            if (existing != null) {
                String[] departments = {"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"};
                request.setAttribute("departments", departments);
                request.setAttribute("error", "该科室已存在床位号\"" + bedNo + "\"，不可重复添加");
                request.getRequestDispatcher("/WEB-INF/jsp/bed/add.jsp").forward(request, response);
                return;
            }
            
            Bed bed = new Bed();
            bed.setDepartment(department);
            bed.setBedNo(bedNo);
            bed.setBedFee(Double.parseDouble(request.getParameter("bedFee")));
            bed.setUseStatus(request.getParameter("useStatus"));
            
            boolean success = bedService.add(bed);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/bed/list");
            } else {
                request.setAttribute("error", "添加失败");
                request.getRequestDispatcher("/WEB-INF/jsp/bed/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/bed/add.jsp").forward(request, response);
        }
    }
    
    private void updateBed(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Bed bed = new Bed();
            bed.setBedId(Integer.parseInt(request.getParameter("bedId")));
            bed.setDepartment(request.getParameter("department"));
            bed.setBedNo(request.getParameter("bedNo"));
            bed.setBedFee(Double.parseDouble(request.getParameter("bedFee")));
            bed.setUseStatus(request.getParameter("useStatus"));
            
            boolean success = bedService.update(bed);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/bed/list");
            } else {
                request.setAttribute("error", "更新失败");
                request.getRequestDispatcher("/WEB-INF/jsp/bed/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/bed/edit.jsp").forward(request, response);
        }
    }
    
    private void deleteBed(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            bedService.delete(id);
        }
        response.sendRedirect(request.getContextPath() + "/bed/list");
    }
}
