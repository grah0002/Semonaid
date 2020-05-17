package com.gerontechies.semonaid.Activities.Budget.Calculator;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gerontechies.semonaid.Activities.Budget.BudgetMainMenuActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Adapters.TopCategoriesAdapter;
import com.gerontechies.semonaid.Models.Budget.SemonaidDB;
import com.gerontechies.semonaid.Models.Budget.BudgetItem;
import com.gerontechies.semonaid.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;


import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SummaryActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    SemonaidDB db = null;
    List<BudgetItem> item;
    List<BudgetItem> allItemList = new ArrayList<>();
    List<BudgetItem> incomeItemList = new ArrayList<>();
    List<BudgetItem> expenseItemList = new ArrayList<>();
    String category_bills = "Utility Bills", category_personal = "Personal Expenses", category_transport = "Transport Expenses", category_household = "Household Expenses", categotry_income = "Income";
    List<BudgetItem> top3 = new ArrayList<>();
    RecyclerView topList;
    double incomeTotal, expenseTotal, maxExpense = 0;
    TextView incomeTotalTxt, expenseTotalTxt;
    private PieChart chart;
    Typeface font;
    private static DecimalFormat df = new DecimalFormat("0.00");
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112;
    boolean isIncome = false, isExpense = false, isData = false;
    LinearLayout noInfo, valuesExsist;
    Button back_to_buget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(this,
                SemonaidDB.class, "db_semonaid")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);

        noInfo = (LinearLayout) findViewById(R.id.noValues_layout);
        valuesExsist = (LinearLayout) findViewById(R.id.values_layout);

        incomeTotalTxt = (TextView) findViewById(R.id.income_amt);
        expenseTotalTxt = (TextView) findViewById(R.id.exp_amt);
        topList = (RecyclerView) findViewById(R.id.list_top);
        back_to_buget = (Button) findViewById(R.id.btn_budget);

        back_to_buget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, BudgetMainMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        chart = findViewById(R.id.chart1);
        initilizePieData();


    }


    //initilizes the chart with the ui elements
    public void initilizePieData() {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(font);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);


        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.spin(2000, 0, 360, Easing.EaseInBack);


        //setting the legend for the chart
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(13f);
        l.setTypeface(font);


        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTypeface(font);
        l.setWordWrapEnabled(false);

        chart.getLegend().setWordWrapEnabled(true);
        chart.setEntryLabelTextSize(12f);
    }


    //sets the center string
    private SpannableString generateCenterSpannableText(String title) {

        SpannableString s = new SpannableString(title);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    //gets the data from the SQL db
    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.AppDAO().getAllBudgetData();
            if (!(item.isEmpty() || item == null)) {
                isData = true;
                for (BudgetItem temp : item) {


                    int multiplier = 1;
                    if (temp.frequency == 1) {
                        multiplier = 52;
                    } else if (temp.frequency == 2) {
                        multiplier = 26;
                    } else if (temp.frequency == 3) {
                        multiplier = 12;
                    } else if (temp.frequency == 4) {
                        multiplier = 1;
                    }


                    //checking to see if the values are of type income or expense
                    if (temp.type == 100) { //income
                        incomeItemList.add(temp);
                        incomeTotal = incomeTotal + (temp.getAmount() * multiplier);


                    } else if (temp.type == 999) { //expense
                        expenseItemList.add(temp);
                        expenseTotal = expenseTotal + (temp.getAmount() * multiplier);

                    }
                    allItemList.add(temp);

                }

                status = "full";
            } else {
                isData = false;
                status = "empty";
            }


            return status;
        }


        @Override
        protected void onPostExecute(String details) {
            // displaySummaryItems(details);

            //setting the values in the income/exp card
            if (incomeTotal > 0) {
                incomeTotalTxt.setText("$ " + String.valueOf(incomeTotal));
                isIncome = true;
            }
            if (expenseTotal > 0) {
                expenseTotalTxt.setText("$ " + String.valueOf(expenseTotal));
                isExpense = true;
            }


            //checking to see if there is data. If not, the img is displayed
            if (isData) {
                if (incomeTotal == 0 && expenseTotal == 0) {
                    //need to tell the user to enter something
                    valuesExsist.setVisibility(View.INVISIBLE);
                    noInfo.setVisibility(View.VISIBLE);
                } else {
                    valuesExsist.setVisibility(View.VISIBLE);
                    noInfo.setVisibility(View.GONE);
                    budggetGraph();
                    categories();
                }

            }

        }

    }

    //calculate the top 3 categories
    public void categories() {

        //sorting the arraylist expenseItemList based on the amount
        Collections.sort(expenseItemList, new Comparator<BudgetItem>() {
            @Override
            public int compare(BudgetItem o1, BudgetItem o2) {
                return Double.compare(o1.getAmount(), o2.getAmount());
            }
        });

        //sorts in decending order
        Collections.reverse(expenseItemList);

        if (expenseItemList.size() > 3) {
            // gets the top 3 categories
            for (int i = 0; i < 3; i++) {
                BudgetItem item = expenseItemList.get(i);
                Log.d("CAT", item.itemName + "----" + item.getAmount());
                top3.add(item);
            }
        } else {
            // gets the top 3 categories
            for (int i = 0; i < expenseItemList.size(); i++) {
                BudgetItem item = expenseItemList.get(i);
                Log.d("CAT", item.itemName + "----" + item.getAmount());
                top3.add(item);
            }
        }


        TopCategoriesAdapter adapter = new TopCategoriesAdapter(this, top3);
        RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        topList.setLayoutManager(mLayoutManagerIncome);

        topList.setItemAnimator(new DefaultItemAnimator());
        topList.setAdapter(adapter);
        topList.setNestedScrollingEnabled(false);


    }

    //func to compute the values for the graph
    public void budggetGraph() {

        String title = "";
        double household = 0, personal = 0, bills = 0, transport = 0;
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        maxExpense = 0;
        for (int i = 0; i < expenseItemList.size(); i++) {

            BudgetItem b = expenseItemList.get(i);

            int multiplier = 1;
            if (b.frequency == 1) {
                multiplier = 52;
            } else if (b.frequency == 2) {
                multiplier = 26;
            } else if (b.frequency == 3) {
                multiplier = 12;
            } else if (b.frequency == 4) {
                multiplier = 1;
            }

            //getting the values for each category
            if (b.category.equals(category_household)) {
                household = household + b.amount * multiplier;
            } else if (b.category.equals(category_personal)) {
                personal = personal + b.amount * multiplier;
            } else if (b.category.equals(category_bills)) {
                bills = bills + b.amount * multiplier;
            } else if (b.category.equals(category_transport)) {
                transport = transport + b.amount * multiplier;
            }

        }

        //expense is more and there is no money left. So we need to show the breakdown of expense
        if (expenseTotal > incomeTotal) {

            if (household > 0) {
                int val = (int) Math.round(household);
                pieEntries.add(new PieEntry((float) val, "Household Exp"));
            }

            if (personal > 0) {
                int val = (int) Math.round(personal);
                pieEntries.add(new PieEntry((float) val, "Personal"));

            }

            if (transport > 0) {
                int val = (int) Math.round(transport);
                pieEntries.add(new PieEntry((float) val, "Transport"));

            }

            if (bills > 0) {
                int val = (int) Math.round(bills);
                Log.d("VAL", String.valueOf(val));
                pieEntries.add(new PieEntry((float) val, "Bills"));
            }

            double diff = (expenseTotal - incomeTotal) / expenseTotal * 100;
            title = "You have overshot your expenses by " + df.format(diff) + "%";


        } else if (expenseTotal < incomeTotal) { //if expense is less than income

            if (household > 0) {
                double hPercentage = household / incomeTotal * 100;
                pieEntries.add(new PieEntry((float) hPercentage, "Household"));

            }

            if (personal > 0) {
                double pPercentage = personal / incomeTotal * 100;
                pieEntries.add(new PieEntry((float) pPercentage, "Personal"));

            }

            if (bills > 0) {
                double uPercentage = bills / incomeTotal * 100;
                pieEntries.add(new PieEntry((float) uPercentage, "Utility"));

            }

            if (transport > 0) {
                double tPercentage = transport / incomeTotal * 100;
                pieEntries.add(new PieEntry((float) tPercentage, "Transport"));

            }

            double diff = (incomeTotal - expenseTotal) / incomeTotal * 100;
            double remaining = Math.abs(diff - 100);
            pieEntries.add(new PieEntry((float) diff, "Surplus"));
            title = "You have utilized " + df.format(remaining) + "% of your Income!";
        }


        //generating a pie data with the values
        PieDataSet dataSet = new PieDataSet(pieEntries, " ");

        dataSet.setDrawIcons(false);
        dataSet.setValueTextSize(10f);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(font);

        chart.setDrawSliceText(false);
        chart.setCenterText(generateCenterSpannableText(title));

        chart.setData(data);
        chart.setEntryLabelTypeface(font);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        } else if (id == R.id.homeIcon) {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setTitle(String title) {
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTypeface(font);
        textView.setTextSize(20);
        textView.setTextColor(getResources().getColor(R.color.white));

        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }


}