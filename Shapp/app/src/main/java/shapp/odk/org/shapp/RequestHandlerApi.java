package shapp.odk.org.shapp;



import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



class RequestHandlerApi extends AsyncTask<String,String, String>{

    private Context contextMain;

    public RequestHandlerApi(Context context){
        this.contextMain = context;
    }



    @Override
    protected String doInBackground(String... url) {

        try{
            URL urlHarassmentApi = new URL("http://10.0.2.2:5000/geo-api");
            HttpURLConnection connMain = (HttpURLConnection)urlHarassmentApi.openConnection();
            connMain.setRequestProperty("Accept", "application/json");
            connMain.setRequestMethod("GET");
            Log.d("Hello",""+connMain);

            /*
            if (connMain.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connMain.getResponseCode());
            }*/

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connMain.getInputStream()));


            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);

            }


            reader.close();



                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(contextMain.openFileOutput("config.json", Context.MODE_PRIVATE));
                    outputStreamWriter.write(response.toString());
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}