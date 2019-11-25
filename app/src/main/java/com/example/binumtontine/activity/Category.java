package com.example.binumtontine.activity;

/**
 * Create a class file named Category.java
 * This model class will be useful to convert json data into objects.
 */
public class Category {

    private int id;
    private int fk;
    private String name;

    public Category(){}

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }
    public Category(int id,int fk, String name){
        this.id = id;
        this.id = fk;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setFk(int fk){
        this.fk = fk;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }
    public int getFk(){
        return this.fk;
    }

    public String getName(){
        return this.name;
    }

}
