package com.example.lauribohm.javafinkkariviikko9;

public class Theatres {

    String name;
    String id;

    public void setInfo(String n, String i){

        name = n;
        id = i;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
