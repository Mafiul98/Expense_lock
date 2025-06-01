package com.example.expenselock;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class Data_Insert extends AppCompatActivity {

    AutoCompleteTextView reasonSpinner,typeSpinner;
    TextInputLayout layout3;
    TextView savebutton;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        }
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_data_insert);
        reasonSpinner=findViewById(R.id.reasonSpinner);
        typeSpinner=findViewById(R.id.typeSpinner);
        layout3=findViewById(R.id.layout3);
        savebutton=findViewById(R.id.savebutton);
        backbutton=findViewById(R.id.backbutton);



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//===============================Spinner Start======================================================
        String[] types = {"Income","Expense","Savings"};
        String[] reasons1 = {"","Salary", "Business", "Incentive", "Extra", "Due"};
        String[] reasons2 = {"","Shopping", "Food", "Travel", "Home", "Personal"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_dropdown_item,
                types
        );

        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedtype = typeSpinner.getText().toString();

                if (selectedtype.equals("Income")){
                    layout3.setVisibility(VISIBLE);
                    reasonSpinner.setText("");
                    ArrayAdapter<String> reasonAdapter1 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons1
                    );
                    reasonSpinner.setAdapter(reasonAdapter1);


                } else if (selectedtype.equals("Expense")) {
                    layout3.setVisibility(VISIBLE);
                    reasonSpinner.setText("");
                    ArrayAdapter<String> reasonAdapter2 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons2
                    );
                    reasonSpinner.setAdapter(reasonAdapter2);

                }else {
                    layout3.setVisibility(GONE);
                }
            }
        });
//===============================Spinner End======================================================



    }
}