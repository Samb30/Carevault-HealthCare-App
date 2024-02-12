package com.example.carevault.Adapters;

import java.util.ArrayList;
import java.util.HashMap;

public class Note2 {
    String DOA;
    String Time;
    ArrayList<Boolean> times;
    HashMap<String,Boolean> map;
    public Note2(){

    }
    public String getDateM(){
        return DOA;
    }
    public void setDateM(String DOA){
        this.DOA=DOA;
    }
    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
    public ArrayList<Boolean> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Boolean> times) {
        this.times = times;
    }
    public HashMap<String,Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<String,Boolean> map) {
        this.map = map;
    }
}