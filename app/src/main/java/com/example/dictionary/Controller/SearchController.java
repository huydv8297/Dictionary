package com.example.dictionary.Controller;

import android.content.Context;
import android.database.Cursor;

import com.example.dictionary.DAO.DataBaseController;
import com.example.dictionary.Entity.Word;

import java.util.LinkedList;
import java.util.List;

public class SearchController {
    public SearchController() {

    }

    public List<Word> getResults(Context context, String nameDB, String tableName, String keyWord, String columeQuery) {
        DataBaseController mDbHelper = new DataBaseController(context, nameDB, nameDB);
        mDbHelper.createDatabase();
        mDbHelper.open();

        LinkedList<Word> wordResults = new LinkedList<Word>();

        Cursor cursor = mDbHelper.getWord(tableName, columeQuery + " LIKE '" + keyWord + "%'");

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                String[] items = new String[cursor.getColumnCount() - 1];
                for (int i = 0; i < items.length; i++) {
                    items[i] = cursor.getString(i + 1);
                }
                word.setItems(items);
                wordResults.add(word);
            } while (cursor.moveToNext());
        }
        mDbHelper.close();
        return  wordResults;
    }
}
