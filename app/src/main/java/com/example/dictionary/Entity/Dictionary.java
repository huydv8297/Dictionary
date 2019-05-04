package com.example.dictionary.Entity;

public class Dictionary {
    public int id;
    public String dbname;
    public String name;

    public Dictionary(){

    }

    public Dictionary(int id, String dbname, String name) {
        this.id = id;
        this.dbname = dbname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
