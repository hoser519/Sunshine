package com.example.android.sunshine.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class TopLevel extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Log.v(TopLevel.class.getSimpleName(),"onCreate=");

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
    // This is called by NavigationDrawerFragment each time it's created, or a drawer is selected. Whe device rotated this activity is recreated
    // and this function is run again - so we need to detect if the fragment already exists (was created previously) in the fragment manager (the backstack). Otherwise
    // this will overwrite the previous running fragmetn (which is restored automatically from backstack) and we will lose saved states.
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newContent = null;

        // update the main content by replacing fragments
//    /    String s;
//        if(position == 0)
//        {
//            s = LightingPowerLevel.;
//        }
//        else
//        {
//            s = SettingsFragment.TAg;
//        }
//        Fragment fragment;
        Class fragmentClass;
        switch (position) {
            case 1:
//                newContent = new LightingPowerLevel();
                fragmentClass = LightingPowerLevel.class;
                break;
            default:
                fragmentClass = LightingPowerLevel.class;
//                newContent = new LightingPowerLevel();


        }

        newContent = fragmentManager.findFragmentByTag(s);

        try {
            newContent =  (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v(TopLevel.class.getSimpleName(),"Fragment Tag=" + newContent.getTag() + "toString="+newContent.toString());


        String newContentTag = newContent.toString();
        fragmentManager.beginTransaction()
                .replace(R.id.container, newContent, newContentTag)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
















    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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

}
