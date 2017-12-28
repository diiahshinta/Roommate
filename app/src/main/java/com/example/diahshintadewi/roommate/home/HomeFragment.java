package com.example.diahshintadewi.roommate.home;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.diahshintadewi.roommate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    RecyclerView mRecycler;
    AdapterHostel mAdapter;
    DataHostel mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab, container, false);
        new AsyncLogin().execute();
        return rootView;
    }
    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://api.foursquare.com/v2/venues/search?categoryId=4bf58dd8d48988d1ee931735&client_id=SY0DE1WXUZ4F4T3SZIYSNQLF4IVZKEUUIGU11QDPJSGYZFBC&client_secret=TQMR4RQPKDQRE3P4PJ2CAPYR1EW4AH3C2DIDMKEPP5FCRME1&v=20130815%20&near=Malang,Indonesia");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataHostel> data=new ArrayList<>();

            pdLoading.dismiss();
            try {
                JSONObject jsonobject = new JSONObject(result);
                JSONArray jsonarray = jsonobject.getJSONObject("response").getJSONArray("venues");
                Toast.makeText(HomeFragment.this.getActivity(), "isi array : "+jsonarray.toString(), Toast.LENGTH_SHORT).show();


                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jsonarray.length();i++){
                    JSONObject json_data = jsonarray.getJSONObject(i);
                    DataHostel fishData = new DataHostel();
                    fishData.hName= json_data.getString("name");
                    data.add(fishData);}

                // Setup and Handover data to recyclerview
                mRecycler = (RecyclerView) rootView.findViewById(R.id.listHostel);
                mAdapter = new AdapterHostel(HomeFragment.this.getActivity(), data);
                mRecycler.setAdapter(mAdapter);
                mRecycler.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
