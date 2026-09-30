package com.hospital.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费实体类
 */
public class Charge implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer chargeId;       // 收费ID（主键）
    private Integer patientId;      // 病人ID（外键关联Patient表）
    private String chargeItem;      // 收费项目
    private Double unitPrice;       // 单价
    private Integer quantity;       // 数量
    private Double amount;          // 金额
    private Date chargeDate;        // 收费日期

    // 关联属性（用于显示）
    private String patientName;     // 病人姓名
    private String department;      // 科别

    public Charge() {
    }

    public Charge(Integer chargeId, Integer patientId, String chargeItem, 
                  Double unitPrice, Integer quantity, Double amount, Date chargeDate) {
        this.chargeId = chargeId;
        this.patientId = patientId;
        this.chargeItem = chargeItem;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.amount = amount;
        this.chargeDate = chargeDate;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getChargeItem() {
        return chargeItem;
    }

    public void setChargeItem(String chargeItem) {
        this.chargeItem = chargeItem;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Date chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "chargeId=" + chargeId +
                ", patientId=" + patientId +
                ", chargeItem='" + chargeItem + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", chargeDate=" + chargeDate +
                '}';
    }
}
