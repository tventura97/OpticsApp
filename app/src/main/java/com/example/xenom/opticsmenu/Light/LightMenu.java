package com.example.xenom.opticsmenu.Light;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.xenom.opticsmenu.R;

import java.util.ArrayList;

public class LightMenu extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Light");
        setContentView(R.layout.activity_light_menu);
        listView = (ListView)findViewById(R.id.list);
        String [] titles = {"Background", "Fundamental Properties", "Refraction", "Reflection"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);


        listView.setOnClickListener(new View.OnClickListener());
    }
}
