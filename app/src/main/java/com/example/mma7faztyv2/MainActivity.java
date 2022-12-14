package com.example.mma7faztyv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements
        creatingDialogFragment.editFRagListener,
        dateFrag.getCalenderRead,
        PlaceholderFragment.sendTheSum,
        budgetAdapter.recyclerItemDeleted {
    private static final String TAG = "MainActivity2";
    RecyclerView recyclerView;
    ArrayList<budgetData> recyclerArray;
    calcData sumData;
    public static ma7fazaDatabase caller_ma7fazaDatabase;
    String thetitle;
    Double thenum;
    Spinner spinner;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabs;
    FloatingActionButton fab;
    SectionsPagerAdapter sectionsPagerAdapter;
    public static final String titleRequestCode = "titlerequestcode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: main###########################################");
        definexml();
        defineVariables();
        defineClasses();
        tabactions();
        //recyclerDummyEntry();
        Log.i(TAG, String.format("onCreate: before sumreinter :"));
        sumPrinter(calcData.getCalcSum(caller_ma7fazaDatabase.getThisDateArray(ma7fazaDatabase.allSt)));
    }

    //define functions
    public void definexml() {
        recyclerView = findViewById(R.id.rv);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.spinner);
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        fab = findViewById(R.id.fab);

    }

    public void defineVariables() {
        recyclerArray = new ArrayList<>();
    }

    private void defineClasses() {
        caller_ma7fazaDatabase = new ma7fazaDatabase(this);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    }
//menu setup

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }//t3ref elmenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.m_clear) {
            clear();//recyclerDummyEntry();
            //refreshData();
            Toast.makeText(this, String.format(" selected: %s", item.getTitle()), Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.m_dummmyEntry) {
            recyclerDummyEntry();
            Toast.makeText(this, String.format(" selected: %s", item.getTitle()), Toast.LENGTH_LONG).show();
        }
        if (item.getItemId() == R.id.m_dummyRetrieve) {
            refreshData();
            Toast.makeText(this, String.format(" selected: %s", item.getTitle()), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }//hy3ml eh lma ados 3la elzrayr

    //recycler functions
    public void addNewBudgetItem(String t, Double n) {
        caller_ma7fazaDatabase.addbudget(new budgetData(caller_ma7fazaDatabase.countTableRows(ma7fazaDatabase.budget_table), t, dateTime.getCurrentDateTime(), new calcData(n, 0, 0, 0)));
        budgetData.recyclerArrayPrinter(TAG, "after adding", recyclerArray);
    }//bya5od el data mn el onEditClickListener w yzwdha fel recycler

    //dialog functions
    public void createDialogFrag() {
        Log.i(TAG, "createDialogFrag: adding budget##################################");
        creatingDialogFragment fragment = new creatingDialogFragment();
        fragment.show(getSupportFragmentManager(), null);
    }//by3ml elfragment

    public void calenderDialogFrag() {
        dateFrag frag = new dateFrag();
        frag.show(getSupportFragmentManager(), null);
    }//by3ml elfragment

    @Override
    public void onCreatingClickListener(String title, double num) {
        thetitle = title;
        thenum = num;
        Log.i(TAG, String.format("onCreatingClickListener: in the listener title :%s", title));
        addNewBudgetItem(title, num);
        //intentSender(title);
        refreshData();
        Log.i(TAG, String.format("onEditClickListener: title :%s , num : %s", title, num));
    }//elly byst2bl el data mn elfragment

    //tab functions
    public void tabactions() {
        sectionsPagerAdapter.addtab(new tabData(ma7fazaDatabase.allSt, PlaceholderFragment.newInstance(ma7fazaDatabase.allSt)));
        sectionsPagerAdapter.addtab(new tabData(ma7fazaDatabase.todaySt, PlaceholderFragment.newInstance(ma7fazaDatabase.todaySt)));
        sectionsPagerAdapter.addtab(new tabData(ma7fazaDatabase.yesterdaySt, PlaceholderFragment.newInstance(ma7fazaDatabase.yesterdaySt)));
        sectionsPagerAdapter.addtab(new tabData(ma7fazaDatabase.thismonthSt, PlaceholderFragment.newInstance(ma7fazaDatabase.thismonthSt)));
        sectionsPagerAdapter.addtab(new tabData(ma7fazaDatabase.thisyearSt, PlaceholderFragment.newInstance(ma7fazaDatabase.thisyearSt)));
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabSelected: " + tab.getText());
                sumPrinter(calcData.getCalcSum(caller_ma7fazaDatabase.getThisDateArray(tab.getText().toString())));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabUnselected: " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabReselected: " + tab.getText());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialogFrag();
            }
        });

    }//elly by set eltablayout wy7ot asamy el7agat w y print el sum

    //database functions
    @Override
    public void onPickDateListener(String date) {
        Toast.makeText(this, String.format(" you picked: %s", date), Toast.LENGTH_LONG).show();
        recyclerArray = caller_ma7fazaDatabase.getAllbudgetsWithDate(date);
    }//bygeb el list bta3 eldate elyy e5tatto

    public void sumPrinter(calcData c) {
        TextView maxValue = (TextView) findViewById(R.id.m_maxValue);
        TextView spentValue = (TextView) findViewById(R.id.m_spentValue);
        TextView remainVlaue = (TextView) findViewById(R.id.m_remainValue);
        maxValue.setText(String.valueOf(c.getMax()));
        spentValue.setText(String.valueOf(c.getSpent()));
        remainVlaue.setText(String.valueOf(c.getRemain()));
    }//byzhr el sum fel text views 

    @Override
    public void sendingThesum(calcData c) {
        sumData = c;


    }

    //deleting data
    public void clear() {
        Log.i(TAG, "clear: deleteing all budgets#########################################");
        caller_ma7fazaDatabase.deleteAllBudgets();
        refreshData();
    }//elzorar ely byms7 kol 7aga

    @Override
    public void recyclerDelete(boolean b) {
        Toast.makeText(this, String.format(" deleted: %s", b), Toast.LENGTH_LONG).show();
        Log.i(TAG, String.format("recyclerDelete: deleted :%s", b));
        refreshData();
    }//bygeb el boolean ely byol fe 7aga etms7t w yms7 erlrecord w y update

    //sending data to the records activity
    public void intentSender(String t) {
        Log.i(TAG, String.format("intentIntent: in the listener title :%s", t));
        Intent intent = new Intent(this, recordsActivity.class);
        intent.putExtra("1", t);
        startActivity(intent);
    }

    public void recyclerDummyEntry() {
        caller_ma7fazaDatabase.addbudget(new budgetData(1, "new budget 1", dateTime.getyesterdayDateTime(), new calcData(10, 4, 6, 0)));
        caller_ma7fazaDatabase.addbudget(new budgetData(3, "new budget 2", dateTime.getCurrentDateTime(), new calcData(10, 4, 6, 0)));
        caller_ma7fazaDatabase.addbudget(new budgetData(4, "new budget 3", new dateTime("9/7/2022", "5:00"), new calcData(10, 4, 6, 0)));
        caller_ma7fazaDatabase.addbudget(new budgetData(8, "new budget 4", new dateTime("6/11/2020", "3:00"), new calcData(10, 4, 6, 0)));
        caller_ma7fazaDatabase.addbudget(new budgetData(9, "new budget 5", new dateTime("6/6/2021", "11:00"), new calcData(10, 4, 6, 0)));

    }

    @Override
    protected void onStart() {
        refreshData();
        Log.i(TAG, "lifecycle:" + getLocalClassName() + ":onStart: ");
        super.onStart();
    }

    public void refreshData() {
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        sumPrinter(calcData.getCalcSum(caller_ma7fazaDatabase.getThisDateArray(ma7fazaDatabase.allSt)));
    }
}