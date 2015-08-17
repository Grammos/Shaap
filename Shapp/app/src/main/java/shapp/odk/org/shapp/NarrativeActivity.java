package shapp.odk.org.shapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class NarrativeActivity extends Activity {

    private GPSTrackerButton gpsTrackerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrative_activity);

        Switch switchButton = (Switch) findViewById(R.id.onoff);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                gpsTrackerButton = new GPSTrackerButton(NarrativeActivity.this);

                if (isChecked) {

                    if(!gpsTrackerButton.canGetLocation()) {

                        gpsTrackerButton.showOneButtonDialog();

                    }else {
                        double latitude = gpsTrackerButton.getLatitude();
                        double longitude = gpsTrackerButton.getLongitude();

                        Log.d("","LAT"+latitude);

                         // \n is for new line
                         Toast.makeText(getApplicationContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Your Location isn't enable", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
