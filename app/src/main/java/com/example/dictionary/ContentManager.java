package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.dictionary.Controller.SearchController;
import com.example.dictionary.Entity.Word;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContentManager {
    public BaseAdapter adapter;
    List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
    List<Result> resultList = new ArrayList<Result>();
    ListView listView;
    LayoutInflater layoutInflater;
    SearchController searchController = new SearchController();
    Context context;

    public ContentManager(Context context ,ListView listView) {
        this.context = context;
        this.listView = listView;
        this.layoutInflater = layoutInflater.from(context);

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
                        DisplayResult(item.word);
                    }
                }else{
                    DisplayResult(historyItems.get(position).word);
                }

            }
        });
    }

    public String[] getHistory(){
        String[] temp = new String[historyItems.size()];
        for(int i = 0; i < historyItems.size(); i++){
            temp[i] = historyItems.get(i).word.getItems()[0];
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

    public void Search(String keyword)
    {
        resultList = searchController.getResults(context,"en_vi.db", "main", keyword, "word");
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

    public void ChangeTab(int position){

        adapter.notifyDataSetChanged();
    }

}
