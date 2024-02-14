package com.example.carevault.Adapters;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelDoc {
    String Name;
    String date;
    String clinic;
    String category;
    HashMap<String,Boolean> times;
    public ModelDoc(){

    }
    public String getName(){
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }
    public String getClinic(){
        return clinic;
    }
    public void setClinic(String clinic){
        this.clinic=clinic;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public HashMap<String,Boolean> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String,Boolean> times) {
        this.times = times;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }
}
