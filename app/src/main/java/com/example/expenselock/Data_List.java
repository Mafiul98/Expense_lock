package com.example.expenselock;

import android.database.Cursor;
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
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Data_List extends AppCompatActivity {

    ListView listview;
    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String,String> hashMap;
    public static String EXPENSE_LOCK = "";
    DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        listview=findViewById(R.id.listview);
        dbhelper = new DataBaseHelper(this);


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
                imageview.setImageResource(R.drawable.income);
            } else if (EXPENSE_LOCK.equals("expense")) {
                imageview.setImageResource(R.drawable.expense);
            }else {
                imageview.setImageResource(R.drawable.savings);
            }


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
                hashMap.put("reason", "-");
                hashMap.put("note", "-");
                hashMap.put("time", time);
                arrayList.add(hashMap);
            }
        }

        listview.setAdapter(new myadapter());

    }

    //=====================================Load Data End=============================================

}