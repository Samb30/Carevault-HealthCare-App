package com.example.carevault.Adapters;

import java.util.ArrayList;

public class Note1 {
    String title;
    String content;
    String date;
    String id;
    String silent;
    ArrayList<Integer> arr;
    String text;
    String timer;
    String times;
    ArrayList<String> mIds;
    ArrayList<String> days;
    boolean repeat;
    ArrayList<String> ids2;
    public Note1(){

    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return date;
    }

    public void setTimestamp(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Integer> getArr() {
        return arr;
    }

    public void setArr(ArrayList<Integer> arr) {
        this.arr = arr;
    }
    public String getSilent(){
        return silent;
    }
    public void setSilent(String silent){
        this.silent=silent;
    }
    public String getText1(){
        return text;
    }
    public void setText1(String text){
        this.text=text;
    }
    public String getTimer(){
        return timer;
    }
    public void setTimer(String timer){
        this.timer=timer;
    }
    public String getTimes(){
        return times;
    }
    public void setTimes(String times){
        this.times=times;
    }
    public ArrayList<String> getMids() {
        return mIds;
    }

    public void setMids(ArrayList<String> mIds) {
        this.mIds = mIds;
    }
    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
    public boolean getRepeat(){
        return repeat;
    }
    public void setRepeat(boolean repeat){
        this.repeat=repeat;
    }
    public ArrayList<String> getIds2() {
        return ids2;
    }

    public void setIds2(ArrayList<String> ids2) {
        this.ids2 = ids2;
    }
}
