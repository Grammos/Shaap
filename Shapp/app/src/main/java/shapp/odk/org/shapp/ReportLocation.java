package shapp.odk.org.shapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

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

public class ReportLocation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportlocation);

        /**
         * GridView is a ViewGroup that displays items in a two-dimensional, scrollable grid.
         * please read more at http://developer.android.com/guide/topics/ui/layout/gridview.html
         */

        GridView gridView = (GridView)findViewById(R.id.gridview);
        // Create the Custom Adapter Object
        ReportLocationAdapter reportLocationAdapter = new ReportLocationAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(reportLocationAdapter);

    }

    /**
     * should consider implementing cache manager for better performance
     *
     */

    private class ReportLocationAdapter extends BaseAdapter
    {
        private final Context contextLocationReport;
        private final List<String> urlsButtonPics = new ArrayList<>();

        public ReportLocationAdapter(Context contextReportLocation)
        {
            this.contextLocationReport = contextReportLocation;

            try {




                String reportLocationScreen = "";

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
                        reportLocationScreen = stringBuilder.toString();

                        //Log.d("QAO QAO",""+ret);
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }




                JSONObject harassmentJsonArrayReportLoc= new JSONObject(reportLocationScreen);
                Log.d("KOSOVO",""+harassmentJsonArrayReportLoc);
                JSONArray harassmentJsonTypesLoc =new JSONArray(harassmentJsonArrayReportLoc.getJSONObject("locations").getJSONArray("types").toString());
                for (int i = 0; i < harassmentJsonTypesLoc.length(); i++) {

                    JSONObject harassment = harassmentJsonTypesLoc.getJSONObject(i);
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
                view = new CircledImageView(contextLocationReport);

            }

            // Get the image URL for the current position.
            String url = getItem(position);

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(contextLocationReport) //
                    .load(url) //
                    .placeholder(R.drawable.error) //
                    .error(R.drawable.error) //
                    .fit() //
                    .tag(contextLocationReport) //
                    .into(view);

            return view;
        }


    }

}


