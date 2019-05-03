package com.example.dictionary.Entity;

public class Word {
    private int id;
    private String[] items;

    public Word() {
    }

    public Word(String[] items) {
        this.items = items;
    }

    public Word(int id, String[] items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }
}
