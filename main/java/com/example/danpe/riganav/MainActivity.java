package com.example.danpe.riganav;

import android.icu.text.TimeZoneFormat;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.w3c.dom.Text;
import android.view.Display;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = GregorianCalendar.getInstance();
        TableLayout myTable = (TableLayout)findViewById(R.id.TableLayout1);
        myTable.setStretchAllColumns(true);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //get information about system date and time
        int dayOfMonth = c.get(Calendar.DATE);
        int monthOfYear = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        //Bus Row
        TableRow busRow = new TableRow(this);
        myTable.addView(busRow);
        busRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView busText = new TextView(this);
        busText.setText("Bus No 22");
        busText.setTextSize(24);
        busRow.addView(busText);

        TextView leavesInText = new TextView(this);
        leavesInText.setText("Leaves in");
        leavesInText.setTextSize(24);
        busRow.addView(leavesInText);

        try {
            String busFileName = "";
            List<Integer> days = new ArrayList<>();
            List<Integer> months = new ArrayList<>();
            List<Integer> years = new ArrayList<>();
            BufferedReader holidayRead = new BufferedReader(new InputStreamReader(getAssets().open("latviaHolidays.txt")));
            int i = 0;
            boolean isHoliday = false;
            String line;
            while (((line = holidayRead.readLine()) != null) || isHoliday) {
                String[] date=line.split(" ");
                days.add(Integer.parseInt(date[0]));
                months.add(Integer.parseInt(date[1]));
                years.add(Integer.parseInt(date[2]));
                if ((days.get(i) == dayOfMonth) && (months.get(i) == monthOfYear) && (years.get(i) == year)){
                    isHoliday = true;
                }
                i++;
            }
            if (isHoliday) {
                busFileName = "busHolidays.txt";
            } else {
            if (dayOfWeek >=2 && dayOfWeek <= 6) { //checks week day
               busFileName = "busWorkDays.txt";
               //miniBusName = "miniBusWorkDays.txt";
            } else {
               busFileName = "busHolidays.txt";
               //miniBusName = "miniBusHolidays.txt";
            }
            }
            BufferedReader busRead = new BufferedReader(new InputStreamReader(getAssets().open(busFileName)));
            //string builder, string tokenizer, string make
            List<Integer> hours = new ArrayList<Integer>(); //arrayList of hours
            List<Integer> minutes = new ArrayList<Integer>(); //arrayList of minutes
            String hour;
            String[] minute;

            while ((hour = busRead.readLine()) != null) { //fills arrayLists with values
                minute = busRead.readLine().split(" ");
                for (i = 0; i < minute.length; i++) {
                    hours.add(Integer.parseInt(hour));
                    minutes.add(Integer.parseInt(minute[i]));
                }
            }

            int n = 0;
            int currentHour = c.get(c.HOUR_OF_DAY);
            int currentMinute = c.get(c.MINUTE);

            while (currentHour != hours.get(n)) {
                n++;
            }
            while (currentMinute >= minutes.get(n)) {
                n++;
            } // sets correct index for time

            int timeRowCount = n + 3; //number of rows to be added
            TableRow[] timeTableRow = new TableRow[timeRowCount]; //array of rows
            TextView[] timeTextView = new TextView[timeRowCount]; //array of TextViews
            TextView[] leavesInTextView = new TextView[timeRowCount];
            i = 0;

            do {
                timeTableRow[i] = new TableRow(this);
                timeTableRow[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                myTable.addView(timeTableRow[i]); //adds a TableRow
                timeTextView[i] = new TextView(this);

                if (hours.size() < n + 1) {
                    n = 0;
                }
                if (minutes.get(n) < 10) { //makes sure minute values under 10 get printed as strings with a zero
                    timeTextView[i].setText(hours.get(n)+":0"+minutes.get(n));
                } else {

                    timeTextView[i].setText(hours.get(n)+":"+minutes.get(n));
                }//sets Text for specific time of index i
                timeTextView[i].setTextSize(20);
                timeTableRow[i].addView(timeTextView[i]); //adds a TextView to the generated TableRow
                leavesInTextView[i] = new TextView(this);
                if (currentHour - hours.get(n)>1) {
                    leavesInTextView[i].setText("over an hour");
                } else {
                if (currentHour != hours.get(n)) {
                    leavesInTextView[i].setText(60-currentMinute + minutes.get(n)+" min");
                } else {
                    leavesInTextView[i].setText(minutes.get(n) - currentMinute+" min");
                }
                }
                leavesInTextView[i].setTextSize(20);
                //leavesInTextView[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                timeTableRow[i].addView(leavesInTextView[i]);
                i++;
                n++;
            } while ( i < 3);
            busRead.close();
            holidayRead.close();
        } catch (IOException e) {Toast.makeText(MainActivity.this, "FILE READ ERROR", Toast.LENGTH_LONG).show();}

        TextView priceText = new TextView(this);
        priceText.setText("Price");
        priceText.setTextSize(24);

        TextView busPrice = new TextView(this);
        busPrice.setText("2€");
        busPrice.setTextSize(24);

        TableRow priceBusRow = new TableRow(this);
        myTable.addView(priceBusRow);
        priceBusRow.addView(priceText);
        priceBusRow.addView(busPrice);

        TableRow infoRow = new TableRow(this);
        myTable.addView(infoRow);
        TextView empty = new TextView(this);
        infoRow.addView(empty);
        empty.setText("___________________________");
        /**
        Button info = new Button(this);
        info.setText("Details");
        infoRow.addView(info);

        //MiniBus Row
        TableRow miniBusRow = new TableRow(this);
        myTable.addView(miniBusRow);
        miniBusRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView miniBusText = new TextView(this);
        miniBusText.setTextSize(24);
        miniBusText.setText("Minibus No. 241");
        miniBusRow.addView(miniBusText);

        TextView leavesInText2 = new TextView(this);
        leavesInText2.setTextSize(24);
        leavesInText2.setText("Leaves in");
        miniBusRow.addView(leavesInText2);

        TextView priceText2 = new TextView(this);
        priceText2.setTextSize(24);
        priceText2.setText("Price");
        miniBusRow.addView(priceText2); */

        TableRow taxiRow = new TableRow(this);
        myTable.addView(taxiRow);
        taxiRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView nameText = new TextView(this);
        nameText.setTextSize(24);
        nameText.setText("Taxi");
        taxiRow.addView(nameText);

        TextView taxiPrice = new TextView(this);
        taxiPrice.setTextSize(24);
        taxiPrice.setText("Price");
        taxiRow.addView(taxiPrice);

        TableRow balticRow = new TableRow(this);
        myTable.addView(balticRow);
        balticRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView balticText = new TextView(this);
        balticText.setText("Baltic Taxi");
        balticText.setTextSize(20);
        balticRow.addView(balticText);

        TextView balticPrice = new TextView(this);
        balticPrice.setText("11.38€ - 14.23€");
        balticPrice.setTextSize(20);
        balticRow.addView(balticPrice);

        TableRow redCabRow = new TableRow(this);
        myTable.addView(redCabRow);
        redCabRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView redCabText = new TextView(this);
        redCabRow.addView(redCabText);
        redCabText.setText("Red Cab");
        redCabText.setTextSize(20);

        TextView redPrice = new TextView(this);
        redPrice.setText("11.38€ - 14.23€");
        redPrice.setTextSize(20);
        redCabRow.addView(redPrice);


    }
}
