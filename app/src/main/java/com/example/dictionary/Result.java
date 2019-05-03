package com.example.dictionary;

import com.example.dictionary.Entity.Word;

public class Result {

    public String value;
    public int iconId;
    public int arrowId;

    Word word;

    public Result(Word word) {
        this.word = word;
        this.value = word.getItems()[0];
        this.iconId = R.drawable.searchicon;
        this.arrowId = R.drawable.north_west_arrow;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getArrowId() {
        return arrowId;
    }

    public void setArrowId(int arrowId) {
        this.arrowId = arrowId;
    }

}
