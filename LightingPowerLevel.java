package com.example.android.sunshine.app;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightingPowerLevel extends NavigationDrawerContentFragment
                                                     {

    // Keep a reference to the NetworkFragment which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mConnected = false;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public final static String TAG = "LightingPowerLevel";
    public static final int DRAWER_MENU_ITEM_NUM = 0;

    private SeekBar ch0SeekBar;
    private int ch0Pwr =0;

    private TextView ch0_percent_view;
    private View ch0PwrSeekbar;

//    private ProgressBar spinner;



     public String getFormalName(){
        return TAG;
    }

    public LightingPowerLevel() {
        // Required empty public constructor
    }





    public static LightingPowerLevel newInstance(int sectionNumber) {

        LightingPowerLevel fragment = new LightingPowerLevel();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    // Fragment lifecycle

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TopLevel) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LightingPowerLevel.class.getSimpleName(),"onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ch0PwrSeekbar = inflater.inflate(R.layout.fragment_lighting_power_level, container, false);

        Log.v(LightingPowerLevel.class.getSimpleName(),"onCreateView");

        ch0SeekBar =(SeekBar) ch0PwrSeekbar.findViewById(R.id.ch0PwrSeekbar);
        ch0_percent_view =  (TextView) ch0PwrSeekbar.findViewById(R.id.ch0_percent_view);
      //  spinner = (ProgressBar)ch0PwrSeekbar.findViewById(R.id.connectProgressBar);
      //  spinner.setVisibility(View.VISIBLE);


        // Set focus on to Seekbar so we don't auto go to the TextView
        ch0SeekBar.setFocusable(true);
        ch0SeekBar.setFocusableInTouchMode(true);
        ch0SeekBar.requestFocus();
        return ch0PwrSeekbar;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    // We can be sure at  this point that activity.create()
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            ch0Pwr = savedInstanceState.getInt("ch0Pwr");
            Log.v(LightingPowerLevel.class.getSimpleName(),"onActivityCreated restore="+ch0Pwr);


        } else {
            // Only setup if this is not a
            ch0_setup();

        }
        // get out networkFragmnet
        mNetworkFragment = NetworkFragment.getInstance(getFragmentManager());

    }

    @Override
    public void onResume () {
        super.onResume();
      //  ch0SeekBar.setProgress(ch0Pwr);
        Log.v(LightingPowerLevel.class.getSimpleName(),"onResume=");


    }

    @Override
    public void  onStart () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onStart");


    }
    @Override
    public void  onPause () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onPause");


    }
    @Override
    public void  onStop () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onStop");


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ch0Pwr",ch0Pwr);
        //Save the fragment's state here
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
       // ch0_percent_view.setText(44 + " %");
//        ch0SeekBar.setProgress(44);
        ch0SeekBar.setProgress(ch0Pwr);




    }
    public void ch0_setup( ){

        ch0_percent_view.setText(0 + " %");

        ch0SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int startProgress;
            private int lastProgress;
            private boolean justStarted;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String percentProgress = progress + " %";

                Log.v(LightingPowerLevel.class.getSimpleName(), "onProgressChanged: progress="+progress+" justStared="+justStarted+" startProgress="+startProgress+" lastProgress="+lastProgress);

                if (justStarted) {
                    if (progress > startProgress) {
                        Log.v(LightingPowerLevel.class.getSimpleName(), "calling StartNetworkIO 1");
                        mNetworkFragment.startNetworkIO("U");
                    }      else if (progress < startProgress) {
                        Log.v(LightingPowerLevel.class.getSimpleName(), "calling StartNetworkIO 2");
                    mNetworkFragment.startNetworkIO("D");
                    }
                    justStarted = false;
                } else {
                    if (progress > lastProgress) {
                        Log.v(LightingPowerLevel.class.getSimpleName(), "calling StartNetworkIO 3");

                        mNetworkFragment.startNetworkIO("U");
                    } else if (progress < lastProgress) {
                        Log.v(LightingPowerLevel.class.getSimpleName(), "calling StartNetworkIO 4");

                        mNetworkFragment.startNetworkIO("D");
                    }
                }
                lastProgress = progress;

                ch0_percent_view.setText(percentProgress);
                ch0Pwr  = progress;
                //Log.v(LightingPowerLevel.class.getSimpleName(),"setOnSeekBarChangeListener progree="+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.v(LightingPowerLevel.class.getSimpleName(),"onStartTrackingTouch - start ");
                startProgress = seekBar.getProgress();
                justStarted = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.v(LightingPowerLevel.class.getSimpleName(),"onStopTrackingTouch - next power level ="+ch0Pwr);
                //      Toast.makeText(LightTweak.this,"SeekBar in StopTracking",Toast.LENGTH_LONG).show();


            }
        });/*
        ch0_percent_view.addTextChangedListener(new TextWatcher() {
            boolean _ignore = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v(LightTweak.class.getSimpleName(),"OnTextChanged "+i+i1+i2);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.v(LightTweak.class.getSimpleName(),"afterTextChanged length="+editable.length());
                String replacement = "5 %";
                if (_ignore)
                    return;
                _ignore = true;
                editable.replace(0, replacement.length()-1, replacement);



                //ditable.clear();

            }
        });*/

    }



/*
    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            mDataText.setText(result);
        } else {
            mDataText.setText(getString(R.string.connection_error));
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
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
                mDataText.setText("" + percentComplete + "%");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }

    */
}

