package shapp.odk.org.shapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class NarrativeActivity extends Activity {


    private GPSTrackerButton gpsTrackerButton;
    private static final int RESULT_LOAD_IMG = 1;
    private JSONObject harassmentGeoCoordinates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrative_activity);
        OnClickButtonsListener();

        Switch switchButton = (Switch) findViewById(R.id.onoff);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                gpsTrackerButton = new GPSTrackerButton(NarrativeActivity.this);

                if (isChecked ) {

                    if (!gpsTrackerButton.canGetLocation()) {

                        gpsTrackerButton.showOneButtonDialog();

                    } else {



                        try {
                            //The jsonObject for GPS coordinates
                            harassmentGeoCoordinates = new JSONObject();

                            double latitude = gpsTrackerButton.getLatitude();
                            double longitude = gpsTrackerButton.getLongitude();

                            harassmentGeoCoordinates.put("lat", latitude);
                            harassmentGeoCoordinates.put("lng", longitude);

                            Toast.makeText(getApplicationContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("", "KOSOVO" + harassmentGeoCoordinates);


                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Your Location isn't enable", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // the ClickButtonListener for the Upload and Report Buttons
    private void OnClickButtonsListener(){


        Button uploadButton = (Button) findViewById(R.id.button_photo);
        TableRow tableRow=(TableRow)uploadButton.getParent();
        uploadButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View photo_button) {


                        AlertDialog alertDialog = new AlertDialog.Builder(NarrativeActivity.this).create();
                        alertDialog.setTitle("Hello Photo");
                        //alertDialog.setMessage("Alert message to be shown");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Photo Gallery",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Create intent to Open Image applications like Gallery, Google Photos
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        // Start the Intent
                                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                                    }
                                });

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Take Photo",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(takePicture, 0);
                                    }


                                });
                        alertDialog.show();
                    }
                }
        );


        //Timestamp from device
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final String deviceTime =  simpleDateFormat.format(new Date(System.currentTimeMillis()));



        //Report button (the final one!)
        Button reportButton = (Button) findViewById(R.id.button_report);
        TableRow tableRow2 = (TableRow) reportButton.getParent();
        reportButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View report_button) {


                        // The jsonArray gathering all the jsonObjects
                        JSONArray sendDataToApi = new JSONArray();

                        try{
                            // The "general" reporting harassment data
                            JSONObject reportHarassment = new JSONObject();

                            // The jsonObject for the harassment type and location
                            JSONObject harassmentSuffering = new JSONObject();

                            harassmentSuffering.put("type", Globals.harassmentTypeId);
                            harassmentSuffering.put("location",  Globals.locationId);

                            reportHarassment.put("harassment_suffering", harassmentSuffering);
                            reportHarassment.put("coordinates", harassmentGeoCoordinates);
                            reportHarassment.put("timestamp", deviceTime);

                            sendDataToApi.put(reportHarassment);

                           //System.out.println ("This are the infos:" + sendDataToApi);

                            Toast.makeText(getApplicationContext(), "Thank You! Your report has been saved.", Toast.LENGTH_LONG).show();


                        }catch (JSONException e){
                            e.printStackTrace();

                        }



                    }
                }
        );



    }



}
