package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Report extends Activity {

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
        ReportAdapter reportAdapter = new ReportAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(reportAdapter);

    }

    /**
     * should consider implementing cache manager for better performance
     *
     */

    private class ReportAdapter extends BaseAdapter
    {
        private final Context contextReport;
        private final List<String> urlsButtonPics = new ArrayList<>();

        public ReportAdapter(Context contextReport)
        {
            this.contextReport = contextReport;

            try {

                //it's not a good choice i don't want to cheat on the "network" UI of the thread
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }


                URL urlHarassmentApi = new URL("http://10.0.2.2:5000/geo-api");
                HttpURLConnection connReport = (HttpURLConnection)urlHarassmentApi.openConnection();

                //add request header
                connReport.setRequestProperty("Accept", "application/json");

                connReport.setRequestMethod("GET");

                //int responseCode = connReport.getResponseCode();

                if (connReport.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + connReport.getResponseCode());
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connReport.getInputStream()));

                String inputLine;


                StringBuffer response = new StringBuffer();


                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);

                }

                reader.close();

                JSONArray harassmentJsonArray= new JSONArray(response.toString());

                for (int i = 0; i < harassmentJsonArray.length(); i++) {

                    JSONObject harassment = harassmentJsonArray.getJSONObject(i);
                    String imageUrl = harassment.getString("imageUrl");
                    String harassmentName =  harassment.getString("name");
                    String harassmentId = harassment.getString("id");

                    urlsButtonPics.add(imageUrl);

                }



            } catch (IOException | JSONException e) {
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

            }

            // Get the image URL for the current position.
            String url = getItem(position);

            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(contextReport) //
                    .load(url) //
                    .placeholder(R.drawable.tree1) //
                    .error(R.drawable.tree2) //
                    .fit() //
                    .tag(contextReport) //
                    .into(view);

            return view;
        }


    }

}


