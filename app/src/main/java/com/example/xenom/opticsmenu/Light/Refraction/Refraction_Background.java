package com.example.xenom.opticsmenu.Light.Refraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xenom.opticsmenu.R;

public class Refraction_Background extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refraction__background);

        textView =(TextView)findViewById(R.id.text);

        textView.setText("Hello, World!");
    }
}
