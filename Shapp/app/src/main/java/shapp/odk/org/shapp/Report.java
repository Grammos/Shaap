package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            // Ensure we get a different ordering of images on each run.

            //urlsButtonPics.add("https://forums.digitalpoint.com/proxy/V%2FlNRoM4xGeK5YYhZVYcS%2B9mKLD4rQceAt2HpNNZGKK%2BHZpMfoiRYQ7ix2kpUgWra42tnY7BAVsNJQ7S%2FjO7x9qBnsoK7JVSpOe4p9UJ6N8%2FcgLNOQ%3D%3D/image.png");
            //urlsButtonPics.add("https://forums.digitalpoint.com/proxy/V%2FlNRoM4xGeK5YYhZVYcS%2B9mKLD4rQceAt2HpNNZGKK%2BHZpMfoiRYQ7ix2kpUgWra42tnY7BAVsNJQ7S%2FjO7x9qBnsoK7JVSpOe4p9UJ6N8%2FcgLNOQ%3D%3D/image.png");

            String harassmentJsonString = "[{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Verbal&w=300&h=300\", \"id\": \"verbal\", \"name\": \"Verbal\"}," +
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Stalking&w=300&h=300\", \"id\": \"stalking\", \"name\": \"Stalking\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Groping&w=300&h=300\", \"id\": \"groping\", \"name\": \"Groping\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Assault&w=300&h=300\", \"id\": \"assault\", \"name\": \"Assault\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Flashing&w=300&h=300\", \"id\": \"flashing\", \"name\": \"Flashing\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Racism&w=300&h=300\", \"id\": \"racism\", \"name\": \"Racism\"}]" ;

            try {

                JSONArray harassmentJsonArray= new JSONArray(harassmentJsonString);

                for (int i = 0; i < harassmentJsonArray.length(); i++) {

                    JSONObject harassment = harassmentJsonArray.getJSONObject(i);
                    String imageUrl = harassment.getString("imageUrl");
                    String harassmentName =  harassment.getString("name");
                    String harassmentId = harassment.getString("id");
                    urlsButtonPics.add(imageUrl);

                    //Log.d("hello",""+ imageUrl);
                    //Log.d("hello",""+ harassmentName);
                    //Log.d("hello",""+ harassmentId);


                }

                //urlsButtonPics.add(harassmentJsonString);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Triple up the list.
            //ArrayList<String> copy = new ArrayList<String>(urlsButtonPics);
            //.addAll(copy);
            //urlsButtonPics.addAll(copy);
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


