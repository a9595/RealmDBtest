package tieorange.edu.realmdbtest.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import tieorange.edu.realmdbtest.Activities.ChartsActivity;
import tieorange.edu.realmdbtest.Helpers.ChartHelper;
import tieorange.edu.realmdbtest.R;


public class YearFragment extends Fragment {
    //    @Bind(R.id.bar_chart)
    BarChart mUiBarChart;

    private View mView;
    private Random mRandom = new Random();
    private Realm mRealm;
    private ChartsActivity mActivity;

    public YearFragment() {
        // Required empty public constructor
    }

    public static YearFragment newInstance() {
        YearFragment fragment = new YearFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_year, container, false);
        mRealm = Realm.getDefaultInstance();
        mActivity = (ChartsActivity) getActivity();
        setupBarChart();

        return mView;
    }

    public void setupBarChart() {
        // create dataset:
        mUiBarChart = (BarChart) mView.findViewById(R.id.year_bar_chart);


        ArrayList<BarEntry> entries = ChartHelper.getChartBarEntriesList(mRealm);
        ArrayList<String> labels = ChartHelper.getChartLabels(entries.size());
//        for (int i = 0; i < 12; i++) {
//            int pagesCount = mRandom.nextInt(200 - 20) + 20;
//            entries.add(new BarEntry(pagesCount, i));
//        }

        BarDataSet barDataSet = new BarDataSet(entries, "Прочитав сторінок за місяць");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

//        // Defining the X-Axis Labels
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//        labels.add("July");
//        labels.add("August");
//        labels.add("September");
//        labels.add("October");
//        labels.add("November");
//        labels.add("December");

//        labels.add("NoData");

        BarData barData = new BarData(labels, barDataSet);
        mUiBarChart.setData(barData);
        mUiBarChart.setDescription("Скільки ти читаєш за рік?");
        mUiBarChart.animateXY(2000, 2000);
        mUiBarChart.setClickable(false);

        // set LIMIT Line
        int goal = mActivity.getBookGoal().getGoal();
        LimitLine limitLine = new LimitLine(goal);
        limitLine.setLineColor(Color.GREEN);
        limitLine.setLabel("Goal");
        limitLine.setLineWidth(2f);
        final YAxis axisLeft = mUiBarChart.getAxisLeft();
        axisLeft.addLimitLine(limitLine);


        // Scale (show only last 7 days)
        int xValuesCount = entries.size(); // amount of x-values
        if (xValuesCount > 7) {
            float scaleX = xValuesCount / 7f; // calculate scale

            mUiBarChart.setScaleMinima(scaleX, 1f); // zoom to first 7 values
            mUiBarChart.moveViewToX(xValuesCount); // move to the end of chart (show last vals)
        }

        mUiBarChart.invalidate(); // refresh
    }


}
