package com.example.dictionary;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.arch.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabFragment extends Fragment {

    MainActivity main;
    Context context = null;

    public ListView listView;

    public BaseAdapter adapter;
    List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
    List<Result> resultList = new ArrayList<Result>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {

        }


        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("hâhaa",0));
        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("test1",0));
        historyItems.add(new HistoryItem("test1",0));

        //adapter = new HistoryListAdapter(context, historyItems);
    }

    public void Search(String keyword)
    {
        resultList.add(new Result(keyword));
        //adapter = new ResultListAdapter(context, resultList);
        adapter.notifyDataSetChanged();
    }

    public void Cancel(){
        //adapter = new HistoryListAdapter(context, historyItems);
        adapter.notifyDataSetChanged();
    }

    public void ChangeTab(int position){


        adapter.notifyDataSetChanged();
    }

    public int type;
    public static TabFragment newInstance(int tab_layout, int tab_type) {
        TabFragment f = new TabFragment();
        Bundle b = new Bundle();
        b.putInt("tab_layout", tab_layout);
        b.putInt("tab_type", tab_type);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int tab_layout = bundle.getInt("tab_layout");
        int tab_type = bundle.getInt("tab_type");
        this.type = tab_type;


        View layout =  inflater.inflate(R.layout.content_main, container, false);

        listView = (ListView) layout.findViewById(R.id.history_list);
        switch (type){
            case TabType.JAPAN_VIETNAM:
                break;
            case TabType.VIETNAM_JAPAN:
                historyItems = new ArrayList<HistoryItem>();
                break;
            case TabType.GRAMMAR:
                break;
            case TabType.KANJI:
                break;
        }


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historyItems.add(new HistoryItem("adđ", 9));
                adapter.notifyDataSetChanged();
            }
        });

        return layout;
    }
}