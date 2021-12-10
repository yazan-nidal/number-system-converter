package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import static android.widget.RadioButton.*;
import static java.lang.Long.toBinaryString;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        OnCheckedChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnFocusChangeListener
{
    static int err=2,trr=1;
    static String rre="";
    private BottomNavigationView  nav;
    private EditText input;
    //
    private TextView abin;
    private TextView  adec;
    private TextView  aoct;
    private TextView  ahex;
    //
    private RadioGroup rg;
    private RadioButton rbin;
    private RadioButton rdec;
    private RadioButton roct;
    private RadioButton rhex;
    //
    private Button clear;

    //
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater  inf=getMenuInflater ();
        inf.inflate ( R.menu.menu2 ,menu);
        if (menu!=null && menu instanceof  MenuBuilder)
            ((MenuBuilder)menu).setOptionalIconsVisible ( true );
        return super.onCreateOptionsMenu ( menu );
    }
    //
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) { return super.onPrepareOptionsMenu ( menu ); }
    //
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) { return super.onMenuOpened ( featureId, menu ); }
    //
    @Override
    public void onOptionsMenuClosed(Menu menu) { super.onOptionsMenuClosed ( menu ); }
    //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //getSupportActionBar ().setTitle ( item.getTitle ()+ "  is pressed" );
        if (item.getItemId ()==R.id.ex) { onBackPressed(); }
        else
        if (item.getItemId ()==R.id.cl)
        {
            Toast.makeText(getApplicationContext(), " Clear is pressed ;-) ", Toast.LENGTH_SHORT).show();
            clear.callOnClick();
        }
        return super.onOptionsItemSelected ( item );
    }

    //
    @Override
    public boolean onSupportNavigateUp()
    {
        Log.w ("hello", "onSupportNavigateUp is calll");
        onBackPressed ();
        return super.onSupportNavigateUp ();
    }
    //
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            input.clearFocus();
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
    //
    @Override
    public void onBackPressed()
    {
        Log.w ("hello", "this onbackpress is calll");
        Toast.makeText(getApplicationContext(), " goodbye ;-) ", Toast.LENGTH_SHORT).show();
        super.onBackPressed ();
        //clear.callOnClick();
        err=2;
        this.finish();
        return;
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        ActionBar bar= getSupportActionBar ();
        bar.setHomeButtonEnabled ( true );
        bar.setDisplayHomeAsUpEnabled ( true );
        bar.setHomeAsUpIndicator ( R.drawable.mr);
//
        nav= (BottomNavigationView)findViewById ( R.id.nb );
        nav.setItemIconTintList ( null );
        nav.setOnNavigationItemSelectedListener (this);
//
        abin    = findViewById (R.id.abin);
        adec    = findViewById (R.id.adec);
        aoct    = findViewById (R.id.aoct);
        ahex    = findViewById (R.id.ahex);
//
        rg   = (RadioGroup)findViewById( R.id.radioGroup );
        rbin = (RadioButton)findViewById ( R.id.rbin );
        rdec = (RadioButton)findViewById ( R.id.rdec );
        roct = (RadioButton)findViewById ( R.id.roct );
        rhex = (RadioButton)findViewById ( R.id.rhex );
//
        input   = findViewById (R.id.input);
        //input.setInputType(InputType.TYPE_CLASS_TEXT);
        //input.setKeyListener(null);
//
        clear    = (Button) findViewById (R.id.clear);
//
        clear.setOnClickListener (this);
        rbin.setOnClickListener ( this );
        rdec.setOnClickListener ( this );
        roct.setOnClickListener ( this );
        rhex.setOnClickListener ( this );
//
        input.setOnFocusChangeListener(this);
        rbin.setOnCheckedChangeListener ( this );
        rdec.setOnCheckedChangeListener ( this );
        roct.setOnCheckedChangeListener ( this );
        rhex.setOnCheckedChangeListener ( this );
//
        input.setOnKeyListener ( new View.OnKeyListener () {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == event.KEYCODE_DEL){ System.out.println("wlow2"); return false;}
                if (keyCode == KeyEvent.KEYCODE_BACK){input.clearFocus(); return false;}
                if (keyCode == KeyEvent.KEYCODE_ENTER) { fuN(); return false; }
                switch(err)
                {
                    case 1:{ return !((keyCode >=event.KEYCODE_0) && (keyCode <=event.KEYCODE_1) ); }
                    case 2:{ return !((keyCode >=event.KEYCODE_0) && (keyCode <=event.KEYCODE_9) ); }
                    case 3:{ return !((keyCode >=event.KEYCODE_0) && (keyCode <=event.KEYCODE_7) ); }
                    case 4:{ return !( ((keyCode >=event.KEYCODE_0) && (keyCode <=event.KEYCODE_9) )
                                    || ((keyCode >=event.KEYCODE_A) && (keyCode <=event.KEYCODE_F) ) ); }
                    default:{}
                }
                return   true;
            }
        } );

        input.addTextChangedListener ( new TextWatcher ()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { System.out.println("beforeTextChanged()   is called"); }
            //
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { System.out.println("onTextChanged()   is called"); }
            //
            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged()   is called");
                rre=input.getText().toString();
                fuN(); }
//
        } );
//
        switch(err)
        {
            case 1:{
                rbin.setSelected(true);
                rbin.setChecked(true);
                //clearSelection(nav);
                fbin();
                input.setText(rre);
                break;}

            case 2:{
                rdec.setSelected(true);
                rdec.setChecked(true);
                //clearSelection(nav);
                fdec();
                input.setText(rre);
                //input.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;}

            case 3:{
                roct.setSelected(true);
                roct.setChecked(true);
                //clearSelection(nav);
                foct();
                input.setText(rre);
               // input.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;}

            case 4:{
                rhex.setSelected(true);
                rhex.setChecked(true);
                //clearSelection(nav);
                fhex();
                input.setText(rre);
                //input.setKeyListener(null);
               // input.setInputType(InputType.TYPE_CLASS_TEXT);
                break;}
            default:{}
        }

        if(trr==1) { trr++; Toast.makeText(getApplicationContext(), " welcome to my program ;-) ", Toast.LENGTH_SHORT).show(); }
//
    }
    //
    @SuppressLint("RestrictedApi")
    public static void clearSelection(BottomNavigationView view)
    {
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++)
        {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setChecked(false);
        }
    }
    //
//////////////////////////////////////////////////////////////////
//
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.clear) { cleaR(); return; }
        if (v instanceof RadioButton)
        {
            Log.i("hello",((RadioButton)v).getText () + " is pressed cccccccc ") ;

            if (v == rbin)
            {
                fbin(); rbin.setSelected(true); err=1;
               // input.setInputType(InputType.TYPE_CLASS_TEXT);
               // input.setKeyListener ( DigitsKeyListener.getInstance ( "01" ) ); /*System.out.println("ser");*/
                return;
            }
//
            if (v.getId() == R.id.rdec)
            {
                fdec(); rdec.setSelected(true); err=2;
                //input.setKeyListener ( DigitsKeyListener.getInstance ( "0123456789" ) ); /*System.out.println("ser2");*/ // input.setText(adec.getText());
                return;
            }
//
            if (v.getId() == R.id.roct)
            {
                foct(); roct.setSelected(true); err=3;
                //input.setKeyListener ( DigitsKeyListener.getInstance ( "01234567" ) ); /*System.out.println("ser1");*/ //input.setText(aoct.getText());
                return;
            }
//
            if (v.getId() == R.id.rhex)
            {
                fhex();  rhex.setSelected(true); err=4;
               // input.setKeyListener ( DigitsKeyListener.getInstance ( "0123456789abcdef" ) );
                return;
            }
//
        }
    }
    //
    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked)
    {
        if (view==rbin)
        {
            if (isChecked == true)
            {
                //input.setKeyListener(DigitsKeyListener.getInstance("01"));
                String f=abin.getText().toString();
                clear.callOnClick();
                rbin.setSelected(true);
                input.setText(f);
            }
            return;
        }
//
        if (view==rdec)
        {
            if (isChecked == true)
            {
                //input.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                String f=adec.getText().toString();
                clear.callOnClick();
                rdec.setSelected(true);
                input.setText(f);
                //System.out.println("ser12");
            }
            return;
        }
//
        if (view==roct)
        {
            if (isChecked == true)
            {
                //input.setKeyListener(DigitsKeyListener.getInstance("01234567"));
                String f=aoct.getText().toString();
                clear.callOnClick();
                roct.setSelected(true);
                input.setText(f);
                //System.out.println("ser11");
            }
            return;
        }
//
        if (view==rhex)
        {
            if (isChecked == true)
            {
                //input.setKeyListener(DigitsKeyListener.getInstance("ABCDEFabcdef0123456789"));
                //System.out.println("why");
                String f=ahex.getText().toString();
                clear.callOnClick();
                rhex.setSelected(true);
                input.setText(f);
            }
            return;
        }
    }
    //
    @SuppressLint("WrongViewCast")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId ()==R.id.bin)
        {
            rbin.callOnClick();
            rbin.setChecked(true);
            fbin();
        }
//
        else if (item.getItemId ()==R.id.dec)
        {
            rdec.callOnClick();
            rdec.setChecked(true);
            fdec();
        }
//
        else if (item.getItemId ()==R.id.oct)
        {
            roct.callOnClick();
            roct.setChecked(true);
            foct();
        }
//
        else if (item.getItemId ()==R.id.Hex)
        {
            rhex.callOnClick();
            rhex.setChecked(true);
            fhex();
        }
        return false;
    }
    //
//////////////////////////////////////////////////////////////////
//
    private void cleaR()
    {
        rbin.setSelected(false); rdec.setSelected(false); roct.setSelected(false); rhex.setSelected(false);
        clearSelection(nav);
        /* rg.clearFocus();*/ rg.clearCheck();
        input.setText(""); abin.setText(""); adec.setText(""); aoct.setText(""); ahex.setText("");
    }
    //
    @SuppressLint("RestrictedApi")
    void fhex()
    {
        //nav.setSelectedItemId(R.id.oct);
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) nav.getChildAt(0);
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(3);
        item.setChecked(true);
    }
    //
    @SuppressLint("RestrictedApi")
    void foct()
    {
        //nav.setSelectedItemId(R.id.oct);
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) nav.getChildAt(0);
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(2);
        item.setChecked(true);
    }
    //
    @SuppressLint("RestrictedApi")
    void fdec()
    {
        //nav.setSelectedItemId(R.id.oct);
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) nav.getChildAt(0);
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(1);
        item.setChecked(true);
    }
    //
    @SuppressLint("RestrictedApi")
    void fbin()
    {
        //nav.setSelectedItemId(R.id.oct);
        final BottomNavigationMenuView menuView;
        menuView = (BottomNavigationMenuView) nav.getChildAt(0);
        BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(0);
        item.setChecked(true);
    }
    //
    private void fuN()
    {
        String s=input.getText ().toString ();
        if (s.isEmpty ())
        {
            //input.setText("");
            abin.setText("");
            adec.setText("");
            aoct.setText("");
            ahex.setText("");
            rbin.setSelected(rbin.isSelected());
            rdec.setSelected(rdec.isSelected());
            roct.setSelected(roct.isSelected());
            rhex.setSelected(rhex.isSelected());
            //Toast.makeText(getApplicationContext(), " No number is provided ;-) ", Toast.LENGTH_SHORT).show();
            return;
        }
//
        if(rbin.isSelected())
        {
            long decimal =Long.parseLong(s,2);
            adec.setText(""+decimal);
            String binary = toBinaryString(decimal);
            abin.setText(binary);
            String octal=Long.toOctalString(decimal );
            aoct.setText(octal);
            String hexadecimal =Long.toHexString(decimal);
            ahex.setText(hexadecimal);
            return;
        }
//
        if(rdec.isSelected())
        {
            long num=Long.parseLong(s);
            adec.setText(""+num);
            String binary = toBinaryString(num);
            abin.setText(binary);
            String octal=Long.toOctalString(num);
            aoct.setText(octal);
            String hexadecimal =Long.toHexString(num);
            ahex.setText(hexadecimal);
            return;
        }
//
        if(roct.isSelected())
        {
            long decimal =Long.parseLong(s,8);
            adec.setText(""+decimal);
            String octal=Long.toOctalString(decimal);
            aoct.setText(octal);
            String binary = toBinaryString(decimal);
            abin.setText(binary);
            String hexadecimal =Long.toHexString(decimal);
            ahex.setText(hexadecimal);
            return;
        }
//
        if(rhex.isSelected())
        {
            long decimal =Long.parseLong(s,16);
            adec.setText(""+decimal);
            String hexadecimal =Long.toHexString(decimal);// 001F == 1f
            ahex.setText(hexadecimal);
            String binary = toBinaryString(decimal);
            abin.setText(binary);
            String octal=Long.toOctalString(decimal );
            aoct.setText(octal);
            return;
        }
        clear.callOnClick();
        Toast.makeText(getApplicationContext(), " Please Select Number System :) ", Toast.LENGTH_LONG).show();
    }
    //
///////////////////////////////////////////////////////////////////
//
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v==input)
        {
            if (!hasFocus) {
                Log.d("focus", "focus lost");
                // Do whatever you want here
            } else {
                Toast.makeText(getApplicationContext(), " Tap outside edittext to lose focus ", Toast.LENGTH_SHORT).show();
                Log.d("focus", "focused");
            }
        }
    }
    //
    // <!-- Clear focus on touch outside for all EditText inputs. "Clear focus input"
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

// "Clear focus input" -->

}