package com.tonian.yanqiang.sudoku;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    public String mode="";
    public SudokuView sudokuView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sudokuView=new SudokuView(this,mode);


        setContentView(sudokuView);
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
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_easy) {
            this.mode="easy";
        }
        else if (id == R.id.action_medium) {
            this.mode="medium";
        }
        else if (id == R.id.action_hard) {
            this.mode="hard";
        }

        sudokuView=new SudokuView(this,mode);


        setContentView(sudokuView);

        return super.onOptionsItemSelected(item);
    }
}
