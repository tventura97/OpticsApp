package com.example.xenom.opticsmenu.Light;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.xenom.opticsmenu.Light.Reflection.LawOfReflection;
import com.example.xenom.opticsmenu.Light.Refraction.RefractionMenu;
import com.example.xenom.opticsmenu.Light.Refraction.Refraction_Background;
import com.example.xenom.opticsmenu.R;
import com.example.xenom.opticsmenu.UI.SplashActivity;

import java.util.ArrayList;

public class LightMenu extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Light");
        setContentView(R.layout.activity_light_menu);
        listView = (ListView) findViewById(R.id.list);
        String[] titles = {"Background", "Fundamental Properties", "Refraction", "Reflection"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(LightMenu.this, Refraction_Background.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(LightMenu.this, SplashActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(LightMenu.this, RefractionMenu.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(LightMenu.this, LawOfReflection.class);
                        startActivity(intent3);
                        break;

                }

            }
        });
    }
}
