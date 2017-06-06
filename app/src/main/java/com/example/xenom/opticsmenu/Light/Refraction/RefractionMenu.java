package com.example.xenom.opticsmenu.Light.Refraction;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.xenom.opticsmenu.Light.LightMenu;
import com.example.xenom.opticsmenu.R;

public class RefractionMenu extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refraction_menu);
        listView = (ListView) findViewById(R.id.list);
        String[] titles = {"Background", "Snell Interact", "Snell Quiz", "Reflection"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(RefractionMenu.this, Refraction_Background.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(RefractionMenu.this, Snell_Interact.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(RefractionMenu.this, Snell_Quiz.class);
                        startActivity(intent2);
                        break;

                }

            }
        });
    }
}
