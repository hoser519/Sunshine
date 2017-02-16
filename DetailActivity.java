package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_top, new Fragment_Top())
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_bottom, new Fragment_Btm())
                    .commit();
        }
    }


    static  public  class Fragment_Top extends Fragment {

        public Fragment_Top() {
        }

        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate (savedInstanceState);
            setHasOptionsMenu(true);

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_top, container, false);
            Intent i = getActivity().getIntent();
            String k = i.getStringExtra("DATA_TO_PRINT");
            TextView t = (TextView)rootView.findViewById(R.id.textView2);


            t.setText(k);
            return  rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate (R.menu.detail_activity, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {

                Intent launchSettings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(launchSettings);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    }

    static  public  class Fragment_Btm extends Fragment {

        public Fragment_Btm() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_btm, container, false);
            return  rootView;
        }

    }

    }
