package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the user interface layout for this Activity
        // the layout file is defined in the project res/layout/activity_main.xml file

        setContentView(R.layout.activity_main);
        // if savedinstancState==null, this is the first launch of this activity
        if (savedInstanceState == null) {
            Log.v(MainActivity.class.getSimpleName(),"OnCreate:First start" );
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        } else
            Log.v(MainActivity.class.getSimpleName(),"OnCreate" );
    }
    @Override
    public  void onStart () {
        super.onStart();
        Log.v(MainActivity.class.getSimpleName(),"OnStart:" );
    }
    @Override
    public  void onResume () {
        super.onResume();
        Log.v(MainActivity.class.getSimpleName(),"OnResume:" );
    }
    @Override
    public  void onPause () {
        super.onPause();
        Log.v(MainActivity.class.getSimpleName(),"OnPause:" );
    }
    @Override
    public  void onStop () {
        super.onStop();
        Log.v(MainActivity.class.getSimpleName(),"OnStop:" );
    }
    @Override
    public  void onDestroy() {
        super.onDestroy();
        Log.v(MainActivity.class.getSimpleName(),"onDestroy:" );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


// These will respond to drop menu items - Fragments can also add items to the menu and will inherit the ones declared by their parent Activity
        if (id == R.id.action_doNothing) {

//                Intent launchSettings = new Intent(this, SettingsActivity.class);
//                startActivity(launchSettings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

