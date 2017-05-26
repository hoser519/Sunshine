package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    ArrayAdapter<String> mForcastAdapter;
    ArrayList<String> ListOfStuff;

    public ForecastFragment() {
    }
    public ArrayAdapter<String> getmForcastAdapter() {
        return mForcastAdapter;
    }
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void	onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate (R.menu.forcastfragment, menu);

    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item)  {

        int id = item.getItemId();

        //  the ID of each menu item is specified in the R.menu.xml_file, the onCreateOptionsMenu adds
//        menu items in addition to those created in the parent Activity, which overide items in the fragment class
//                if they have the same ID. asdasdasdasd

        if (id == R.id.action_refresh) {
            Log.v(ForecastFragment.class.getSimpleName(),"`Refreshing!" );
            DownloadFilesTask m =  new DownloadFilesTask();
            m.execute();

            return true;

        } else if (id == R.id.action_settings) {

            Intent launchSettings = new Intent(getActivity(), SettingsActivity.class);
            startActivity(launchSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState==null)
            Log.v(ForecastFragment.class.getSimpleName(),"OnCreateView:Fragment first start" );
        else
            Log.v(ForecastFragment.class.getSimpleName(),"OnCreateView:" );

        ListOfStuff = new ArrayList<>();

        ListOfStuff.add("Item 1");
        ListOfStuff.add("Item 2");
        ListOfStuff.add("Item 3");
        ListOfStuff.add("Item 4");

        // Get one of the list items from our preferences
        // Get all the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // Get the  value (String) of the preference associated
       String valOfPref = prefs.getString(getString(R.string.key_edittextpref), "keyNotFound");

       ListOfStuff.add(valOfPref);

      //  ArrayList<String> StuffToList = new ArrayList<String>(Arrays.asList((String[])ListOfStuff.toArray()));

        mForcastAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_text_frame,
                R.id.list_item_textview,
                ListOfStuff);

        ListView A = (ListView)rootView.findViewById(R.id.listView);
        A.setAdapter(mForcastAdapter);
        A.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                Context context = getActivity();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;
//
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("DATA_TO_PRINT", "DATA I WANT TO PRINT");
            startActivity(i);


            }

        });

//            new DownloadFilesTask().execute(null, null, null);

        return rootView;

    }
    @Override
     public  void onStart () {
        super.onStart();
        Log.v(ForecastFragment.class.getSimpleName(),"OnStart:" );
    }
    @Override
    public  void onResume () {
        super.onResume();
        Log.v(ForecastFragment.class.getSimpleName(),"OnResume:" ); // Get one of the list items from our preferences
        // Get all the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // Get the  value (String) of the preference associated
        String newPrevVal = prefs.getString(getString(R.string.key_edittextpref), "keyNotFound");
        Log.v(ForecastFragment.class.getSimpleName(),"OnResume: ListOfStuff size=" +  ListOfStuff.size() + " newPrevVa=" + newPrevVal ); // Get one of the list items from our preferences
        // Index of ArrayList starts at 0.
       ListOfStuff.set(ListOfStuff.size()-3, newPrevVal);
    }
    @Override
    public  void onPause () {
        super.onPause();
        Log.v(ForecastFragment.class.getSimpleName(),"OnPause:" );
    }
    @Override
    public  void onStop () {
        super.onStop();
        Log.v(ForecastFragment.class.getSimpleName(),"OnStop:" );
    }
    @Override
    public  void onDestroyView() {
        super.onDestroyView();
        Log.v(ForecastFragment.class.getSimpleName(),"onDestroyView:" );
    }
    @Override
    public  void onDestroy() {
        super.onDestroy();
        Log.v(ForecastFragment.class.getSimpleName(),"onDestroy:" );
    }

    class DownloadFilesTask extends AsyncTask<Void, Void, String> {


         @Override
         protected void onPostExecute(String result) {
             mForcastAdapter.clear();
             mForcastAdapter.add(result);
          }

              protected String doInBackground(Void... Params) {

              // These two need to be declared outside the try/catch
              // so that they can be closed in the finally block.
  //            HttpURLConnection urlConnection = null;
  //            BufferedReader reader = null;
  //
  //            // Will contain the raw JSON response as a string.
  //            String forecastJsonStr = null;
   //           mForcastAdapter
              try {
  //                // Construct the URL for the OpenWeatherMap query
  //                // Possible parameters are avaiable at OWM's forecast API page, at
  //                // http://openweathermap.org/API#forecast
  //                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
  //
  //                // Create the request to OpenWeatherMap, and open the connection
  //                urlConnection = (HttpURLConnection) url.openConnection();
  //                urlConnection.setRequestMethod("GET");
  //                urlConnection.connect();
  //
  //                // Read the input stream into a String
  //                InputStream inputStream = urlConnection.getInputStream();
   //               StringBuffer buffer = new StringBuffer();
  //                if (inputStream == null) {
  //                    // Nothing to do.
  //                    return null;
  //                }
  //                reader = new BufferedReader(new InputStreamReader(inputStream));
  //
  //                String line;
  //                while ((line = reader.readLine()) != null) {
  //                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
  //                    // But it does make debugging a *lot* easier if you print out the completed
  //                    // buffer for debugging.
  //                    buffer.append(line + "\n");
  //                }
  //
  //                if (buffer.length() == 0) {
  //                    // Stream was empty.  No point in parsing.
  //                    return null;
  //                }
  //                forecastJsonStr = buffer.toString();
                  Log.v(ForecastFragment.class.getSimpleName(),"NETWORK ACTIVITY..." );
                  return new String("Result of network operation");
                  //return null;
              } catch (Exception e) {
                  Log.e("ForecastFragment", "Error ", e);
                  // If the code didn't successfully get the weather data, there's no point in attemping
                  // to parse it.
                  return null;
              } finally{
              //    Log.e("ForecastFragment", "Error ");

  //                if (urlConnection != null) {
  //                    urlConnection.disconnect();
  //                }
  //                if (reader != null) {
  //                    try {
  //                        reader.close();
  //                    } catch (final IOException e) {
  //                        Log.e("ForecastFragment", "Error closing stream", e);
  //                    }
  //                }
              }
             //     return null;

              }

  }


}
