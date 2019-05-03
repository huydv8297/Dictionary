package com.example.dictionary.Controller;

import android.content.Context;
import android.database.Cursor;

import com.example.dictionary.DAO.DataBaseController;
import com.example.dictionary.Entity.Word;
import com.example.dictionary.Result;

import java.util.ArrayList;

public class SearchController {
    public SearchController() {

    }

    public ArrayList<Result> getResults(Context context, String nameDB, String tableName, String keyWord, String columeQuery) {
        DataBaseController mDbHelper = new DataBaseController(context, nameDB, nameDB);
        mDbHelper.createDatabase();
        mDbHelper.open();

        ArrayList<Result> wordResults = new ArrayList<>();

        Cursor cursor = mDbHelper.getWord(tableName, columeQuery + " LIKE '" + keyWord + "%'  ORDER BY " + columeQuery + " ASC");
        if(cursor == null)
            return  wordResults;
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                String[] items = new String[cursor.getColumnCount() - 1];
                for (int i = 0; i < items.length; i++) {
                    items[i] = cursor.getString(i + 1);
                }
                word.setItems(items);

                wordResults.add(new Result(word));
            } while (cursor.moveToNext());
        }
        mDbHelper.close();
        return  wordResults;
    }
}
