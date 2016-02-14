package tieorange.edu.realmdbtest.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import tieorange.edu.realmdbtest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {


    private BarChart mUiBarChart;
    private View mView;
    Random random = new Random();
    private LineChart mUiLineChart;

    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_month, container, false);
        // Inflate the layout for this fragment


        //setupBarChart();

        setupLineChart();
        return mView;
    }

    public static MonthFragment newInstance(String param1, String param2) {
        MonthFragment fragment = new MonthFragment();
        return fragment;
    }

//    private void setupBarChart() {
//        // create dataset:
//        mUiBarChart = (BarChart) mView.findViewById(R.id.month_bar_chart);
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        ArrayList<String> labels = new ArrayList<String>();
//        for (int i = 0; i < 30; i++) {
//            int pagesCount = random.nextInt(50);
//            entries.add(new BarEntry(pagesCount, i));
//
//            // Defining the X-Axis Labels
//            labels.add(String.valueOf(i + 1));
//        }
//        BarDataSet barDataSet = new BarDataSet(entries, "Прочитав сторінок за день");
//        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
//
//        BarData barData = new BarData(labels, barDataSet);
//        mUiBarChart.setData(barData);
//        mUiBarChart.setDescription("Скільки ти читаєш за місяць?");
//        mUiBarChart.animateXY(5000, 5000);
//
//
//        mUiBarChart.invalidate(); // refresh
//    }

    private void setupLineChart() {
        // create dataset:
        mUiLineChart = (LineChart) mView.findViewById(R.id.month_line_chart);

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int pagesCount = random.nextInt(50);
            entries.add(new Entry(pagesCount, i));

            // Defining the X-Axis Labels
            labels.add(String.valueOf(i + 1));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Pages per day");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        LineData lineData = new LineData(labels, dataSet);

        mUiLineChart.setData(lineData);
        mUiLineChart.setDescription("Per month");
        mUiLineChart.animateXY(5000, 5000);

        LimitLine limitLine = new LimitLine(10f); // LIMIT LINE
        final YAxis axisLeft = mUiLineChart.getAxisLeft();
        axisLeft.addLimitLine(limitLine);

        mUiLineChart.invalidate();


    }


}
