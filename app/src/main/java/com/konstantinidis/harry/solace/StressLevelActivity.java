package com.konstantinidis.harry.solace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.konstantinidis.harry.arcseeker.ProgressListener;
import com.konstantinidis.harry.arcseeker.ArcSeekBar;

import java.util.Date;


public class StressLevelActivity extends AppCompatActivity {

    private int progressRateBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_level);

        ArcSeekBar arcSeekBar = findViewById(R.id.seekArc);
        Button solaceButton = findViewById(R.id.solaceButton);
        TextView questionText = findViewById(R.id.question_text);

        Intent intent = getIntent();
        int progressRate = intent.getIntExtra("ProgressRate", -1);


        arcSeekBar.setMaxProgress(200);
        ProgressListener progressListener = progress -> Log.i("SeekBar", "Value is " + progress);
        progressListener.invoke(0);
        arcSeekBar.setOnProgressChangedListener(progressListener);

        int[] intArray = getResources().getIntArray(R.array.progressGradientColors);
        arcSeekBar.setProgressGradient(intArray);

        if (progressRate != -1){
            questionText.setText(R.string.question_text_after);
            progressRateBefore = progressRate;
            arcSeekBar.setProgress(progressRate);
            solaceButton.setText(R.string.done);
        }

        arcSeekBar.setOnProgressChangedListener(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                Log.i("Progress", "Progress: " + arcSeekBar.getProgress());
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot((arcSeekBar.getProgress() / 30) + 1, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate((arcSeekBar.getProgress() / 30) + 1);
                }
            }
        });

        solaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressRate == -1) {
                    Intent intent = new Intent(StressLevelActivity.this, StressReliefActivity.class);
                    intent.putExtra("ProgressRate", arcSeekBar.getProgress());
                    startActivity(intent);
                    finish();
                } else {
                    StressLevel stressLevel = new StressLevel(progressRateBefore, arcSeekBar.getProgress(), new Date());
//                    List<StressLevel> stressLevels = new ArrayList<>();
//                    stressLevels.add(stressLevel);

                    SharedPreferences appSharedPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(stressLevel);
                    prefsEditor.putString("StressLevel", json);
                    prefsEditor.apply();

                    Intent intent = new Intent(StressLevelActivity.this, HomeActivity.class);
//                    intent.putExtra("StressLevel", stressLevel);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }


}
