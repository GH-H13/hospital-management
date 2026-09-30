package com.hospital.entity;

import java.io.Serializable;

/**
 * 病床利用率统计数据对象
 */
public class BedUtilization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String department;      // 科室名称
    private Integer totalBeds;      // 总病床数
    private Integer usedBeds;       // 占用病床数
    private Double utilizationRate; // 利用率（保留1位小数）

    public BedUtilization() {
    }

    public BedUtilization(String department, Integer totalBeds, Integer usedBeds, Double utilizationRate) {
        this.department = department;
        this.totalBeds = totalBeds;
        this.usedBeds = usedBeds;
        this.utilizationRate = utilizationRate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(Integer totalBeds) {
        this.totalBeds = totalBeds;
    }

    public Integer getUsedBeds() {
        return usedBeds;
    }

    public void setUsedBeds(Integer usedBeds) {
        this.usedBeds = usedBeds;
    }

    public Double getUtilizationRate() {
        return utilizationRate;
    }

    public void setUtilizationRate(Double utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    @Override
    public String toString() {
        return "BedUtilization{" +
                "department='" + department + '\'' +
                ", totalBeds=" + totalBeds +
                ", usedBeds=" + usedBeds +
                ", utilizationRate=" + utilizationRate +
                '}';
    }
}
