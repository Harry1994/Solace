package com.konstantinidis.harry.solace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian3d;
import com.anychart.core.cartesian.series.Area3d;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.hatchfill.HatchFillType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

//        Intent intent = getIntent();
//        StressLevel stressLevel = (StressLevel) intent.getSerializableExtra("StressLevel");

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));


        Cartesian3d area3d = AnyChart.area3d();

        area3d.xAxis(0).labels().format("{%Value}");

        area3d.animation(true);

        area3d.yAxis(0).title("Stress Level");
        area3d.xAxis(0).title("Date");
//        area3d.xAxis(0).labels().padding(5d, 5d, 0d, 5d);


        area3d.title("<b>My stress history</b>");
        area3d.title().useHtml(true);
        area3d.title().padding(10d, 0d, 20d, 0d);

        SharedPreferences appSharedPrefs = getSharedPreferences("sharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("StressLevel" , "");
        StressLevel stressLevel = gson.fromJson(json, StressLevel.class);




        List<DataEntry> seriesData = new ArrayList<>();
//        for (StressLevel sl : stressLevels) {
//            seriesData.add(new CustomDataEntry(sl.rateDate.toString(), sl.initialStressRate, sl.finalStressRate));
//        }
        seriesData.add(new CustomDataEntry("10/11", 162, 42));
        seriesData.add(new CustomDataEntry("10/11", 134, 54));
        seriesData.add(new CustomDataEntry("11/11", 116, 26));
        seriesData.add(new CustomDataEntry("11/11", 122, 32));
        seriesData.add(new CustomDataEntry("12/11", 178, 68));
        seriesData.add(new CustomDataEntry("12/11", 144, 54));
        seriesData.add(new CustomDataEntry("13/11", 176, 66));
        seriesData.add(new CustomDataEntry("13/11", 156, 80));
        seriesData.add(new CustomDataEntry("14/11", 195, 120));
        seriesData.add(new CustomDataEntry("14/11", 190, 115));
        seriesData.add(new CustomDataEntry("15/11", 176, 36));
        seriesData.add(new CustomDataEntry("15/11", 167, 47));
        seriesData.add(new CustomDataEntry("16/11", 142, 72));
        seriesData.add(new CustomDataEntry("16/11", 117, 37));
        seriesData.add(new CustomDataEntry("17/11", 113, 23));
        seriesData.add(new CustomDataEntry("17/11", 132, 30));
        seriesData.add(new CustomDataEntry("18/11", 146, 46));
        seriesData.add(new CustomDataEntry("18/11", 169, 59));
        seriesData.add(new CustomDataEntry("19/11", 184, 44));
        seriesData.add(new CustomDataEntry(stressLevel.getDateString(), stressLevel.initialStressRate, stressLevel.finalStressRate));




        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");

        Area3d series1 = area3d.area(series1Data);
        series1.name("Initial Stress Level");
        series1.hovered().markers(false);
//        series1.hatchFill(HatchFillType.FORWARD_DIAGONAL, "#000", 0.6d, 10d);
        series1.color("#54ffac");

        Area3d series2 = area3d.area(series2Data);
        series2.name("Final Stress Level");
        series2.hovered().markers(false);
//        series2.hatchFill(HatchFillType.BACKWARD_DIAGONAL, "#000", 0.6d, 10d);
        series2.color("#12d274");

        area3d.tooltip()
                .position(Position.CENTER_TOP)
                .positionMode(TooltipPositionMode.POINT)
                .anchor(Anchor.LEFT_BOTTOM)
                .offsetX(5d)
                .offsetY(5d);

        area3d.interactivity().hoverMode(HoverMode.BY_X);
        area3d.zAspect("100%");


        anyChartView.setChart(area3d);
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }
    }
}
