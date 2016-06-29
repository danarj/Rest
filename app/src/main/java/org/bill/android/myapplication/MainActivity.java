package org.bill.android.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreference = getSharedPreferences("sharedPr", MODE_PRIVATE);
        String token = sharedPreference.getString("token", null);
        Toast.makeText(this, "User Token: "+token, Toast.LENGTH_SHORT).show();
        //test comment
    }
}
