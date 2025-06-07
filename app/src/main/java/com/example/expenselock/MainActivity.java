package com.example.expenselock;

import static android.view.Gravity.START;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_layout;
    AdView adView;
    MaterialToolbar toolbar;
    NavigationView navigationview;
    ScrollView scrollview,scrollview1;
    ImageView add;
    TextView  tvtotalincome,tvtotalexpense,tvsavingvalue,tvmainbalance,
            tvsalary,tvTada,tvbusiness,tvincentive,tvextra,tvincomedue,tvshopping,tvfood,tvtravel,tvhome,
            tvpersonal,tvexpensedue,tvexpenselist,tvincomelist,tvsavingslist,tvmedical,tvcommission;
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
        tvincomelist=findViewById(R.id.tvincomelist);
        tvexpenselist=findViewById(R.id.tvexpenselist);
        tvsavingslist=findViewById(R.id.tvsavingslist);
        tvmedical=findViewById(R.id.tvmedical);
        tvcommission=findViewById(R.id.tvcommission);
        tvsalary = findViewById(R.id.tvsalary);
        tvTada = findViewById(R.id.tvTada);
        tvbusiness = findViewById(R.id.tvbusiness);
        tvincentive = findViewById(R.id.tvincentive);
        tvextra = findViewById(R.id.tvextra);
        tvincomedue = findViewById(R.id.tvincomedue);
        tvshopping = findViewById(R.id.tvshopping);
        tvfood = findViewById(R.id.tvfood);
        tvtravel = findViewById(R.id.tvtravel);
        tvhome = findViewById(R.id.tvhome);
        tvpersonal = findViewById(R.id.tvpersonal);
        tvexpensedue = findViewById(R.id.tvexpensedue);
        tvtotalexpense=findViewById(R.id.tvtotalexpense);
        tvtotalincome=findViewById(R.id.tvtotalincome);
        tvsavingvalue=findViewById(R.id.tvsavingvalue);
        tvmainbalance=findViewById(R.id.tvmainbalance);
        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationview = findViewById(R.id.navigationview);
        adView=findViewById(R.id.adView);
        scrollview=findViewById(R.id.scrollview);
        scrollview1=findViewById(R.id.scrollview1);
        dbhelper = new DataBaseHelper(this);



// =================Auto Scroolview Start====================================

        Handler handler1 = new Handler();
        int[] direction1 = {1};
        boolean[] isScroll1Enabled = {true};

        Runnable autoScroll1 = new Runnable() {
            @Override
            public void run() {
                if (isScroll1Enabled[0]) {
                    scrollview.scrollBy(0, direction1[0] * 2);

                    if (!scrollview.canScrollVertically(1)) {
                        direction1[0] = -1;
                    } else if (!scrollview.canScrollVertically(-1)) {
                        direction1[0] = 1;
                    }
                }
                handler1.postDelayed(this, 80);
            }
        };
        handler1.postDelayed(autoScroll1, 1000);
//=================Auto Scroolview End====================================

//=================Auto Scroolview1 Start====================================
        Handler handler2 = new Handler();
        int[] direction2 = {1};
        boolean[] isScroll2Enabled = {true};

        Runnable autoScroll2 = new Runnable() {
            @Override
            public void run() {
                if (isScroll2Enabled[0]) {
                    scrollview1.scrollBy(0, direction2[0] * 3); // অন্য স্পিড

                    if (!scrollview1.canScrollVertically(1)) {
                        direction2[0] = -1;
                    } else if (!scrollview1.canScrollVertically(-1)) {
                        direction2[0] = 1;
                    }
                }
                handler2.postDelayed(this, 80);
            }
        };
        handler2.postDelayed(autoScroll2, 1000);
//=================Auto Scroolview1 End====================================

//=======================Scrollview On/Off===================================
        scrollview.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isScroll1Enabled[0] = !isScroll1Enabled[0];
            }
            return false;
        });

        scrollview1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isScroll2Enabled[0] = !isScroll2Enabled[0];
            }
            return false;
        });
//=======================Scrollview On/Off===================================


//=================Auto Scroolview End====================================


//=============================Banner ads Initialize=============================================

        MobileAds.initialize(this, initializationStatus -> {});

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

//=============================Banner ads Initialize End=============================================

//=========================Button Start=========================================================
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Data_Insert.class));

            }
        });

        tvincomelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data_List.EXPENSE_LOCK="income";
                startActivity(new Intent(MainActivity.this,Data_List.class));



            }
        });

        tvexpenselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data_List.EXPENSE_LOCK="expense";
                startActivity(new Intent(MainActivity.this,Data_List.class));



            }
        });

        tvsavingslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Data_List.EXPENSE_LOCK="savings";
                startActivity(new Intent(MainActivity.this,Data_List.class));



            }
        });
//=========================Button End=========================================================

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
                } else if (item.getItemId() == R.id.reset) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("সাবধান!")
                            .setMessage("তুমি কি সত্যিই সব ডেটা ডিলিট করতে চাও?")
                            .setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    drawer_layout.closeDrawer(GravityCompat.START);
                                    Toast.makeText(MainActivity.this, "সব ডেটা মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show();
                                    dbhelper.deleteAllData();
                                    updateui();

                                }
                            })
                            .setNegativeButton("না", null)
                            .show();

                }

                return true;
            }
        });
//==============================NavigationView End==================================================

        updateui();

    }
//===============================Updateui Start============================================
    public void updateui(){


        double totalIncome = dbhelper.getTotalIncome();
        double totalExpense = dbhelper.getTotalExpense();
        double userInputSavings = dbhelper.getTotalsavings();
        double mainBalance = totalIncome - totalExpense - userInputSavings;
        tvtotalincome.setText("Total Income: " + totalIncome);
        tvtotalexpense.setText("Total Expense: " + totalExpense);
        tvmainbalance.setText("" + mainBalance);
        tvsavingvalue.setText("Savings: " + userInputSavings);
//===============Income Reason===================================================
        tvsalary.setText("Salary: "+dbhelper.getIncomeReason("Salary"));
        tvTada.setText("T a d a: "+dbhelper.getIncomeReason("T a d a"));
        tvbusiness.setText("Business: "+dbhelper.getIncomeReason("Business"));
        tvincentive.setText("Incentive: "+dbhelper.getIncomeReason("Incentive"));
        tvcommission.setText("Commission: "+dbhelper.getIncomeReason("Commission"));
        tvextra.setText("Others: "+dbhelper.getIncomeReason("Others"));
        tvincomedue.setText("Due: "+dbhelper.getIncomeReason("Due"));
//===============Income Reason End===================================================
//===============Expense Reason======================================================
        tvshopping.setText("Shopping: "+dbhelper.getExpenseReason("Shopping"));
        tvfood.setText("Food: "+dbhelper.getExpenseReason("Food"));
        tvtravel.setText("Travel: "+dbhelper.getExpenseReason("Travel"));
        tvhome.setText("Home: "+dbhelper.getExpenseReason("Home"));
        tvmedical.setText("Medical: "+dbhelper.getExpenseReason("Medical"));
        tvpersonal.setText("Personal: "+dbhelper.getExpenseReason("Personal"));
        tvexpensedue.setText("Due: "+dbhelper.getExpenseReason("Due"));
//===============Expense Reason End======================================================


    }

//===============================Updateui End============================================
//========================Banner Ads=====================================================
    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateui();
    }
    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    //=========================================Banner Ads End===============================================
}