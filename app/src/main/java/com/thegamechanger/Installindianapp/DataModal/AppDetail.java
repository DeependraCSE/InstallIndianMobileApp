package com.thegamechanger.Installindianapp.DataModal;

public class AppDetail {
    int table_id;
    String name,package_name,icon,type_name,type_id;
    boolean isInstalled;

    public AppDetail() {
    }

    public AppDetail(String name, String package_name, String icon, String type_name, String type_id) {
        this.name = name;
        this.package_name = package_name;
        this.icon = icon;
        this.type_name = type_name;
        this.type_id = type_id;
    }

    public AppDetail(int table_id, String name, String package_name, String icon, String type_name, String type_id, boolean isInstalled) {
        this.table_id = table_id;
        this.name = name;
        this.package_name = package_name;
        this.icon = icon;
        this.type_name = type_name;
        this.type_id = type_id;
        this.isInstalled = isInstalled;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }
}
