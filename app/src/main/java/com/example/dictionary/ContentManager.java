package com.example.dictionary;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class ContentManager {
    public BaseAdapter adapter;
    List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
    List<Result> resultList = new ArrayList<Result>();
    ListView listView;
    LayoutInflater layoutInflater;
    SearchController searchController = new SearchController();
    public ContentManager(LayoutInflater layoutInflater ,ListView listView) {
        this.listView = listView;
        this.layoutInflater = layoutInflater;
        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("hâhaa",0));
        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("test1",0));

        adapter = new HistoryListAdapter(layoutInflater ,historyItems);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historyItems.add(new HistoryItem("adđ", 9));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void Search(Context context, String keyword)
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
