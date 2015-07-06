package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class Report extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        GridView gridView = (GridView)findViewById(R.id.gridview);
        // Create the Custom Adapter Object
        MyAdapter myAdapter = null;
        try {
            myAdapter = new MyAdapter(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Set the Adapter to GridView
        gridView.setAdapter(myAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {


            }

        });

    }

    private class MyAdapter extends BaseAdapter
    {
        private List<Item> items = new ArrayList<>();
        private LayoutInflater inflater;

        public MyAdapter(Context context) throws JSONException {

            String harassmentsJsonString = "[{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Verbal&w=300&h=300\", \"id\": \"verbal\", \"name\": \"Verbal\"}," +
                    "{\"imageUrl\": \"https://placeholdit.imgix.net/~text?txtsize=33&txt=Stalking&w=300&h=300\", \"id\": \"stalking\", \"name\": \"Stalking\"}  ]";


            JSONArray harassmentJsonArray= new JSONArray(harassmentsJsonString);

            for (int i = 0; i < harassmentJsonArray.length(); i++){

                JSONObject harassment = harassmentJsonArray.getJSONObject(i);

                String imageUrl = harassment.getString("imageUrl");
                String harassmentName =  harassment.getString("name");
                String harassmentId = harassment.getString("id");

                items.add(new Item(harassmentName, R.drawable.tree1));

                //Log.d("check this", "" +imageUrl);

            }








            inflater = LayoutInflater.from(context);

            // 1. loop through the json array
            // 2. for each json object in the json array: add new item to the grid. Hardcode image for now.
            // 3. Add a third object to the json array (Groping). See if it appears on the grid!
            // 4. Remove hardcoded images: Figure out how to create the button image from the image URL.

           // items.add(new Item("Verbal", R.drawable.nature1));
           // items.add(new Item("Stalking", R.drawable.nature2));



        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;

            if(v == null)
            {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
            }

            picture = (ImageView)v.getTag(R.id.picture);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);


            return v;
        }

        private class Item
        {
            final String name;
            final int drawableId;

            Item(String name, int drawableId)
            {
                this.name = name;
                this.drawableId = drawableId;
            }
        }

    }


}




