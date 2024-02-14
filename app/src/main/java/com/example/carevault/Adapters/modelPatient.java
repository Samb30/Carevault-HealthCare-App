package com.example.carevault.Adapters;

public class modelPatient {
    String name;
    String age;
    String problem;
    String time;
    String date;
    String dName;
    String category;
    String docId;
    long sTime;
    public modelPatient(){
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getAge(){
        return age;
    }
    public void setAge(String age){
        this.age=age;
    }
    public String getProblem() {
        return problem;
    }
    public void setProblem(String problem){
        this.problem=problem;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public String getDname(){
        return dName;
    }
    public void setDname(String dName){
        this.dName=dName;
    }
    public String getDocid(){
        return docId;
    }
    public void setDocid(String docId){
        this.docId=docId;
    }
    public long getStime(){
        return sTime;
    }
    public void setStime(long sTime){
        this.sTime=sTime;
    }
}
