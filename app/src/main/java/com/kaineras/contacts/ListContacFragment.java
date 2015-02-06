package com.kaineras.contacts;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListContacFragment extends Fragment {


    private DatabaseHelper mDBHelper = null;
    LinkAdapter mAdapter;
    View v;
    List<Contact> contactList,temp;

    public ListContacFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDBHelper=getDBHelper();
        try {
            contactList=mDBHelper.getContacts();
            for(Contact cTemp:contactList)
                Log.v("Query_Res",cTemp.getName());
            mAdapter = new LinkAdapter(contactList, getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        v = inflater.inflate(R.layout.fragment_list_contac, container, false);
        ListView lView = (ListView) v.findViewById(R.id.listView);
        lView.setAdapter(mAdapter);
        setHasOptionsMenu(true);
        return v;
    }

    public void addContact() throws SQLException {
        contactList=mDBHelper.getContacts();
        Contact temp=contactList.get(contactList.size()-1);
        mAdapter.add(temp);
    }

    private DatabaseHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent=new Intent(getActivity(),AddContact.class);
            startActivity(intent);
            try {
                if(mDBHelper!=null)
                    contactList=mDBHelper.getContacts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
