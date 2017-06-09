package com.example.xenom.opticsmenu.Light.Reflection;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xenom.opticsmenu.R;
/**
 *  Currently not implemented, using this class for various random things
 *
 */
public class LawOfReflection extends Activity {
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_of_reflection);

        textView = (TextView)findViewById(R.id.text);

    }
}
