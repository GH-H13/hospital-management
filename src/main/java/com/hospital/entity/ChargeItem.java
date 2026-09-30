package com.hospital.entity;

import java.io.Serializable;

/**
 * 收费项目实体类
 */
public class ChargeItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer itemId;         // 项目ID（主键）
    private String itemName;        // 项目名称
    private String itemType;        // 项目类型（诊疗/护理/检查等）
    private Double defaultPrice;    // 默认单价

    public ChargeItem() {
    }

    public ChargeItem(Integer itemId, String itemName, String itemType, Double defaultPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.defaultPrice = defaultPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    @Override
    public String toString() {
        return "ChargeItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", defaultPrice=" + defaultPrice +
                '}';
    }
}
