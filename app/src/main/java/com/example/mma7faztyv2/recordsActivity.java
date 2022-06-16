package com.example.mma7faztyv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Arrays;


public class recordsActivity extends AppCompatActivity
        implements pickFragment.OnFragmentClickListener_pick,
        typrfrag.OnFragmentClickListener_type
    ,diaglogFragment.delFRagListener
    ,diaglogFragment.editFRagListener{
    public String titleRequestCode;
    public String theTitleHere;
    calcData theCalcHere;
   public static Double theNumberSelected;
    public static String  thecommentSelected;

    int c=10;
    public ExpandableLayout expandableLayout;
    public static ma7fazaDatabase caller_ma7fazaDatabase;
    private static final String TAG = "recordsActivity";
    static           FragmentManager     fm;
    static               FragmentTransaction ft;
    private ListView addListView;
    private ListView subListView;
     private Toolbar toolbarActionbar;
     public static ArrayList<recordsData> addRecordArray;
     public static ArrayList<recordsData> subRecordArray;
     static Context recordContext;
    TextView maxValue;
    TextView spentValue;
    TextView remainVlaue;
    private Button recordAddButton;
    private Button recordSubButton;
    double maxNumber,spentNumber,remainNumber,tempSpentNumber;
    Switch input_switch;
    boolean checkswitch;
    boolean notNegativeChecker;
    int delPosition;
    String theSelectedList, SELECTED_ADD = "ADD", SELECTED_SUB = "SUB";
    LinearLayout ln;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Log.i(TAG, "onCreate: records################################################");
        recordContext=this;
        definexml();
        defineVariables();
        defineClasses();
//intent setup
        ln = findViewById(R.id.add_record_include);
        ln.setVisibility(View.VISIBLE);

        creatingIntentReceiver();//hygeb eltitle lw lSA new
        exictedIntentReceiver();//hygeb eltitile lw dost 3la item mn el recycler
        toolbarSetup();//byzbt eltoolbar
        //showpickFrag();
        showtypeFrag();
        theCalcHere = caller_ma7fazaDatabase.getcalc(theTitleHere);
        calcPrinter(theCalcHere);
        addListView.setAdapter(new listRecordAdapter(this,R.layout.layout,caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table)));
        addRecordArray=caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table);
        onButtonsCLick();
        calcValuesUpdate();
        //inputSwitchListener();
        showprgFrag();
        onListsClick();
    }
//define functions
    public void definexml() {
        maxValue = (TextView) findViewById(R.id.r_maxValue);
        spentValue = (TextView) findViewById(R.id.r_spentValue);
        remainVlaue = (TextView) findViewById(R.id.r_remainVlaue);
        addListView = findViewById(R.id.addListView);
        subListView = findViewById(R.id.subListView);
        recordAddButton = (Button) findViewById(R.id.addbt);
        recordSubButton = (Button) findViewById(R.id.subbt);
        toolbarActionbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbarActionbar);
    }
    public void defineVariables() {
        titleRequestCode = MainActivity.titleRequestCode;
    }
    private void defineClasses() {
        caller_ma7fazaDatabase = new ma7fazaDatabase(this);
    }
    private void toolbarSetup() {
        getSupportActionBar().setTitle(theTitleHere);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarActionbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_white_arrow));
        toolbarActionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void onButtonsCLick() {
        recordAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });
        recordSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubButtonClicked();
                Toast.makeText(recordsActivity.this,String.format("button : %s",recordSubButton.getText()), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void onListsClick() {
      //undo------------------------------------------------------
      addListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              delPosition = position;
              theSelectedList = SELECTED_ADD;
              Toast.makeText(recordsActivity.this,String.format(" you clicked on: %s",addRecordArray.get(delPosition).getNumber()), Toast.LENGTH_LONG).show();
             editDialogFrag(addRecordArray);
          }
      });
      subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              delPosition = position;
              theSelectedList = SELECTED_SUB;
              Toast.makeText(recordsActivity.this,String.format(" you clicked on: %s",addRecordArray.get(delPosition).getNumber()), Toast.LENGTH_LONG).show();
              editDialogFrag(subRecordArray);
          }
      });
  }
    //menu setup--------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }//t3ref elmenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        TextView switchB=findViewById(R.id.input_switch);
        if (itemId == R.id.r_clear) {clearingLists(); }
        else if (itemId == R.id.r_dummyRecordAdd) {recordsDummyEntry(); calcValuesUpdate();}
        else if (itemId == R.id.r_dummyRecordGet) {calcValuesUpdate();
            }

        return super.onOptionsItemSelected(item);
    }//hy3ml eh lma ados 3la elzrayr
//intent part--------------------------------------------
    public void creatingIntentReceiver() {
        Intent intent = getIntent();
        String createdTitle=intent.getStringExtra("1");
        if (createdTitle!=null){theTitleHere= createdTitle;}
    }
    public void exictedIntentReceiver() {
        Intent intent2 = getIntent();
        String existedTitle=intent2.getStringExtra("2");
        if (existedTitle!=null){theTitleHere=existedTitle;}
    }
//data entry part--------------------------------------------
    public void calcPrinter(calcData c) {
        if (c!=null) {
            maxValue.setText(  String.valueOf(c.getMax()));
            spentValue.setText(  String.valueOf(c.getSpent()));
            remainVlaue.setText(  String.valueOf(c.getRemain()));
        }
    }//byzhr el sum fel text views
    public  void showpickFrag() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        new pickFragment();
        pickFragment pf = pickFragment.newpickInstance(theTitleHere);
        ft.replace(R.id.FragNumFramelayout, pf);
        ft.commit();
    }
    @Override
    public void onFragmentIntereaction_pick(double s) {
        theNumberSelected=s;
        Toast.makeText(this,String.format(" you picked: %s",s), Toast.LENGTH_LONG).show();
    }
    public  void showtypeFrag() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        new typrfrag();
        typrfrag tf = typrfrag.newtypeInstance(theTitleHere);
        ft.replace(R.id.FragNumFramelayout, tf);
        ft.commit();
    }
    public void type_number_listener(double s) { theNumberSelected = s; }

    @Override
    public void type_comment_listener(String comment) { thecommentSelected=comment; }

    public  void showprgFrag() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Log.i(TAG, "showprgFrag: theTitleHere "+theTitleHere);
        calcData data=caller_ma7fazaDatabase.getcalc(theTitleHere);
        prgFragment prgf = new prgFragment().newInstance((int)data.getMax(),(int)data.getSpent());
        ft.replace(R.id.prg_frame, prgf);
        ft.commit();
    }
/*
    public void inputSwitchListener(){
        input_switch = findViewById(R.id.input_switch);
        input_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "onCheckedChanged: " + isChecked);
                checkswitch = isChecked;
                input_switch(isChecked);
            }
        });
    }
*/
    public void input_switch(String check) {
        Log.i(TAG, "input_switch: " + check);
        //np gone edit text visible
        if (check=="pick") {
            showpickFrag();
        }
        else if (check=="type"){
            showtypeFrag();
        }
    }
    public void hidetv(View view) {
        TextView tv=findViewById(R.id.hide_tv);
        if (ln.getVisibility() != View.VISIBLE) {
            ln.setVisibility(View.VISIBLE);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);

        } else {
            ln.setVisibility(View.GONE);
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);

        }

    }
//editfragment--------------------------------------------------
    public void editDialogFrag(ArrayList<recordsData> arrayList) {
        double num = arrayList.get(delPosition).getNumber();
        String comm = arrayList.get(delPosition).getComment();
        new diaglogFragment();
        diaglogFragment fragment = diaglogFragment.newInstance(num, comm);
        fragment.show(getSupportFragmentManager(), null);
    }
    @Override
    public void onEditClickListener(double num, String comm) {
    }
    @Override
    public void onDelClickListener() {
        if (theSelectedList.equals(SELECTED_ADD)) {
            deleteARecord(delPosition, ma7fazaDatabase.add_table);
        }
        else if (theSelectedList.equals(SELECTED_SUB)) {
            deleteARecord(delPosition,ma7fazaDatabase.sub_table);
        }
        calcValuesUpdate();
    }
    public void deleteARecord(int delposition,String tablename) {
        caller_ma7fazaDatabase.deleteCetrainrecord(delposition,addRecordArray.get(delPosition),tablename);
    }
//calculation part--------------------------------------------
    public void recordsDummyEntry()   {
    Log.i(TAG, "calcValuesGetter: dummy : insetring the data");

    ArrayList<recordsData> dummarray =  new ArrayList<>(Arrays.asList(
                    new recordsData(theTitleHere,2, theTitleHere + "add:1"),
                    new recordsData(theTitleHere,4, theTitleHere + "add:2"),
                    new recordsData(theTitleHere,6, theTitleHere + "add:3"),
                    new recordsData(theTitleHere,3, theTitleHere + "add:4"),
                    new recordsData(theTitleHere,10, theTitleHere + "add:5")));
    ArrayList<recordsData> dummarraysub =  new ArrayList<>(Arrays.asList(
                    new recordsData(theTitleHere,1,theTitleHere+"sub:1"),
                    new recordsData(theTitleHere,4, theTitleHere+"sub:2"),
                    new recordsData(theTitleHere,1,theTitleHere+"sub:3"),
                    new recordsData(theTitleHere,3,theTitleHere+"sub:4"),
                    new recordsData(theTitleHere,10,theTitleHere+"sub:5")));
    for (recordsData recordsData : dummarray) {
        caller_ma7fazaDatabase.insertRecordAdd(recordsData);
    }
    for (recordsData recordsData : dummarraysub) {
        caller_ma7fazaDatabase.insertRecordSub(recordsData);
    }

}
    public void calcValuesUpdate()    {
    Log.i(TAG, "calcValuesGetter: dummy : getting the data");

        addRecordArray=caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table);
        subRecordArray=caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.sub_table);
        addListView.setAdapter(new listRecordAdapter(this,R.layout.layout,addRecordArray));
        subListView.setAdapter(new listRecordAdapter(this,R.layout.layout_red,subRecordArray));
        maxNumber=recordsData.getNumberSum(addRecordArray);
        spentNumber=recordsData.getNumberSum(subRecordArray);
        tempSpentNumber = maxNumber - spentNumber;
        if (tempSpentNumber>=0)
                {remainNumber=maxNumber-spentNumber;
                    notNegativeChecker=true;}
        else Toast.makeText(recordContext,String.format(" you only have: %s",tempSpentNumber), Toast.LENGTH_LONG).show();
        caller_ma7fazaDatabase.updatecalc(theTitleHere,new calcData(maxNumber,spentNumber,remainNumber,0));
        theCalcHere=caller_ma7fazaDatabase.getcalc(theTitleHere);
        calcPrinter(theCalcHere);
    }
    public void clearingLists()       {
        Toast.makeText(recordContext,String.format(" clearingList: %s","click"), Toast.LENGTH_LONG).show();
        caller_ma7fazaDatabase.delAllRecords(theTitleHere,ma7fazaDatabase.add_table);
        caller_ma7fazaDatabase.delAllRecords(theTitleHere,ma7fazaDatabase.sub_table);
        addRecordArray=caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.add_table);
        subRecordArray=caller_ma7fazaDatabase.getRecordWithTitle(theTitleHere,ma7fazaDatabase.sub_table);
        addListView.setAdapter(new listRecordAdapter(this,R.layout.layout,addRecordArray));
        subListView.setAdapter(new listRecordAdapter(this,R.layout.layout_red,subRecordArray));
    }
    private void onAddButtonClicked() {
        Log.i(TAG, String.format("onClick: add button clicked :"));

        caller_ma7fazaDatabase.insertRecordAdd(new recordsData(theTitleHere,theNumberSelected,thecommentSelected));
        calcValuesUpdate();
        showprgFrag();
    }
    private void onSubButtonClicked() {
        Log.i(TAG, String.format("onClick: add button clicked :"));
        if (caller_ma7fazaDatabase.getcalc(theTitleHere).getRemain()-theNumberSelected>=0)caller_ma7fazaDatabase.insertRecordSub(new recordsData(theTitleHere,theNumberSelected,thecommentSelected));
        else Toast.makeText(recordContext,String.format(" you only have: %s",tempSpentNumber), Toast.LENGTH_LONG).show();
        calcValuesUpdate();
        showprgFrag();
    }

}