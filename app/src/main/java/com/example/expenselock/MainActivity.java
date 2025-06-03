package com.example.expenselock;

import static android.view.Gravity.START;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_layout;
    MaterialToolbar toolbar;
    NavigationView navigationview;
    TextView tvexpense, tvincome, tvtotalincome,income,expense,tvtotalexpense,tvsavingvalue,tvmainbalance;
    ImageView add;
    DataBaseHelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        }WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.add);
        tvexpense = findViewById(R.id.tvexpense);
        tvincome = findViewById(R.id.tvincome);
        tvtotalexpense=findViewById(R.id.tvtotalexpense);
        tvtotalincome=findViewById(R.id.tvtotalincome);
        tvsavingvalue=findViewById(R.id.tvsavingvalue);
        tvmainbalance=findViewById(R.id.tvmainbalance);
        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationview = findViewById(R.id.navigationview);
        dbhelper = new DataBaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Data_Insert.class));

            }
        });







//============================NavigationView Start==================================================
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer_layout, toolbar,
                R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.policy) {
                    Toast.makeText(MainActivity.this, "Policy", Toast.LENGTH_LONG).show();
                    drawer_layout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
//==============================NavigationView End==================================================

        updateui();

    }

    public void updateui(){


        double totalIncome = dbhelper.getTotalIncome();
        double totalExpense = dbhelper.getTotalExpense();
        double userInputSavings = dbhelper.getTotalsavings();

        double mainBalance = totalIncome - totalExpense - userInputSavings;

        tvtotalincome.setText("Total Income: " + totalIncome);
        tvtotalexpense.setText("Total Expense: " + totalExpense);
        tvmainbalance.setText("" + mainBalance);
        tvsavingvalue.setText("" + userInputSavings);






    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateui();
    }
}