package com.example.carevault;

import java.util.ArrayList;

public class Note {
    String title;
    String content;
    String date;
    String id;
    ArrayList<Integer> arr;
    public Note(){

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
}
