package com.example.preferencesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    Button btnSimplePref;
    Button btnFancyPref;
    TextView txtCaption1;
    Boolean fancyPrefChosen = false;
    View myLayout1Vertical;
    final int mode = Activity.MODE_PRIVATE;
    final String MYPREFS = "MyPreferences_001";

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLayout1Vertical = (View)findViewById(R.id.linLayout1Vertical);
        txtCaption1 = (TextView) findViewById(R.id.txtCaption1);
        txtCaption1.setText("This is a sample line \n"
                + "suggesting the way the UI looks \n"
                + "after you choose your preference");

        mySharedPreferences = getSharedPreferences(MYPREFS, 0);
        myEditor = mySharedPreferences.edit();

        if (mySharedPreferences != null
                && mySharedPreferences.contains("backColor")) {
            applySavedPreferences();
        } else {
            Toast.makeText(getApplicationContext(),
                    "No Preferences found",Toast.LENGTH_LONG).show();
        }
        btnSimplePref = (Button) findViewById(R.id.btnPrefSimple);
        btnSimplePref.setOnClickListener(this);
        btnFancyPref = (Button) findViewById(R.id.btnPrefFancy);
        btnFancyPref.setOnClickListener(this);
    }

    public void onClick(View v) {
        myEditor.clear();
        if (v.getId() == btnSimplePref.getId()) {
            myEditor.putInt("backColor", Color.BLACK);// black background
            myEditor.putInt("textSize", 12); // humble small font
        } else { // case btnFancyPref
            myEditor.putInt("backColor", Color.BLUE); // fancy blue
            myEditor.putInt("textSize", 20); // fancy big
            myEditor.putString("textStyle", "bold"); // fancy bold
            myEditor.putInt("layoutColor", Color.GREEN);//fancy green
        }
        myEditor.commit();
        applySavedPreferences();
    }

    protected void onPause() {
        myEditor.putString("DateLastExecution", new Date().toLocaleString());
        myEditor.commit();
        super.onPause();
    }

    public void applySavedPreferences() {
        int backColor = mySharedPreferences.getInt("backColor",Color.BLACK);
        int textSize = mySharedPreferences.getInt("textSize", 12);
        String textStyle = mySharedPreferences.getString("textStyle", "normal");
        int layoutColor = mySharedPreferences.getInt("layoutColor",Color.DKGRAY);
        String msg = "color " + backColor + "\n"
                + "size " + textSize + "\n"
                + "style " + textStyle;
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        txtCaption1.setBackgroundColor(backColor);
        txtCaption1.setTextSize(textSize);
        if (textStyle.compareTo("normal")==0){
            txtCaption1.setTypeface(null, Typeface.NORMAL);
        }
        else {
            txtCaption1.setTypeface(null, Typeface.BOLD);
        }
        myLayout1Vertical.setBackgroundColor(layoutColor);
    }
}