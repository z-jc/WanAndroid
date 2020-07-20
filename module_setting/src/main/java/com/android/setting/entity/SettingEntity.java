package com.android.setting.entity;

/**
 * FileName: SettingEntity
 * Author: admin
 * Date: 2020/7/20 16:50
 * Description:
 */
public class SettingEntity {

    public String itemName;
    public String itemValue;

    public SettingEntity(String itemName, String itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    @Override
    public String toString() {
        return "SettingEntity{" +
                "itemName='" + itemName + '\'' +
                ", itemValue='" + itemValue + '\'' +
                '}';
    }
}
