package com.example.expenselock;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Data_Insert extends AppCompatActivity {

    AutoCompleteTextView reasonSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_insert);
        reasonSpinner=findViewById(R.id.reasonSpinner);


        String[] reasons = {"salary", "business", "incentive", "extra", "due"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_dropdown_item,
                reasons
        );

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        reasonSpinner.setAdapter(adapter);

    }
}