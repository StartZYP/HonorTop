package com.github.startzyp.mc.entity;

import java.util.List;

public class PlayerModel {
    private List<String> honorid;
    private int TotalHonorValue;

    public PlayerModel(List<String> honorid, int totalHonorValue){
        this.honorid = honorid;
        TotalHonorValue = totalHonorValue;
    }

    public List<String> getHonorid(){
        return honorid;
    }

    public void setHonorid(List<String> honorid){
        this.honorid = honorid;
    }

    public int getTotalHonorValue(){
        return TotalHonorValue;
    }

    public void setTotalHonorValue(int totalHonorValue){
        TotalHonorValue = totalHonorValue;
    }
}
