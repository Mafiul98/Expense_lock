package com.example.expenselock;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PieChart chart;
    TextView tvexpense,tvincome,totalincome,totalexpense;
    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));}
        WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_main);
        chart=findViewById(R.id.chart);
        tvexpense=findViewById(R.id.tvexpense);
        tvincome=findViewById(R.id.tvincome);
        totalincome=findViewById(R.id.totalincome);
        totalexpense=findViewById(R.id.totalexpense);
        dbhelper = new DataBaseHelper(this);

        updateUi();

    }

    public void updateUi() {
        double income = dbhelper.getTotalIncome();
        double expense = dbhelper.getTotalExpense();
        double balance = income - expense;

        totalincome.setText("BDT: 20000" + income);
        totalexpense.setText("BDT: 8000" + expense);
        chart.setCenterText("Main Balance\n" + balance + " taka");

        updatePieChart(income, expense);
    }

    private void updatePieChart(double totalIncome, double totalExpense) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        if (totalIncome == 0 && totalExpense == 0) {
            entries.add(new PieEntry(1f, "No Data"));
        } else {
            if (totalIncome > 0) {
                entries.add(new PieEntry((float) totalIncome, "Income"));
            }
            if (totalExpense > 0) {
                entries.add(new PieEntry((float) totalExpense, "Expense"));
            }
            float expensePercent = (float) ((totalExpense / totalIncome) * 100);
            float savingsPercent = 100f - expensePercent;

            entries.add(new PieEntry(expensePercent, "Expense"));
            entries.add(new PieEntry(savingsPercent, "Savings"));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Income vs Expense");

        if (totalIncome == 0 && totalExpense == 0) {
            pieDataSet.setColors(Color.LTGRAY);
            pieDataSet.setValueTextColor(Color.TRANSPARENT);
        } else {
            // আলাদা রঙ দিতে চাইলে নিজেই set করতে পারিস
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.rgb(76, 175, 80)); // Green for income
            colors.add(Color.rgb(244, 67, 54)); // Red for expense
            colors.add(Color.parseColor("#e67e22"));
            colors.add(Color.parseColor("#1e8449"));
            pieDataSet.setColors(colors);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueFormatter(new PercentFormatter(chart));
        }

        pieDataSet.setValueTextSize(14f);

        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
        chart.setUsePercentValues(true);
        chart.setDrawEntryLabels(true);
        chart.getDescription().setEnabled(false);

        double balance = totalIncome - totalExpense;
        chart.setCenterText("Balance\n" + (int) balance + "৳");
        chart.setCenterTextColor(Color.BLACK);
        chart.setCenterTextSize(15f);
        chart.setEntryLabelTextSize(10f);
        chart.setEntryLabelColor(Color.BLACK);
        chart.animateY(1000);
        chart.invalidate();
    }

}