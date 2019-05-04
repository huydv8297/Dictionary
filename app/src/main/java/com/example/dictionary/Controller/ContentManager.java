package com.example.dictionary.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.dictionary.Controller.SearchController;
import com.example.dictionary.DetailActivity;
import com.example.dictionary.Entity.HistoryItem;
import com.example.dictionary.Entity.Result;
import com.example.dictionary.Entity.Word;
import com.example.dictionary.HistoryListAdapter;
import com.example.dictionary.ResultListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ContentManager {
    public BaseAdapter adapter;
    List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
    List<Result> resultList = new ArrayList<Result>();
    ListView listView;
    LayoutInflater layoutInflater;
    SearchController searchController;
    public Context context;

    public ContentManager(Context context ,ListView listView) {
        this.context = context;
        this.listView = listView;
        this.layoutInflater = layoutInflater.from(context);
        searchController = new SearchController(context);
        adapter = new HistoryListAdapter(layoutInflater ,historyItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter instanceof ResultListAdapter){
                    HistoryItem item = new HistoryItem( resultList.get(position).word, 1);
                    if(!historyItems.contains(item))
                    {
                        historyItems.add(0, item);
                        adapter.notifyDataSetChanged();
                        DisplayResult(resultList.get(position).word);
                    }
                }else{
                    Result result = searchController.getHistoryResult("en_vi.db", "main", historyItems.get(position).value, "word");
                    DisplayResult(result.word);
                }

            }
        });
    }

    public String[] getHistory(){
        String[] temp = new String[historyItems.size()];
        for(int i = 0; i < historyItems.size(); i++){
            temp[i] = historyItems.get(i).value;
        }
        return temp;
    }

    public void DisplayResult(Word word)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("word", word);
        context.startActivity(intent);
        Log.e("display", word.toString());
    }

    public void DisplayResult(String keyword)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("word", keyword);
        context.startActivity(intent);
        Log.e("display", keyword);
    }

    public void Search(String keyword)
    {
        resultList = searchController.getResults("en_vi.db", "main", keyword, "word");
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
