package shapp.odk.org.shapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainScreen extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }



    @Override
    public void onClick(View view) {

    }

    public void button1(View view) {
        Log.d("Button1", "Report");
    }

    public void button2(View view) {
        Log.d("Button2", "Light");
    }

    public void button3(View view) {
        Log.d("Button3", "Maps");
    }

    public void button4(View view) {
        Log.d("Button4", "Analytics");
    }

    public void button5(View view) {
        Log.d("Button5", "Settings");
    }

    public void button6(View view) {
        Log.d("Button1", "Contact");

    }
}