package com.example.dictionary.Controller;

import android.content.Context;
import android.database.Cursor;

import com.example.dictionary.DAO.DataBaseController;
import com.example.dictionary.Entity.Dictionary;
import com.example.dictionary.Entity.Result;
import com.example.dictionary.Entity.Word;

import java.util.ArrayList;
import java.util.List;

public class DictionaryManager {

    Context context;

    private static final String nameDB = "dict.db";
    private static final String tableName = "dictionary";
    private static final String columeQuery = "dict_name";


    public ArrayList<Dictionary> dictionaryList;
    public ArrayList<String> dictionaryListName;
    public Dictionary currentDict;

    public DictionaryManager(Context context) {
        this.context = context;
    }

    public void loadData() {
        DataBaseController mDbHelper = new DataBaseController(context, nameDB, nameDB);
        mDbHelper.createDatabase();
        mDbHelper.open();
        dictionaryListName = new ArrayList<>();
        dictionaryList = new ArrayList<>();

        Cursor cursor = mDbHelper.getWord(tableName, columeQuery + " LIKE '"  + "%'");
        if(cursor == null)
            return;
        if (cursor.moveToFirst()) {
            do {
                Dictionary dictionary = new Dictionary();
                dictionary.setId(Integer.parseInt(cursor.getString(0)));
                dictionary.setDbname(cursor.getString(1));
                dictionary.setName(cursor.getString(2));

                dictionaryListName.add(cursor.getString(2));
                dictionaryList.add(dictionary);
            } while (cursor.moveToNext());
        }
        mDbHelper.close();
    }

    public ArrayList<Dictionary> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(ArrayList<Dictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    public ArrayList<String> getDictionaryListName() {
        return dictionaryListName;
    }

    public void setDictionaryListName(ArrayList<String> dictionaryListName) {
        this.dictionaryListName = dictionaryListName;
    }
}
