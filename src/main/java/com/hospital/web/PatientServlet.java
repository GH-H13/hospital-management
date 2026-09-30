package com.hospital.web;

import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.entity.Bed;
import com.hospital.service.PatientService;
import com.hospital.service.DoctorService;
import com.hospital.service.BedService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 * 病人管理 Servlet
 */
@WebServlet("/patient/*")
public class PatientServlet extends HttpServlet {
    
    private PatientService patientService = new PatientService();
    private DoctorService doctorService = new DoctorService();
    private BedService bedService = new BedService();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 医生只能查看列表，不能添加/编辑/删除
        HttpSession session = request.getSession(false);
        boolean isDoctor = session != null && session.getAttribute("doctor") != null;
        String pathInfo = request.getPathInfo();
        
        if (isDoctor && (pathInfo.equals("/add") || pathInfo.equals("/edit") || pathInfo.equals("/delete"))) {
            response.sendRedirect(request.getContextPath() + "/doctor/main");
            return;
        }
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            listPatients(request, response);
        } else if (pathInfo.equals("/add")) {
            showAddForm(request, response);
        } else if (pathInfo.equals("/edit")) {
            showEditForm(request, response);
        } else if (pathInfo.equals("/delete")) {
            deletePatient(request, response);
        } else {
            listPatients(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 医生不能执行添加/修改操作
        HttpSession session = request.getSession(false);
        boolean isDoctor = session != null && session.getAttribute("doctor") != null;
        String pathInfo = request.getPathInfo();
        
        if (isDoctor && (pathInfo.equals("/add") || pathInfo.equals("/edit"))) {
            response.sendRedirect(request.getContextPath() + "/doctor/main");
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        pathInfo = request.getPathInfo();
        
        if (pathInfo.equals("/add")) {
            addPatient(request, response);
        } else if (pathInfo.equals("/edit")) {
            updatePatient(request, response);
        }
    }
    
    private void listPatients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查是否以医生身份登录
        HttpSession session = request.getSession(false);
        com.hospital.entity.Doctor doctor = session != null ?
                (com.hospital.entity.Doctor) session.getAttribute("doctor") : null;

        List<Patient> patients;

        if (doctor != null) {
            // 医生登录：只看自己的病人
            patients = patientService.findByAttendingDoctor(doctor.getDoctorId());
            request.setAttribute("isDoctor", true);
        } else {
            // 管理员登录：查看所有病人（支持搜索）
            String keyword = request.getParameter("keyword");
            String department = request.getParameter("department");
            String doctorIdStr = request.getParameter("doctorId");

            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
            boolean hasDepartment = department != null && !department.trim().isEmpty();
            boolean hasDoctorId = doctorIdStr != null && !doctorIdStr.trim().isEmpty();

            if (hasKeyword || hasDepartment || hasDoctorId) {
                Integer docId = null;
                if (hasDoctorId) {
                    try { docId = Integer.parseInt(doctorIdStr); } catch (NumberFormatException e) {}
                }
                patients = patientService.findByConditions(
                        hasDepartment ? department : null,
                        docId,
                        null, null
                );
                if (hasKeyword) {
                    List<Patient> filtered = new ArrayList<>();
                    for (Patient p : patients) {
                        if (p.getPatientName() != null && p.getPatientName().contains(keyword)) {
                            filtered.add(p);
                        }
                    }
                    patients = filtered;
                }
            } else {
                patients = patientService.findAll();
            }

            request.setAttribute("keyword", keyword);
            request.setAttribute("department", department);
            request.setAttribute("doctorId", doctorIdStr);

            List<Doctor> doctors = doctorService.findAll();
            request.setAttribute("doctors", doctors);

            Set<String> deptSet = new LinkedHashSet<>();
            for (Doctor d : doctors) {
                if (d.getDepartment() != null && !d.getDepartment().isEmpty()) {
                    deptSet.add(d.getDepartment());
                }
            }
            request.setAttribute("departments", new ArrayList<>(deptSet));
            request.setAttribute("isDoctor", false);
        }

        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/WEB-INF/jsp/patient/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 传递医生列表
        List<Doctor> doctors = doctorService.findAll();
        request.setAttribute("doctors", doctors);
        // 传递空闲床位列表
        List<Bed> availableBeds = bedService.findAvailableBeds();
        request.setAttribute("availableBeds", availableBeds);
        // 传递科室列表（下拉选项）
        Set<String> deptSet = new LinkedHashSet<>();
        String[] defaultDepts = {"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"};
        for (String d : defaultDepts) { deptSet.add(d); }
        for (Doctor d : doctors) { if (d.getDepartment() != null && !d.getDepartment().isEmpty()) { deptSet.add(d.getDepartment()); } }
        request.setAttribute("departments", new ArrayList<>(deptSet));

        request.getRequestDispatcher("/WEB-INF/jsp/patient/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            Patient patient = patientService.findById(id);
            if (patient != null) {
                request.setAttribute("patient", patient);
                // 传递医生列表
                List<Doctor> doctors = doctorService.findAll();
                request.setAttribute("doctors", doctors);
                // 传递空闲床位 + 病人当前床位
                List<Bed> availableBeds = bedService.findAvailableBeds();
                if (patient.getBedNo() != null) {
                    Bed currentBed = bedService.findById(patient.getBedNo());
                    if (currentBed != null) {
                        boolean exists = false;
                        for (Bed b : availableBeds) {
                            if (b.getBedId().equals(currentBed.getBedId())) { exists = true; break; }
                        }
                        if (!exists) { availableBeds.add(currentBed); }
                    }
                }
                request.setAttribute("availableBeds", availableBeds);
                // 传递科室列表（下拉选项）
                Set<String> deptSet = new LinkedHashSet<>();
                String[] defaultDepts = {"内科", "外科", "儿科", "骨科", "妇产科", "眼科", "耳鼻喉科", "皮肤科", "口腔科", "急诊科"};
                for (String d : defaultDepts) { deptSet.add(d); }
                for (Doctor d : doctors) { if (d.getDepartment() != null && !d.getDepartment().isEmpty()) { deptSet.add(d.getDepartment()); } }
                request.setAttribute("departments", new ArrayList<>(deptSet));

                // 格式化日期供表单使用
                if (patient.getAdmissionDate() != null) {
                    patient.setAdmissionDateStr(sdf.format(patient.getAdmissionDate()));
                }
                if (patient.getDischargeDate() != null) {
                    patient.setDischargeDateStr(sdf.format(patient.getDischargeDate()));
                }

                request.getRequestDispatcher("/WEB-INF/jsp/patient/edit.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/patient/list");
    }
    
    private void addPatient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Patient patient = buildPatientFromRequest(request, false);
            
            boolean success = patientService.add(patient);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/patient/list");
            } else {
                request.setAttribute("error", "添加失败");
                request.getRequestDispatcher("/WEB-INF/jsp/patient/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/patient/add.jsp").forward(request, response);
        }
    }
    
    private void updatePatient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Patient patient = buildPatientFromRequest(request, true);
            
            boolean success = patientService.update(patient);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/patient/list");
            } else {
                request.setAttribute("error", "更新失败");
                request.getRequestDispatcher("/WEB-INF/jsp/patient/edit.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "数据格式错误：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/patient/edit.jsp").forward(request, response);
        }
    }
    
    private void deletePatient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            patientService.delete(id);
        }
        response.sendRedirect(request.getContextPath() + "/patient/list");
    }
    
    private Patient buildPatientFromRequest(HttpServletRequest request, boolean isUpdate) throws Exception {
        Patient patient = new Patient();
        
        if (isUpdate) {
            patient.setPatientId(Integer.parseInt(request.getParameter("patientId")));
        }
        
        patient.setDepartment(request.getParameter("department"));
        patient.setPatientName(request.getParameter("patientName"));
        patient.setGender(request.getParameter("gender"));
        patient.setAge(Integer.parseInt(request.getParameter("age")));
        patient.setIllness(request.getParameter("illness"));
        
        String bedNoStr = request.getParameter("bedNo");
        if (bedNoStr != null && !bedNoStr.trim().isEmpty()) {
            patient.setBedNo(Integer.parseInt(bedNoStr));
        }
        
        String attendingDoctorIdStr = request.getParameter("attendingDoctorId");
        if (attendingDoctorIdStr != null && !attendingDoctorIdStr.trim().isEmpty()) {
            patient.setAttendingDoctorId(Integer.parseInt(attendingDoctorIdStr));
        }
        
        String admissionDateStr = request.getParameter("admissionDate");
        if (admissionDateStr != null && !admissionDateStr.trim().isEmpty()) {
            patient.setAdmissionDate(sdf.parse(admissionDateStr));
        }
        
        String dischargeDateStr = request.getParameter("dischargeDate");
        if (dischargeDateStr != null && !dischargeDateStr.trim().isEmpty()) {
            patient.setDischargeDate(sdf.parse(dischargeDateStr));
        }
        
        return patient;
    }
}
