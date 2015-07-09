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


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        GridView gridView = (GridView)findViewById(R.id.gridview);
        // Create the Custom Adapter Object
        MainScreenAdapter mainScreenAdapter = new MainScreenAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(mainScreenAdapter);


        // Handling touch/click Event on GridView Item

        /**Interface definition for a callback to be invoked when an item in this AdapterView has been clicked.
         * please read more at http://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
         */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                switch(i){
                    case 0:
                        Intent intent1 = new Intent(getApplicationContext(), Report.class);
                        startActivity(intent1);
                        Log.d("REPORT ","BUTTON "+ intent1);
                        break;

                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), Light.class );
                        startActivity(intent2);
                        Log.d("LIGHT ", "BUTTON " + intent2);
                        break;

                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), Maps.class );
                        startActivity(intent3);
                        Log.d("MAPS ", "BUTTON " + intent3);
                        break;

                    case 3:
                        Intent intent4 = new Intent(getApplicationContext(), Analytics.class );
                        startActivity(intent4);
                        Log.d("ANALYTICS ", "BUTTON " + intent4);
                        break;

                    case 4:
                        Intent intent5 = new Intent(getApplicationContext(), Settings.class );
                        startActivity(intent5);
                        Log.d("SETTINGS ", "BUTTON " + intent5);
                        break;

                    case 5:
                        Intent intent6 = new Intent(getApplicationContext(), Contact.class );
                        startActivity(intent6);
                        Log.d("CONTACT ", "BUTTON" + intent6);
                        break;




                }
            }
        });

    }

    private class MainScreenAdapter extends BaseAdapter
    {
        private final Context contextReport;
        private final List<String> urlsButtonPics = new ArrayList<>();

        public MainScreenAdapter(Context contextMain)
        {
            this.contextReport = contextMain;
            // Ensure we get a different ordering of images on each run.

            //urlsButtonPics.add("https://forums.digitalpoint.com/proxy/V%2FlNRoM4xGeK5YYhZVYcS%2B9mKLD4rQceAt2HpNNZGKK%2BHZpMfoiRYQ7ix2kpUgWra42tnY7BAVsNJQ7S%2FjO7x9qBnsoK7JVSpOe4p9UJ6N8%2FcgLNOQ%3D%3D/image.png");
            //urlsButtonPics.add("https://forums.digitalpoint.com/proxy/V%2FlNRoM4xGeK5YYhZVYcS%2B9mKLD4rQceAt2HpNNZGKK%2BHZpMfoiRYQ7ix2kpUgWra42tnY7BAVsNJQ7S%2FjO7x9qBnsoK7JVSpOe4p9UJ6N8%2FcgLNOQ%3D%3D/image.png");

            String harassmentJsonString = "[{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Report&w=300&h=300\", \"id\": \"report\", \"name\": \"Report\"}," +
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Light&w=300&h=300\", \"id\": \"light\", \"name\": \"Light\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Maps&w=300&h=300\", \"id\": \"maps\", \"name\": \"Maps\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Analytics&w=300&h=300\", \"id\": \"analytics\", \"name\": \"Analytics\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Settings&w=300&h=300\", \"id\": \"settings\", \"name\": \"Settings\"},"+
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Contact&w=300&h=300\", \"id\": \"contact\", \"name\": \"Contact\"}]" ;

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