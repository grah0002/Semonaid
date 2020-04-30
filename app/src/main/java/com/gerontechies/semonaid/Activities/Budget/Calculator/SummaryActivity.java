package com.gerontechies.semonaid.Activities.Budget.Calculator;


import androidx.annotation.RequiresApi;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gerontechies.semonaid.Activities.Budget.Tips.BillsTipsMenuActivity;
import com.gerontechies.semonaid.Activities.Budget.Tips.OtherTipsActivity;
import com.gerontechies.semonaid.Activities.Budget.Tips.TipsMenuActivity;
import com.gerontechies.semonaid.Activities.Budget.Tips.TravelTipsActivity;
import com.gerontechies.semonaid.Activities.HomeScreenActivity;
import com.gerontechies.semonaid.Activities.Services.ServiceCategoryActivity;
import com.gerontechies.semonaid.Activities.Services.ServicesCategoryList;
import com.gerontechies.semonaid.Adapters.SummaryItemAdapter;
import com.gerontechies.semonaid.Models.BudgetDatabase;
import com.gerontechies.semonaid.Models.BudgetItem;
import com.gerontechies.semonaid.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;


import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    BudgetDatabase db = null;
    String CATEGORY = "bills";
    String heroCategory = "None, you're Golden!";
    String gosave = "Go To Saving Tips";
    String goservice = "Go To Services";
    Bundle BudgetCalculator = new Bundle();
    List<BudgetItem> item;
    List<BudgetItem> allItemList = new ArrayList<>();
    List<BudgetItem> incomeItemList = new ArrayList<>();
    List<BudgetItem> expenseItemList = new ArrayList<>();
    String category_bills = "Utility Bills", category_personal = "Personal Expenses", category_transport = "Transport Expenses", category_household = "Household Expenses", categotry_income = "Income";

    RecyclerView incomeList, expenseList;

    double incomeTotal, expenseTotal, maxExpense = 0;
    int heroRedirectToken = 4;

    TextView incomeTotalTxt, expenseTotalTxt;

    private PieChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    Typeface font ;
    private static DecimalFormat df = new DecimalFormat("0.00");
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setTitle("Budget Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Room.databaseBuilder(this,
                BudgetDatabase.class, "budget_database")
                .fallbackToDestructiveMigration()
                .build();

        ReadDatabase rd = new ReadDatabase();
        rd.execute();

        font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);


//        incomeList = (RecyclerView) findViewById(R.id.summary_income);
//        expenseList = (RecyclerView) findViewById(R.id.summary_expense);

        incomeTotalTxt = (TextView) findViewById(R.id.income_amt);
        expenseTotalTxt = (TextView) findViewById(R.id.exp_amt);


/*
        Button btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(SummaryActivity.this)) {
                    try {
                        saveChart();
                        Log.d("Item", "sharing");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        btn_save.setTypeface(font);
*/
        Button btn_home = (Button) findViewById(R.id.btn_next);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_home.setTypeface(font);

        Button btn_gosave = (Button) findViewById(R.id.btn_gosave);
        btn_gosave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, TipsMenuActivity.class);
                if(heroRedirectToken == 1) {
                    intent = new Intent(SummaryActivity.this, BillsTipsMenuActivity.class);
                }
                if(heroRedirectToken == 2) {
                    intent = new Intent(SummaryActivity.this, OtherTipsActivity.class);
                }
                if(heroRedirectToken == 3) {
                    intent = new Intent(SummaryActivity.this, TravelTipsActivity.class);
                }

                startActivity(intent);
                finish();
            }
        });
        btn_gosave.setTypeface(font);

        Button btn_goservice = (Button) findViewById(R.id.btn_goservice);
        btn_goservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, ServiceCategoryActivity.class);
/*                if(heroRedirectToken == 1) {
                    intent = new Intent(SummaryActivity.this, ServiceCategoryActivity.class);
                }
                if(heroRedirectToken == 2) {
                    intent = new Intent(SummaryActivity.this, ServiceCategoryActivity.class);
                }

 */
                if(heroRedirectToken == 3) {
                    String category = "Travel Assistance";
                    intent = new Intent(SummaryActivity.this, TravelTipsActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, category);
                }

                startActivity(intent);
                finish();

            }
        });
        btn_goservice.setText(goservice);
        btn_goservice.setTypeface(font);

        chart = findViewById(R.id.chart1);
        initilizePieData();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(SummaryActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    public  void initilizePieData(){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        // chart.setCenterTextTypeface(tfLight);

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

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);



        chart.animateY(1400, Easing.EaseInOutQuad);
        chart.spin(2000, 0, 360, Easing.EaseInBack);



//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTypeface(font);
        chart.setEntryLabelTextSize(12f);
    }








    private SpannableString generateCenterSpannableText(String title) {

        SpannableString s = new SpannableString(title);
        //  s.setSpan(new StyleSpan(Typeface.create(font,Typeface.NORMAL)));
        //  s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //   s.setSpan(new StyleSpan(Typeface.), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //      s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.create(font), s.length() - 14, s.length(), 0);
        // s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
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



    private class ReadDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String status = "";
            item = db.budgetDAO().getAll();
            if (!(item.isEmpty() || item == null) ){
                for (BudgetItem temp : item) {


                    int multiplier = 1;
                    if(temp.frequency == 1){
                        multiplier = 52;
                    } else if(temp.frequency == 2){
                        multiplier = 26;
                    } else if(temp.frequency == 3){
                        multiplier = 12;
                    } else if(temp.frequency == 4){
                        multiplier = 1;
                    }


                    if(temp.type == 100){ //income
                        incomeItemList.add(temp);
                        incomeTotal = incomeTotal + (temp.getAmount() * multiplier);


                    } else if(temp.type == 999){ //expense
                        expenseItemList.add(temp);
                        expenseTotal = expenseTotal + (temp.getAmount() * multiplier);

                    }
                    allItemList.add(temp);

                }

                status = "full";
            } else {
                status = "empty";
            }


            return  status;
        }


        @Override
        protected void onPostExecute(String details) {
            // displaySummaryItems(details);

            expenseTotalTxt.setText("$ "+String.valueOf(expenseTotal));
            incomeTotalTxt.setText("$ "+String.valueOf(incomeTotal));

            budggetGraph();


        }

    }

    public void budggetGraph(){

        String title = "";
        double household = 0, personal = 0, bills = 0, transport=0;
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        maxExpense = 0;
        for(int i = 0; i<expenseItemList.size(); i++){

            BudgetItem b = expenseItemList.get(i);

            int multiplier = 1;
            if(b.frequency == 1){
                multiplier = 52;
            } else if(b.frequency == 2){
                multiplier = 26;
            } else if(b.frequency == 3){
                multiplier = 12;
            } else if(b.frequency == 4){
                multiplier = 1;
            }

            if(b.category.equals(category_household)){
                household = household + b.amount*multiplier;
            } else if(b.category.equals(category_personal)){
                personal = personal + b.amount*multiplier;
            } else if (b.category.equals(category_bills)){
                bills = bills + b.amount*multiplier;
            } else  if(b.category.equals(category_transport)){
                transport = transport + b.amount*multiplier;
            }

        }




        //expense is more and there is no money left. So we need to show the breakdown of expense
        if(expenseTotal > incomeTotal){

            if(household > 0){
                pieEntries.add(new PieEntry((float) household,  "Household"));
                if(household > maxExpense){
                    maxExpense = household;
                    heroRedirectToken = 1;
                    heroCategory = "Household";
                    gosave = getString(R.string.go_save_category);
                }
            }

            if(personal > 0){
                pieEntries.add(new PieEntry((float) personal,  "Personal"));
                if(personal > maxExpense){
                    maxExpense = personal;
                    heroRedirectToken = 2;
                    heroCategory = "Personal";
                    gosave = getString(R.string.go_save_category);
                }
            }

            if(transport > 0){
                pieEntries.add(new PieEntry((float) transport,  "Transport"));
                if(transport > maxExpense){
                    maxExpense = transport;
                    heroRedirectToken = 3;
                    heroCategory = "Transport";
                    gosave = getString(R.string.go_save_category);
                    goservice = getString(R.string.go_service_category);
                }
            }

            if(bills > 0){
                pieEntries.add(new PieEntry((float) bills,  "Utility"));
                if(bills > maxExpense){
                    maxExpense = bills;
                    heroRedirectToken = 1;
                    heroCategory = "Bills";
                    gosave = getString(R.string.go_save_category);
                }
            }

            double diff = (expenseTotal - incomeTotal)/expenseTotal * 100;
            title =  df.format(diff) +"% Overhead";



        } else if(expenseTotal<incomeTotal){

            if(household > 0){
                double hPercentage = household/incomeTotal * 100;
                pieEntries.add(new PieEntry((float) hPercentage,  "Household"));
                if(household > maxExpense){
                    maxExpense = household;
                    heroRedirectToken = 1;
                    heroCategory = "Household";
                    gosave = getString(R.string.go_save_category);
                }
            }

            if(personal > 0){
                double pPercentage = personal/incomeTotal * 100;
                pieEntries.add(new PieEntry((float) pPercentage,  "Personal"));
                if(personal > maxExpense){
                    maxExpense = personal;
                    heroRedirectToken = 2;
                    heroCategory = "Personal";
                    gosave = getString(R.string.go_save_category);
                }
            }

            if(bills > 0){
                double uPercentage = bills/incomeTotal * 100;
                pieEntries.add(new PieEntry((float) uPercentage,  "Utility"));
                if(bills > maxExpense){
                    maxExpense = bills;
                    heroRedirectToken = 1;
                    heroCategory = "Bills";
                    gosave = getString(R.string.go_save_category);
                }
            }

            if(transport > 0){
                double tPercentage = transport/incomeTotal * 100;
                pieEntries.add(new PieEntry((float) tPercentage,  "Transport"));
                if(transport > maxExpense){
                    maxExpense = transport;
                    heroRedirectToken = 3;
                    heroCategory = "Transport";
                    gosave = getString(R.string.go_save_category);
                    goservice = getString(R.string.go_service_category);
                }
            }

            double diff = (incomeTotal - expenseTotal)/incomeTotal * 100;
            pieEntries.add(new PieEntry((float) diff, "Surplus"));
            title = df.format(diff) + "% of Income Spent";
        }

        TextView maxExpenseCategoryTxt = (TextView)findViewById(R.id.maxExpenseCategoryTxt);
        maxExpenseCategoryTxt.setText(heroCategory);

        Button btn_gosave = (Button) findViewById(R.id.btn_gosave);
        btn_gosave.setText(gosave);

        Button btn_goservice = (Button) findViewById(R.id.btn_goservice);
        btn_goservice.setText(goservice);

        PieDataSet dataSet = new PieDataSet(pieEntries, " ");

        dataSet.setDrawIcons(false);

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
        //dataSet.setSelectionShift(0f);

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

    public void saveChart() throws IOException {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        chart.saveToGallery("Semonaid-" + ts);

        Bitmap bitmap = chart.getChartBitmap();
        Uri yourUri = getImageUri(SummaryActivity.this, bitmap);

        shareImageUri(yourUri);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void shareImageUri(Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }

    public  void displaySummaryItems(String details){
        if(details.equals("full"))
        {

            Log.d("ITEM", String.valueOf(expenseItemList.size()));


            Log.d("ITEM", String.valueOf(expenseItemList.size()));
            SummaryItemAdapter incomeAdapter = new SummaryItemAdapter(this,  incomeItemList);
            RecyclerView.LayoutManager mLayoutManagerIncome = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            incomeList.setLayoutManager(mLayoutManagerIncome);

            incomeList.setItemAnimator(new DefaultItemAnimator());
            incomeList.setAdapter(incomeAdapter);
            incomeList.setNestedScrollingEnabled(false);

            SummaryItemAdapter expAdapter = new SummaryItemAdapter(this,  expenseItemList);
            RecyclerView.LayoutManager mLayoutManagerExpense = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            expenseList.setLayoutManager(mLayoutManagerExpense);

            expenseList.setItemAnimator(new DefaultItemAnimator());
            expenseList.setAdapter(expAdapter);
            // expenseList.setNestedScrollingEnabled(false);


            expenseTotalTxt.setText("$ "+String.valueOf(expenseTotal));
            incomeTotalTxt.setText("$ " + String.valueOf(incomeTotal));

        } else{
            Log.d("ITEM", "No item");

        }

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
        }

        return super.onOptionsItemSelected(item);
    }


    public void setTitle(String title){
        Typeface font = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);


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