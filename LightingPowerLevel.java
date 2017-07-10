package com.example.android.sunshine.app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LightingPowerLevel extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SeekBar ch0_seek_bar;
    private int ch0Pwr =0;

    private TextView ch0_percent_view;
    private View ch0PwrSeekbar;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LightingPowerLevel.class.getSimpleName(),"onCreate=");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ch0PwrSeekbar = inflater.inflate(R.layout.fragment_lighting_power_level, container, false);

        Log.v(LightingPowerLevel.class.getSimpleName(),"onCreateView=");

        ch0_seek_bar =(SeekBar) ch0PwrSeekbar.findViewById(R.id.ch0PwrSeekbar);
        ch0_percent_view =  (TextView) ch0PwrSeekbar.findViewById(R.id.ch0_percent_view);

        ch0_setup();
        // Set focus on to Seekbar so we don't auto go to the TextView
        ch0_seek_bar.setFocusable(true);
        ch0_seek_bar.setFocusableInTouchMode(true);
        ch0_seek_bar.requestFocus();

        return ch0PwrSeekbar;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            ch0Pwr = savedInstanceState.getInt("ch0Pwr");
            Log.v(LightingPowerLevel.class.getSimpleName(),"onActivityCreated restore="+ch0Pwr);

        }
    }

    @Override
    public void onResume () {
        super.onResume();
      //  ch0_seek_bar.setProgress(ch0Pwr);
        Log.v(LightingPowerLevel.class.getSimpleName(),"onResume=");


    }

    @Override
    public void  onStart () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onStart=");


    }
    @Override
    public void  onPause () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onPause=");


    }
    @Override
    public void  onStop () {
        super.onStart();
        Log.v(LightingPowerLevel.class.getSimpleName(),"onStop=");


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
       // ch0_seek_bar.setProgress(44);




    }
    public void ch0_setup( ){

        ch0_percent_view.setText(0 + " %");


        ch0_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String percentProgress = progress + " %";
                ch0_percent_view.setText(percentProgress);
                ch0Pwr  = progress;
                Log.v(LightingPowerLevel.class.getSimpleName(),"setOnSeekBarChangeListener progree="+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.v(LightingPowerLevel.class.getSimpleName(),"onStartTrackingTouch - start ");

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

  //  @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((TopLevel) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }
}
