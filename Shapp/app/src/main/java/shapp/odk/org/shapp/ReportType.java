package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReportType extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        /**
         * GridView is a ViewGroup that displays items in a two-dimensional, scrollable grid.
         * please read more at http://developer.android.com/guide/topics/ui/layout/gridview.html
         */

        GridView gridView = (GridView)findViewById(R.id.gridview);
        // Create the Custom Adapter Object
        ReportTypeAdapter reportAdapter = new ReportTypeAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(reportAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent reportLocationScreen = new Intent(getApplicationContext(), ReportLocation.class);


                Context context = getApplicationContext();
                CharSequence text = "Be brave enough to report the location!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Globals.harassmentTypeId = view.getId();
                startActivity(reportLocationScreen);
            }

        });

    }

    /**
     * should consider implementing cache manager for better performance
     *
     */

    private class ReportTypeAdapter extends BaseAdapter
    {
        private final Context contextReport;
        private final List<String> urlsButtonPics = new ArrayList<>();

        public ReportTypeAdapter(Context contextReport)
        {
            this.contextReport = contextReport;

            try {




                String reportScreen = "";

                try {
                    InputStream inputStream = openFileInput("config.json");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        reportScreen = stringBuilder.toString();
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }




                JSONObject harassmentJsonArrayReport= new JSONObject(reportScreen);
                JSONArray harassmentJsonTypesReport =new JSONArray(harassmentJsonArrayReport.getJSONObject("reports").getJSONArray("types").toString());
                for (int i = 0; i < harassmentJsonTypesReport.length(); i++) {

                    JSONObject harassment = harassmentJsonTypesReport.getJSONObject(i);
                    String imageUrl = harassment.getString("imageUrl");
                    String harassmentName =  harassment.getString("name");
                    String harassmentId = harassment.getString("id");

                    urlsButtonPics.add(imageUrl);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        public int getCount() {
            return urlsButtonPics.size();
        }

        @Override
        public String getItem(int position) {
            return urlsButtonPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CircledImageView view = (CircledImageView) convertView;
            if (view == null) {
                view = new CircledImageView(contextReport);
                view.setId(position);

            }

            // Get the image URL for the current position.
            String url = getItem(position);

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(contextReport) //
                    .load(url) //
                    .placeholder(R.drawable.error) //
                    .error(R.drawable.error) //
                    .fit() //
                    .tag(contextReport) //
                    .into(view);

            return view;
        }


    }

}


