package tieorange.edu.realmdbtest.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import tieorange.edu.realmdbtest.R;


public class YearFragment extends Fragment {
    //    @Bind(R.id.bar_chart)
    BarChart mUiBarChart;

    private View mView;
    private Random mRandom = new Random();

    public YearFragment() {
        // Required empty public constructor
    }

    public static YearFragment newInstance(String param1, String param2) {
        YearFragment fragment = new YearFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_year, container, false);
        setupBarChart();

        return mView;
    }

    private void setupBarChart() {
        // create dataset:
        mUiBarChart = (BarChart) mView.findViewById(R.id.year_bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int pagesCount = mRandom.nextInt(200 - 20) + 20;
            entries.add(new BarEntry(pagesCount, i));
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Прочитав сторінок за місяць");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        // Defining the X-Axis Labels
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

//        labels.add("NoData");

        BarData barData = new BarData(labels, barDataSet);
        mUiBarChart.setData(barData);
        mUiBarChart.setDescription("Скільки ти читаєш за рік?");
        mUiBarChart.animateXY(5000, 5000);
        mUiBarChart.setClickable(false);


        mUiBarChart.invalidate(); // refresh
    }


}
