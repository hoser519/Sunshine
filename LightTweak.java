package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class LightTweak extends ActionBarActivity {
    private  SeekBar ch0_seek_bar;
    private  TextView ch0_percent_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_tweak);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ch0_setup();
        // Set focus on to Seekbar so we don't auto go to the TextView
        ch0_seek_bar.setFocusable(true);
        ch0_seek_bar.setFocusableInTouchMode(true);
        ch0_seek_bar.requestFocus();
    }

    public void ch0_setup( ){
        ch0_seek_bar = (SeekBar)findViewById(R.id.ch0_seekbar);
        ch0_percent_view =(TextView)findViewById(R.id.ch0_percent_view);
        ch0_percent_view.setText(0 + " %");

        ch0_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String percentProgress = progress + " %";
                ch0_percent_view.setText(percentProgress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
          //      Toast.makeText(LightTweak.this,"SeekBar in StopTracking",Toast.LENGTH_LONG).show();


            }
        });
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
        });

    }
}
