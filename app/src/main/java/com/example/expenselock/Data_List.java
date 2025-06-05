package com.example.expenselock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Data_List extends AppCompatActivity {

    TextView tvtitle;
    ImageView imageback;
    ListView listview;
    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String,String> hashMap;
    public static String EXPENSE_LOCK = "";
    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        }
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(true);
        setContentView(R.layout.activity_data_list);
        tvtitle=findViewById(R.id.tvtitle);
        imageback =findViewById(R.id.imageback);
        listview=findViewById(R.id.listview);
        dbhelper = new DataBaseHelper(this);


        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();


    }

    //===================Adapter============================================
    public class myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myview = layoutInflater.inflate(R.layout.item,parent,false);
            TextView tvtime = myview.findViewById(R.id.tvtime);
            TextView tvtype = myview.findViewById(R.id.tvtype);
            TextView tvreason = myview.findViewById(R.id.tvreason);
            TextView tvamount = myview.findViewById(R.id.tvamount);
            TextView tvnote = myview.findViewById(R.id.tvnote);
            ImageView imageview = myview.findViewById(R.id.imageview);
            ImageView deletebutton = myview.findViewById(R.id.deletebutton);
            ImageView editbutton = myview.findViewById(R.id.editbutton);


            hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String time = hashMap.get("time");
            String type = hashMap.get("type");
            String reason = hashMap.get("reason");
            String amount = hashMap.get("amount");
            String note = hashMap.get("note");

            tvtime.setText(time);
            tvtype.setText("Type: "+type);
            tvreason.setText("Reason: "+reason);
            tvamount.setText("Amount: "+amount);
            tvnote.setText("Note: "+note);


            long timeInMillis = (long) Double.parseDouble(time);
            Date date = new Date(timeInMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            String formattedDate = sdf.format(date);

            tvtime.setText(formattedDate);


            if (EXPENSE_LOCK.equals("income")){
                tvtitle.setText("Income List");
                imageview.setImageResource(R.drawable.income);
            } else if (EXPENSE_LOCK.equals("expense")) {
                tvtitle.setText("Expense list");
                imageview.setImageResource(R.drawable.expense);
            }else {
                tvtitle.setText("Savings List");
                imageview.setImageResource(R.drawable.savings);
            }

//=====================Delete Button==============================================================

            deletebutton.setOnClickListener(v->{

                if (EXPENSE_LOCK.equals("income")){
                    dbhelper.deleteIncome(id);
                } else if (EXPENSE_LOCK.equals("expense")) {
                    dbhelper.deleteExpense(id);
                }else {
                    dbhelper.deleteSavings(id);
                }

                if (EXPENSE_LOCK.equals("income")){

                    new AlertDialog.Builder(Data_List.this)
                            .setTitle("Delete Income Data")
                            .setMessage("Are you sure")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbhelper.deleteIncome(id);
                                    loadData();
                                    Toast.makeText(Data_List.this,"Income Deleted",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();

                } else if (EXPENSE_LOCK.equals("expense")) {
                    new AlertDialog.Builder(Data_List.this)
                            .setTitle("Delete Expense Data")
                            .setMessage("Are you sure")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbhelper.deleteExpense(id);
                                    loadData();
                                    Toast.makeText(Data_List.this,"Expense Deleted",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                }else {

                    new AlertDialog.Builder(Data_List.this)
                            .setTitle("Delete Savings Data")
                            .setMessage("Are you sure")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbhelper.deleteSavings(id);
                                    loadData();
                                    Toast.makeText(Data_List.this,"Savings Deleted",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                }


            });

//=====================Delete Button End==============================================================

//=====================Edit Button===================================================================

            editbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Data_List.this,Data_Insert.class);
                    intent.putExtra("edit_mode", true);
                    intent.putExtra("id", id);
                    intent.putExtra("amount", amount);
                    intent.putExtra("type", type);
                    intent.putExtra("reason", reason);
                    intent.putExtra("addnote", note);
                    intent.putExtra("category", EXPENSE_LOCK);
                    startActivity(intent);



                }
            });

//=====================Edit Button End===================================================================




            return myview;
        }
    }
//=====================================Adapter End=============================================
    public void loadData(){
        arrayList = new ArrayList<>();
        Cursor cursor;
        if (EXPENSE_LOCK.equals("income")){
            cursor = dbhelper.getAllIncome();
        } else if (EXPENSE_LOCK.equals("expense")) {
            cursor = dbhelper.getAllExpense();
        } else {
            cursor = dbhelper.getAllSavings();
        }

        if (EXPENSE_LOCK.equals("income") || EXPENSE_LOCK.equals("expense")) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String type = cursor.getString(2);
                String reason = cursor.getString(3);
                String note = cursor.getString(4);
                String time = cursor.getString(5);

                hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("amount", String.valueOf(amount));
                hashMap.put("type", type);
                hashMap.put("reason", reason);
                hashMap.put("note", note);
                hashMap.put("time", time);
                arrayList.add(hashMap);
            }
        } else if (EXPENSE_LOCK.equals("savings")) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String time = cursor.getString(2);


                hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("amount", String.valueOf(amount));
                hashMap.put("type", "Savings"); // Fallback/default value
                hashMap.put("reason", "Savings");
                hashMap.put("note", "-");
                hashMap.put("time", time);
                arrayList.add(hashMap);
            }
        }

        listview.setAdapter(new myadapter());

    }

    //=====================================Load Data End=============================================

}