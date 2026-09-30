package com.hospital.entity;

import java.io.Serializable;

/**
 * 病床实体类
 */
public class Bed implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer bedId;          // 病床ID（主键）
    private String department;      // 科别
    private String bedNo;           // 病床号
    private Double bedFee;          // 床位费
    private String useStatus;       // 使用状态（空闲/占用）

    public Bed() {
    }

    public Bed(Integer bedId, String department, String bedNo, Double bedFee, String useStatus) {
        this.bedId = bedId;
        this.department = department;
        this.bedNo = bedNo;
        this.bedFee = bedFee;
        this.useStatus = useStatus;
    }

    public Integer getBedId() {
        return bedId;
    }

    public void setBedId(Integer bedId) {
        this.bedId = bedId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public Double getBedFee() {
        return bedFee;
    }

    public void setBedFee(Double bedFee) {
        this.bedFee = bedFee;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    @Override
    public String toString() {
        return "Bed{" +
                "bedId=" + bedId +
                ", department='" + department + '\'' +
                ", bedNo='" + bedNo + '\'' +
                ", bedFee=" + bedFee +
                ", useStatus='" + useStatus + '\'' +
                '}';
    }
}
