package com.example.carevault.Adapters;

public class ModelCategory {
    String name;
    String category;
    String location;
    String hospital;
    public ModelCategory(){

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public String getHospital(){
        return hospital;
    }
    public void setHospital(String hospital){
        this.hospital=hospital;
    }
}
