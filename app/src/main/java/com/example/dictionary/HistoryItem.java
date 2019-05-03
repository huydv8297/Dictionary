package com.example.dictionary;

import com.example.dictionary.Entity.Word;

public class HistoryItem {


    public String value;
    public long time;
    public int iconId;
    public int arrowId;
    Word word;

    public HistoryItem(Word word, long time) {
        this.word = word;
        this.value = word.getItems()[0];
        this.time = time;
        this.iconId = R.drawable.history;
        this.arrowId = R.drawable.north_west_arrow;
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


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
