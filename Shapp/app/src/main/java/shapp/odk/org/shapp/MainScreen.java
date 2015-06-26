package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



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
        MyAdapter myAdapter = new MyAdapter(this);
        // Set the Adapter to GridView
        gridView.setAdapter(myAdapter);


        // Handling touch/click Event on GridView Item
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

    private class MyAdapter extends BaseAdapter
    {
        private List<Item> items = new ArrayList<>();
        private LayoutInflater inflater;

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);

            items.add(new Item("Image 1", R.drawable.nature1));
            items.add(new Item("Image 2", R.drawable.nature2));
            items.add(new Item("Image 3", R.drawable.tree1));
            items.add(new Item("Image 4", R.drawable.nature3));
            items.add(new Item("Image 5", R.drawable.tree2));
            items.add(new Item("Image 6", R.drawable.nature1));



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