package shapp.odk.org.shapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;




public class NarrativeActivity extends Activity {

    private GPSTrackerButton gpsTrackerButton;
    private static final int RESULT_LOAD_IMG = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.narrative_activity);
        OnClickButtonsListener();

        Switch switchButton = (Switch) findViewById(R.id.onoff);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                gpsTrackerButton = new GPSTrackerButton(NarrativeActivity.this);

                if (isChecked) {

                    if (!gpsTrackerButton.canGetLocation()) {

                        gpsTrackerButton.showOneButtonDialog();

                    } else {
                        double latitude = gpsTrackerButton.getLatitude();
                        double longitude = gpsTrackerButton.getLongitude();

                        Log.d("", "LAT" + latitude);

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
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

        //Report button (the final one!)
        Button reportButton = (Button) findViewById(R.id.button_report);
        TableRow tableRow2 = (TableRow) reportButton.getParent();
        reportButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View report_button) {

                        AlertDialog alertDialog = new AlertDialog.Builder(NarrativeActivity.this).create();
                        alertDialog.setTitle("Hello Report");
                        alertDialog.setMessage("Thanks for your report :)!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        alertDialog.show();

                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_LOAD_IMG:
                if(resultCode == Activity.RESULT_OK && data != null && data.getData() != null){

                    try{

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        //return image path to the Narrative Activity
                        Intent returnFromGalleryIntent = new Intent();
                        returnFromGalleryIntent.putExtra("picturePath", picturePath);
                        setResult(RESULT_OK, returnFromGalleryIntent);
                        finish();
                    } catch (Exception e){
                        e.printStackTrace();
                        Intent returnFromGalleryIntent = new Intent();
                        setResult(RESULT_CANCELED, returnFromGalleryIntent);
                        finish();
                    }

                }else {

                    Log.i("","RESULT_CANCELED");
                    Intent returnFromGalleryIntent = new Intent();
                    setResult(RESULT_CANCELED, returnFromGalleryIntent);
                    finish();

                }
                break;   }
    }


}
