package com.riders.testing.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.riders.testing.R;
import com.riders.testing.helpers.ChartBuilderHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighChartsActivity extends AppCompatActivity {

    private static final String TAG = HighChartsActivity.class.getSimpleName();
    private Context context;

    @BindView(R.id.hc)
    HIChartView chartView;

    @BindView(R.id.load_new_data_button)
    Button refreshDataButton;

    @BindView(R.id.exit_button)
    Button exitButton;

    HIChart chart;
    String chartType = "pie";
    HIOptions options;
    HITitle title;
    HIYAxis hiyAxis;
    HILegend legend;
    HIExporting exporting;
    HIPie pie;

    private ArrayList<Object> dummyNames;
    private ArrayList<Integer> dummyNumbers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highcharts);

        ButterKnife.bind(this);

        prepareData();

        if (null == chartView) {
            Log.e(TAG, "Chart view is null");
            return;
        }
        loadChart(false);

    }

    @OnClick(R.id.load_new_data_button)
    public void refreshData() {
        Log.e(TAG, "Reload highcharts");
        loadChart(true);
    }

    @OnClick(R.id.exit_button)
    public void exitApp() {
        Log.e(TAG, "exit testing page");
        onBackPressed();
    }

    public void prepareData() {
        dummyNames = new ArrayList<>();

        dummyNames.add("Lyes");
        dummyNames.add("Michael");
        dummyNames.add("Majd");
        dummyNames.add("Younes");
        dummyNames.add("Thibault");
        dummyNames.add("Wilmer");
        dummyNames.add("Gilles");
        dummyNames.add("Yoann");
        dummyNames.add("Cedric");
        dummyNames.add("Mourad");
    }

    public void loadChart(boolean hasReloadRequest) {

        chart = new HIChart();
        chart.setType(chartType);

        options = new HIOptions();
        options.setChart(chart);

        // Don't need title
        title = new HITitle();
        title.setText("");
        options.setTitle(title);

        exporting = new HIExporting();
        exporting.setEnabled(true);
        options.setExporting(exporting);


        hiyAxis = new HIYAxis();
        hiyAxis.setMin(0);
        hiyAxis.setTitle(title);
        options.setYAxis(new ArrayList() {{
            add(hiyAxis);
        }});


        legend = new HILegend();
        legend.setLayout("vertical");
        options.setLegend(legend);

        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);

        pie = new HIPie();
        pie.setName("Response");

        // Load chart using my method (wip)
        loadData(hasReloadRequest);

        // Load chart using docs method (works)
//        useHighChartsDocsMethod();
    }


    public void loadData(boolean hasReloadRequest) {

        // Iterate on each dummy element in order to
        // set data according to the real data size
        /*ArrayList<HashMap<String, Object>> pieMapListData = new ArrayList<>();*/

        pie.setData(new ArrayList(new ChartBuilderHelper().createPieData(dummyNames)));


        Log.e(TAG, "options setSeries(series)");
        options.setSeries(new ArrayList(Arrays.asList(pie)));
        Log.e(TAG, "chartView setOptions(options)");
        chartView.setOptions(options);

        if (hasReloadRequest) {
            chartView.reload();
        }
    }


    public void useHighChartsDocsMethod() {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name", "Microsoft Internet Explorer");
        map1.put("y", 56.33);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name", "Chrome");
        map2.put("y", 24.03);
        map2.put("sliced", true);
        map2.put("selected", true);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("name", "Firefox");
        map3.put("y", 10.38);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("name", "Safari");
        map4.put("y", 4.77);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("name", "Opera");
        map5.put("y", 0.91);

        HashMap<String, Object> map6 = new HashMap<>();
        map6.put("name", "Proprietary or Undetectable");
        map6.put("y", 0.2);

        pie.setData(new ArrayList<>(Arrays.asList(map1, map2, map3, map4, map5, map6)));

        Log.e(TAG, "options setSeries(series)");
        options.setSeries(new ArrayList(Arrays.asList(pie)));
        chartView.setOptions(options);
    }
}
