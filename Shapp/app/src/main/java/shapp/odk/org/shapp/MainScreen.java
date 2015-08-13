package shapp.odk.org.shapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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

        RequestHandlerApi requestHandlerApi = new RequestHandlerApi(this);
        requestHandlerApi.execute("http://10.0.2.2:5000/geo-api");
        //Log.d("WHAT?!",""+requestHandlerApi);
        requestHandlerApi.getStatus();



        // Handling touch/click Event on GridView Item

        /**Interface definition for a callback to be invoked when an item in this AdapterView has been clicked.
         * please read more at http://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
         */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {



                switch (i) {

                    /*
                     * An Intent provides a facility for performing late runtime binding between the code in different applications.
                     * Its most significant use is in the launching of activities, where it can be thought of as the glue between activities.
                     * It is basically a passive data structure holding an abstract description of an action to be performed.
                     * please read more at http://developer.android.com/reference/android/content/Intent.html
                     *
                     *
                     * method getApplicationContext():
                     *  Return the context of the single, global Application object of the current process.
                     *
                     * I've used switch because I know that this screen for a long time will have the same
                     * content so it's something predefined. :)!
                   */
                    case 0:
                        Intent reportButton = new Intent(getApplicationContext(), ReportType.class);
                        Context context = getApplicationContext();
                        CharSequence text = "What is your harassment suffer?";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        startActivity(reportButton);
                        //Log.d("REPORT ","BUTTON "+ reportButton);
                        break;

                    case 1:
                        Intent lightButton = new Intent(getApplicationContext(), Light.class);
                        startActivity(lightButton);
                        //Log.d("LIGHT ", "BUTTON " + lightButton);
                        break;

                    case 2:
                        Intent mapsButton = new Intent(getApplicationContext(), Maps.class);
                        startActivity(mapsButton);
                        //Log.d("MAPS ", "BUTTON " + mapsButton);
                        break;

                    case 3:
                        Intent analyticsButton = new Intent(getApplicationContext(), Analytics.class);
                        startActivity(analyticsButton);
                        //Log.d("ANALYTICS ", "BUTTON " + analyticsButton);
                        break;

                    case 4:
                        Intent settingsButton = new Intent(getApplicationContext(), Settings.class);
                        startActivity(settingsButton);
                        //Log.d("SETTINGS ", "BUTTON " + settingsButton);
                        break;

                    case 5:
                        Intent contactButton = new Intent(getApplicationContext(), Contact.class);
                        startActivity(contactButton);
                        //Log.d("CONTACT ", "BUTTON" + contactButton);
                        break;
                }
            }

        });
    }


    /*
    * What is a Adapter?
    * An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
    * The Adapter provides access to the data items.
    * The Adapter is also responsible for making a View for each item in the data set.
    * What is a BaseAdapter?
    * BaseAdapter is an abstract base class for the Adapter interface to simplify implementing adapters.
    * You could implement your own, but the framework provides some pretty flexible adapters already.
    * Some popular adapters are:
    * ArrayAdapter,CursorAdapter, SimpleCursorAdapter. Please read more at this awesome comment:
    * (first one)
    * http://stackoverflow.com/questions/4799380/understanding-baseadapters-and-how-to-use-them
    *
    * */

    private class MainScreenAdapter extends BaseAdapter
    {
        /* //Just some basics explanation for why i used final..
        *    static means "not related to a particular instance at all" - final means you cannot change this value after
        initialization
        *       and this value must be initialized.
        *   The combination of final and static gives you the ability to create constants. This is no longer recommended in a public way
        *   (totally ok for e.g. magic numbers in a private context) as it's not type safe.
        *
        *
        *  What is a Context?
        *  As the name suggests, its the context of current state of the application/object. It lets newly created objects understand what has been going on.
        *  Typically you call it to get information regarding another part of your program (activity, package/application)
        *  please read more: http://stackoverflow.com/questions/3572463/what-is-context-in-android (first and second comments :)!
        * */


        private List<Item> items  = new ArrayList<>();
        private LayoutInflater inflaterMainScreen;

        public MainScreenAdapter(Context contextReport){

            inflaterMainScreen = LayoutInflater.from(contextReport);

            items.add(new Item("ReportButton", R.drawable.report));
            items.add(new Item("LightButton", R.drawable.light));
            items.add(new Item("MapsButton", R.drawable.maps));
            items.add(new Item("AnalyticsButton", R.drawable.analytics));
            items.add(new Item("SettingsButton", R.drawable.settings));
            items.add(new Item("ContactButton", R.drawable.contact));

        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).drawableId;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View v = view;
            ImageView picture;

            if(v == null)
            {
                v = inflaterMainScreen.inflate(R.layout.gridview_item, parent, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            Item item = (Item)getItem(position);
            picture.setImageResource(item.drawableId);

            return v;
        }

        private class Item{
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