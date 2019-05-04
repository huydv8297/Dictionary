package com.example.dictionary.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.dictionary.DetailActivity;
import com.example.dictionary.Entity.Dictionary;
import com.example.dictionary.Entity.Result;
import com.example.dictionary.Entity.Word;
import com.example.dictionary.HistoryListAdapter;
import com.example.dictionary.ResultListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContentManager {
    public BaseAdapter adapter;
    ArrayList<String> historyItems = new ArrayList<String>();
    List<Result> resultList = new ArrayList<Result>();
    ListView listView;
    LayoutInflater layoutInflater;
    SearchController searchController;
    SharedPreferences sharedPref;

    public DictionaryManager dictionaryManager;
    public Context context;
    public Dictionary currentDict;

    public ContentManager(Context context ,ListView listView) {
        this.context = context;
        this.listView = listView;
        this.layoutInflater = layoutInflater.from(context);
        sharedPref = context.getSharedPreferences("dictionary", 0);
        searchController = new SearchController(context);
        adapter = new HistoryListAdapter(layoutInflater ,historyItems);

        dictionaryManager = new DictionaryManager(context);
        dictionaryManager.loadData();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter instanceof ResultListAdapter){
                    String item = resultList.get(position).word.getItems()[0];
                    if(!historyItems.contains(item))
                    {
                        historyItems.add(0, item);
                        adapter.notifyDataSetChanged();
                        DisplayResult(resultList.get(position).word);
                    }
                }else{
                    Result result = searchController.getHistoryResult(currentDict.getDbname(), "main", historyItems.get(position), "word");
                    DisplayResult(result.word);
                }

            }
        });
    }

    public void loadHistory(){

        if(sharedPref == null)
            Log.e("error", "null");
        Set<String> saveHistory = sharedPref.getStringSet("saveHistory" + currentDict.getId(), null);

        historyItems = new ArrayList<>();
        if(saveHistory != null){
            historyItems = new ArrayList<>(saveHistory);
        }
        listView.setAdapter(null);
        adapter = new HistoryListAdapter(layoutInflater, historyItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void saveHistory(){
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> temp = new HashSet<>(historyItems);
        editor.putStringSet("saveHistory" + currentDict.getId(), temp);
        editor.commit();
    }


    public void DisplayResult(Word word)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("word", word);
        context.startActivity(intent);
        Log.e("display", word.toString());
    }

    public void Search(String dbName, String keyword)
    {
        resultList = searchController.getResults(dbName, "main", keyword, "word");
        listView.setAdapter(null);
        adapter = new ResultListAdapter(layoutInflater, resultList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.e("Search", keyword);
    }

    public void Cancel(){
        listView.setAdapter(null);
        adapter = new HistoryListAdapter(layoutInflater, historyItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
