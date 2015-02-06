package com.kaineras.contacts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;


public class ContactActivity extends ActionBarActivity implements ListContactFragment.ContactsListener{

    private static final int REQUEST_CODE_ADD = 0;
    private static final int REQUEST_CODE_UPD_DEL = 1;
    Tools tools = new Tools();
    ListContactFragment lcf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lcf = new ListContactFragment();
        tools.loadFragment(getFragmentManager(), lcf, R.id.container, "");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_ADD:
                if (resultCode == RESULT_OK)
                    try {
                        lcf.addContact();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                break;
            case REQUEST_CODE_UPD_DEL:
                if (resultCode == RESULT_OK)
                    try {
                        lcf.refreshContact();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddContact.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ContactsListener(int id) {
        Intent intent=new Intent(this,UpdateDeleteContact.class);
        intent.putExtra("ID",id);
        Log.v("ID_USER", String.valueOf(id));
        startActivityForResult(intent, REQUEST_CODE_UPD_DEL);
    }
}
