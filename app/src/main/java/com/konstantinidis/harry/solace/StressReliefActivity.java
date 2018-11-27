package com.konstantinidis.harry.solace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.konstantinidis.harry.colorpicker.ColorPickerView;
import com.konstantinidis.harry.colorpicker.builder.ColorPickerClickListener;
import com.konstantinidis.harry.colorpicker.builder.ColorPickerDialogBuilder;
import com.konstantinidis.harry.drawingcanvas.CanvasView;

public class StressReliefActivity extends AppCompatActivity {

    private CanvasView canvasView;
    private RelativeLayout parentView;

    private View root;
    private int currentBackgroundColor = 0xffffffff;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_delete:
                    canvasView.clearCanvas();
                    break;
                case R.id.navigation_done:
                    Intent intent = getIntent();
                    int progressRate = intent.getIntExtra("ProgressRate", -1);

                    Intent intent_new = new Intent(StressReliefActivity.this, StressLevelActivity.class);
                    intent_new.putExtra("ProgressRate", progressRate);
                    startActivity(intent_new);
                    finish();
                    break;
                case R.id.navigation_draw:
                    final Context context = StressReliefActivity.this;

                    ColorPickerDialogBuilder
                            .with(context)
                            .setTitle(R.string.color_dialog_title)
                            .initialColor(currentBackgroundColor)
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .density(12)
                            .setPositiveButton("ok", new ColorPickerClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                    changeBackgroundColor(selectedColor);
                                    if (allColors != null) {
                                        StringBuilder sb = null;

                                        for (Integer color : allColors) {
                                            if (color == null)
                                                continue;
                                            if (sb == null)
                                                sb = new StringBuilder("Color List:");
                                            sb.append("\r\n#" + Integer.toHexString(color).toUpperCase());
                                            canvasView.createNewPath(color);

                                        }

                                    }
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .showColorEdit(true)
                            .setColorEditTextColor(ContextCompat.getColor(StressReliefActivity.this, R.color.colorPrimaryDark))
                            .build()
                            .show();
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_relief);
        root = findViewById(R.id.color_screen);
        changeBackgroundColor(currentBackgroundColor);

        canvasView = new CanvasView(StressReliefActivity.this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvasView.setBackground(getDrawable(R.drawable.ic_mandala));
//        }

        parentView = findViewById(R.id.parentView);
        parentView.addView(canvasView);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void changeBackgroundColor(int selectedColor) {
        currentBackgroundColor = selectedColor;
        root.setBackgroundColor(selectedColor);
    }


}
