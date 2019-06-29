package com.github.startzyp.mc.entity;

public class Honorentity {

    private String DisPlayName;
    private String explain;
    private String permission;
    private int HonorValue;
    private String ItemId;

    public Honorentity(String disPlayName, String explain, String permission, int honorValue, String itemId){
        DisPlayName = disPlayName;
        this.explain = explain;
        this.permission = permission;
        HonorValue = honorValue;
        ItemId = itemId;
    }

    public String getDisPlayName(){
        return DisPlayName;
    }

    public void setDisPlayName(String disPlayName){
        DisPlayName = disPlayName;
    }

    public String getExplain(){
        return explain;
    }

    public void setExplain(String explain){
        this.explain = explain;
    }

    public String getPermission(){
        return permission;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }

    public int getHonorValue(){
        return HonorValue;
    }

    public void setHonorValue(int honorValue){
        HonorValue = honorValue;
    }

    public String getItemId(){
        return ItemId;
    }

    public void setItemId(String itemId){
        ItemId = itemId;
    }
}
