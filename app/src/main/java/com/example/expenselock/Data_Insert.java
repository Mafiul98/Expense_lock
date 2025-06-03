package com.example.expenselock;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Data_Insert extends AppCompatActivity {

    TextInputEditText amount,addnote;
    AutoCompleteTextView typeSpinner,reasonSpinner;
    TextInputLayout layout3;
    TextView savebutton;
    ImageView backbutton;
    public static boolean EXPENSE_LOCK= true;
    DataBaseHelper dbhelper;

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
        addnote=findViewById(R.id.addnote);
        amount=findViewById(R.id.amount);
        dbhelper =  new DataBaseHelper(this);


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = amount.getText().toString();
                String type = typeSpinner.getText().toString();
                String reason = reasonSpinner.getText().toString();
                String note = addnote.getText().toString();
                double amount1 = Double.parseDouble(value);

                if (type.equals("Income")){
                    dbhelper.addIncome(amount1,type,reason,note);
                } else if (type.equals("Expense")) {
                    dbhelper.addExpense(amount1,type,reason,note);
                }else {
                    dbhelper.addsavings(amount1);
                }

                typeSpinner.setText("");
                reasonSpinner.setText("");
                addnote.setText("");
                amount.setText("");



            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//===============================Spinner Start======================================================
        String[] types = {"Income","Expense","Savings"};
        String[] reasons1 = {"","Salary","T a d a", "Business", "Incentive", "Extra", "Due"};
        String[] reasons2 = {"","Shopping", "Food", "Travel", "Home", "Personal","Due"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_dropdown_item,
                types
        );

        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedtype = types[position];



                if (selectedtype.equals("Income")){
                    layout3.setVisibility(VISIBLE);

                    ArrayAdapter<String> reasonAdapter1 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons1
                    );
                    reasonSpinner.setAdapter(reasonAdapter1);
                    reasonSpinner.setText("");

                } else if (selectedtype.equals("Expense")) {
                    layout3.setVisibility(VISIBLE);
                    ArrayAdapter<String> reasonAdapter2 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons2
                    );
                    reasonSpinner.setAdapter(reasonAdapter2);
                    reasonSpinner.setText("");

                }else {
                    layout3.setVisibility(GONE);
                    reasonSpinner.setAdapter(null);
                    reasonSpinner.setText("");
                }
            }
        });
//===============================Spinner End======================================================



    }
}