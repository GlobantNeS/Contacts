package com.kaineras.contacts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_CODE = 0;
    Tools tools=new Tools();
    ListContacFragment lcf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lcf=new ListContacFragment();
        tools.loadFragment(getFragmentManager(),lcf,R.id.container,"");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
            if (resultCode == RESULT_OK)
                try {
                    lcf.addContact();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        if (id == R.id.action_add) {
            Intent intent=new Intent(this,AddContact.class);
            startActivityForResult(intent,REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
