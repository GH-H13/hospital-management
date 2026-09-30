package com.hospital.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生实体类
 */
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer doctorId;           // 医生编号（主键）
    private String doctorName;          // 医生姓名
    private String gender;              // 性别
    private String title;               // 职称
    private String duty;                // 职务
    private String department;          // 科室
    private Date birthDate;             // 出生日期
    private Date workDate;              // 工作日期
    private String password;            // 登录密码
    private String phone;               // 联系电话
    private String email;               // 邮箱地址
    private String birthDateStr;        // 出生日期字符串格式（用于表单）
    private String workDateStr;         // 工作日期字符串格式（用于表单）

    public Doctor() {
    }

    public Doctor(Integer doctorId, String doctorName, String gender, String title, 
                  String duty, String department, Date birthDate, Date workDate) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.gender = gender;
        this.title = title;
        this.duty = duty;
        this.department = department;
        this.birthDate = birthDate;
        this.workDate = workDate;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public String getWorkDateStr() {
        return workDateStr;
    }

    public void setWorkDateStr(String workDateStr) {
        this.workDateStr = workDateStr;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", gender='" + gender + '\'' +
                ", title='" + title + '\'' +
                ", duty='" + duty + '\'' +
                ", department='" + department + '\'' +
                ", birthDate=" + birthDate +
                ", workDate=" + workDate +
                ", phone=" + phone +
                ", email=" + email +
                '}';
    }
}
