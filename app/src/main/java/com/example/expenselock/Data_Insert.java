package com.example.expenselock;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
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
    TextInputLayout layout2,layout3;
    TextView savebutton;
    ImageView backbutton;
    DataBaseHelper dbhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        }WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_data_insert);
        reasonSpinner=findViewById(R.id.reasonSpinner);
        typeSpinner=findViewById(R.id.typeSpinner);
        layout2=findViewById(R.id.layout2);
        layout3=findViewById(R.id.layout3);
        savebutton=findViewById(R.id.savebutton);
        backbutton=findViewById(R.id.backbutton);
        addnote=findViewById(R.id.addnote);
        amount=findViewById(R.id.amount);
        dbhelper =  new DataBaseHelper(this);


//=====================SaveButton vs EditButton=================================================
        Intent intent = getIntent();
        boolean isEditMode = intent.getBooleanExtra("edit_mode", false);
        String id = intent.getStringExtra("id");


        if (isEditMode) {
            double prevAmount = intent.getDoubleExtra("amount", 0);
            String prevType = intent.getStringExtra("type");
            String prevReason = intent.getStringExtra("reason");
            String prevNote = intent.getStringExtra("addnote");

            amount.setText(String.valueOf(prevAmount));
            typeSpinner.setText(prevType);
            reasonSpinner.setText(prevReason);
            addnote.setText(prevNote);

            String[] selectedReasons;
            if (prevType.equals("Income")) {
                selectedReasons = new String[]{"","Salary","T a d a", "Business", "Incentive","Commission", "Others", "Due"};
            } else if (prevType.equals("Expense")) {
                selectedReasons = new String[]{"","Shopping", "Food", "Travel", "Home","Medical", "Personal","Due"};
            } else {
                selectedReasons = new String[]{"","Savings"};
            }

            ArrayAdapter<String> reasonAdapter = new ArrayAdapter<>(
                    this,
                    R.layout.spinner_dropdown_item,
                    selectedReasons
            );
            reasonSpinner.setAdapter(reasonAdapter);

            savebutton.setText("Update");
        }


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValid = true;
                String amountStr = amount.getText().toString().trim();
                String type = typeSpinner.getText().toString().trim();
                String reason = reasonSpinner.getText().toString().trim();
                String note = addnote.getText().toString().trim();

                if (amountStr.isEmpty()) {
                    amount.setError("Insert Amount");
                    isValid = false;
                } else {
                    amount.setError(null);
                }


                if (type.isEmpty()) {
                    layout2.setError("Select Type");
                    isValid = false;
                } else {
                    layout2.setError(null);
                }


                if (reason.isEmpty()) {
                    layout3.setError("Select Reason");
                    isValid = false;
                } else {
                    layout3.setError(null);
                }

                if (note.isEmpty()) {
                    addnote.setError("Please write a note");
                    isValid = false;
                } else {
                    addnote.setError(null);
                }

                if (isValid) {
                    double amount1 = Double.parseDouble(amountStr);

                    if (isEditMode) {
                        if (type.equals("Income")) {
                            dbhelper.editIncome(id, amount1, type, reason, note);
                        } else if (type.equals("Expense")) {
                            dbhelper.editExpense(id, amount1, type, reason, note);
                        } else {
                            dbhelper.editSavings(id, amount1);
                        }
                        Toast.makeText(Data_Insert.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        if (type.equals("Income")) {
                            dbhelper.addIncome(amount1, type, reason, note);
                        } else if (type.equals("Expense")) {
                            dbhelper.addExpense(amount1, type, reason, note);
                        } else {
                            dbhelper.addsavings(amount1);
                        }
                        Toast.makeText(Data_Insert.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                    }


                    typeSpinner.setText("");
                    reasonSpinner.setText("");
                    addnote.setText("");
                    amount.setText("");

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data_added", true);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(Data_Insert.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                }
            }
        });

//=====================SaveButton vs EditButton End=================================================

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//===============================Spinner Start======================================================
        String[] types = {"Income","Expense","Savings"};
        String[] reasons1 = {"","Salary","T a d a", "Business", "Incentive","Commission", "Others", "Due"};
        String[] reasons2 = {"","Shopping", "Food", "Travel", "Home","Medical", "Personal","Due"};
        String[] reasons3 = {"","Savings"};

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
                    ArrayAdapter<String> reasonAdapter1 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons1
                    );
                    reasonSpinner.setAdapter(reasonAdapter1);
                    reasonSpinner.setText("");

                } else if (selectedtype.equals("Expense")) {
                    ArrayAdapter<String> reasonAdapter2 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons2
                    );
                    reasonSpinner.setAdapter(reasonAdapter2);
                    reasonSpinner.setText("");

                }else {
                    ArrayAdapter<String> reasonAdapter3 = new ArrayAdapter<>(
                            Data_Insert.this,
                            R.layout.spinner_dropdown_item,
                            reasons3
                    );
                    reasonSpinner.setAdapter(reasonAdapter3);
                    reasonSpinner.setText("");
                }
            }
        });
//===============================Spinner End======================================================



    }
}