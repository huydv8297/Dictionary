package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dictionary.Controller.ContentManager;
import com.example.dictionary.Controller.DictionaryManager;
import com.example.dictionary.Entity.Dictionary;
import com.example.dictionary.Utils.Algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    TextView editText;
    public List<BaseAdapter> adapterList = new ArrayList<>();
    ListView dictionaryList;
    List<String> items = new ArrayList<>();
    Fragment fragment;
    ContentManager contentManager;


    List<Dictionary> listDictionary = new ArrayList<>();
    ImageView cancleButton;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MainActivity", "OnCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ListView listView = findViewById(R.id.history_list);


        sharedPref= getSharedPreferences("dictionary", 0);
        contentManager = new ContentManager(MainActivity.this, listView);
        listDictionary = contentManager.dictionaryManager.getDictionaryList();


        items = contentManager.dictionaryManager.getDictionaryListName();

        cancleButton = findViewById(R.id.cancel_button);
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentManager.Cancel();
                cancleButton.setVisibility(View.GONE);
                editText.setText("");
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        dictionaryList = (ListView) findViewById(R.id.dic_list);
        dictionaryList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        //get pre dictionary
        String currentDictName = sharedPref.getString("currentDictName", "none");
        if(currentDictName.equals("none")){
            contentManager.currentDict = listDictionary.get(0);
        }else{
            int index = items.indexOf(currentDictName);
            contentManager.currentDict = listDictionary.get(index);
        }
        title.setText( contentManager.currentDict.getName());
        contentManager.loadHistory();

        dictionaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title.setText( items.get(position));
                closeNavigation();
                contentManager.saveHistory();
                contentManager.currentDict = listDictionary.get(position);
                contentManager.loadHistory();
                SharedPreferences.Editor  editor = sharedPref.edit();
                editor.putString("currentDictName", contentManager.currentDict.getName());
                editor.commit();
            }
        });

        editText = (EditText) findViewById(R.id.input_text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    Log.e("enter", s.toString());
                    contentManager.Search(contentManager.currentDict.getDbname(), s.toString());
                    cancleButton.setVisibility(View.VISIBLE);
                }else{
                    contentManager.Cancel();
                    cancleButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onPause() {
        contentManager.saveHistory();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void closeNavigation(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void addDictionary(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (data == null) return;

        if (requestCode == 42) {
            try {
                Uri uri = data.getData();
                String dicFileName = Algorithm.FileNameFromUri(data.getData(), false);
                String idxFilePath = Algorithm.ReplaceUriFileName(uri, dicFileName + ".idx");
                String ifoFilePath = Algorithm.ReplaceUriFileName(uri, dicFileName + ".ifo");
                String primaryRoot = Environment.getExternalStorageDirectory().getPath();

                Scanner ifoReader = new Scanner(new FileInputStream(primaryRoot + "/" + ifoFilePath));
                TreeMap<String, String> info = new TreeMap<>();
                while (ifoReader.hasNextLine()) {
                    String line = ifoReader.nextLine();
                    if (line.indexOf("=") < 0) continue;
                    String[] pair = line.split("=", 2);
                    info.put(pair[0], pair[1]);
                }
                ifoReader.close();

                byte[] idxFileBytes = Algorithm.ReadFileToBytes(primaryRoot + "/" + idxFilePath);
                byte[] dicFileBytes = Algorithm.ReadFileToBytes(getContentResolver().openInputStream(uri));

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<String> words = new ArrayList<>();
                        List<Integer> wordOffets = new ArrayList<>();
                        List<Integer> wordSizes = new ArrayList<>();
                        for (int currentIndex = 0; currentIndex < idxFileBytes.length;) {
                            int wordLookaheadIdx = currentIndex;
                            while (idxFileBytes[wordLookaheadIdx] != 0x00) ++wordLookaheadIdx;
                            int wordBytesCount = wordLookaheadIdx - currentIndex;
                            try {
                                String word = new String(idxFileBytes, currentIndex, wordBytesCount, "UTF-8");
                                words.add(word);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            int offsetIdx = wordLookaheadIdx + 1;
                            byte[] offsetBytes = new byte[4];
                            System.arraycopy(idxFileBytes, currentIndex, offsetBytes, 0, 4);
                            // TODO: Implement the rest of the parsing process
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
