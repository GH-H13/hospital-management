package com.hospital.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 病人实体类
 */
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer patientId;              // 病人编号（主键）
    private String department;              // 科别
    private Integer bedNo;                  // 病床号（外键关联Bed表的bedId）
    private String patientName;             // 病人姓名
    private String gender;                  // 性别
    private Integer age;                    // 年龄
    private String illness;                 // 病症
    private Integer attendingDoctorId;      // 主治医生ID（外键关联Doctor表）
    private Date admissionDate;             // 入院日期
    private Date dischargeDate;             // 出院日期

    // 关联属性（用于显示）
    private String attendingDoctorName;     // 主治医生姓名
    private String bedNoStr;                // 病床号显示
    private String admissionDateStr;        // 入院日期字符串格式（用于表单）
    private String dischargeDateStr;        // 出院日期字符串格式（用于表单）

    public Patient() {
    }

    public Patient(Integer patientId, String department, Integer bedNo, String patientName, 
                   String gender, Integer age, String illness, Integer attendingDoctorId, 
                   Date admissionDate, Date dischargeDate) {
        this.patientId = patientId;
        this.department = department;
        this.bedNo = bedNo;
        this.patientName = patientName;
        this.gender = gender;
        this.age = age;
        this.illness = illness;
        this.attendingDoctorId = attendingDoctorId;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getBedNo() {
        return bedNo;
    }

    public void setBedNo(Integer bedNo) {
        this.bedNo = bedNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public Integer getAttendingDoctorId() {
        return attendingDoctorId;
    }

    public void setAttendingDoctorId(Integer attendingDoctorId) {
        this.attendingDoctorId = attendingDoctorId;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getAttendingDoctorName() {
        return attendingDoctorName;
    }

    public void setAttendingDoctorName(String attendingDoctorName) {
        this.attendingDoctorName = attendingDoctorName;
    }

    public String getBedNoStr() {
        return bedNoStr;
    }

    public void setBedNoStr(String bedNoStr) {
        this.bedNoStr = bedNoStr;
    }

    public String getAdmissionDateStr() {
        return admissionDateStr;
    }

    public void setAdmissionDateStr(String admissionDateStr) {
        this.admissionDateStr = admissionDateStr;
    }

    public String getDischargeDateStr() {
        return dischargeDateStr;
    }

    public void setDischargeDateStr(String dischargeDateStr) {
        this.dischargeDateStr = dischargeDateStr;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", department='" + department + '\'' +
                ", bedNo=" + bedNo +
                ", patientName='" + patientName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", illness='" + illness + '\'' +
                ", attendingDoctorId=" + attendingDoctorId +
                ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate +
                '}';
    }
}
