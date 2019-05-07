package com.example.dictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dictionary.Entity.Word;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Word word = (Word)getIntent().getSerializableExtra("word");



        TextView detail = (TextView) findViewById(R.id.detail);
        getSupportActionBar().setTitle(word.getItems()[0]);

        detail.setText(word.getItems()[1]);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
