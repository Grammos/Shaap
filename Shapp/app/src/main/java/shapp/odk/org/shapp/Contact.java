package shapp.odk.org.shapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        ActionBar appCompatActivity = getSupportActionBar();


        if (appCompatActivity != null) {
            appCompatActivity.setHomeButtonEnabled(true);
            appCompatActivity.setDisplayHomeAsUpEnabled(true);
        }


    }
}

