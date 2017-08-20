package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class TopLevel extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                                                    NetworkIOCallback {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // Keep a reference to the NetworkFragment which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mConnected = false;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    // Call back implementation for NetworkIOCallback

    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            Log.v(TopLevel.class.getSimpleName(),"Received from Server="+result);
        } else {
            Log.v(TopLevel.class.getSimpleName(),"Received from Server= Non");

        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                //            mDataText.setText("" + percentComplete + "%");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }
    @Override
    public void cancelNetworkIO() {
        mConnected = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.stopNetworkIO();
        }
    }


    // Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top_level);

            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getString(R.string.app_name);
            Log.v(TopLevel.class.getSimpleName(), "getTitle=" + mTitle);

        //    restoreActionBar();

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));

        Log.v(TopLevel.class.getSimpleName(),"onCreate=");

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "192.168.0.65", 9000);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TopLevel.class.getSimpleName(),"onStart=");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TopLevel.class.getSimpleName(),"onRestart=");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TopLevel.class.getSimpleName(),"onResume=");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TopLevel.class.getSimpleName(),"onPause=");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TopLevel.class.getSimpleName(),"onStop=");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TopLevel.class.getSimpleName(),"onDestroy=");

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
        if (id == R.id.action_settings) {
            Intent launchSettings = new Intent(this, SettingsActivity.class);
            startActivity(launchSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    // This is called by NavigationDrawerFragment each time it's created, or a drawer is selected. Whe device rotated this activity is recreated
    // and this function is run again - so we need to detect if the fragment already exists (was created previously) in the fragment manager (the backstack). Otherwise
    // this will overwrite the previous running fragmetn (which is restored automatically from backstack) and we will lose saved states.
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavigationDrawerContentFragment newContent = null;

        Class fragmentClass;
        switch (position) {
            case LightingPowerLevel.DRAWER_MENU_ITEM_NUM:
                newContent =
                        (NavigationDrawerContentFragment)fragmentManager.findFragmentByTag(LightingPowerLevel.TAG);
                if (newContent==null)
                    newContent = LightingPowerLevel.newInstance(position);
                mTitle = getString(R.string.title_section1);
                break;
            default:
                newContent =
                        (NavigationDrawerContentFragment)fragmentManager.findFragmentByTag(LightingPowerLevel.TAG);
                if (newContent==null)
                    newContent = LightingPowerLevel.newInstance(position);
                mTitle = getString(R.string.title_section1);


        }

        //onSectionAttached(position);
        restoreActionBar();

        Log.v(TopLevel.class.getSimpleName(),"Positio="+position+ "Fragment TAG=" + newContent.getTag() + "getFormalName=" + newContent.getFormalName());
        if (newContent!=null)
            fragmentManager.beginTransaction()
                .replace(R.id.container, newContent, newContent.getFormalName())
                .commit();


        // A previous instance of this Fragment was not found, so create a new one
    /*    if (newContent == null) {
            try {
                newContent = (NavigationDrawerContentFragment)fragmentClass.newInstance();


            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v(TopLevel.class.getSimpleName(), "Fragment TAG=" + newContent.getTag() + "getFormalName=" + newContent.getFormalName());

            fragmentManager.beginTransaction()
                    .replace(R.id.container, newContent, newContent.getFormalName())
                    .commit();
        }*/
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case LightingPowerLevel.DRAWER_MENU_ITEM_NUM:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }







}



/**
 * A placeholder fragment containing a simple view.
 */
/*
    public static class PlaceholderFragment extends Fragment {
        *//**
 * The fragment argument representing the section number for this
 * fragment.
 *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
 * Returns a new instance of this fragment for the given section
 * number.
 *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_top_level, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TopLevel) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
package com.example.mike.myapplication;

        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}*/
