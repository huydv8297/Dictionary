package com.example.dictionary;

public class HistoryItem {


    public String value;
    public long time;
    public int iconId;
    public int arrowId;


    public HistoryItem(String value, long time) {
        this.value = value;
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
