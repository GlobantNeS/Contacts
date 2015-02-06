package com.kaineras.contacts;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListContactFragment extends Fragment {


    private DatabaseHelper mDBHelper = null;
    LinkAdapter mAdapter;
    View v;
    List<Contact> contactList;
    ListView lView;

    public ListContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDBHelper=getDBHelper();
        try {
            contactList=mDBHelper.getContacts();
            mAdapter = new LinkAdapter(contactList, getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        v = inflater.inflate(R.layout.fragment_list_contac, container, false);
        lView = (ListView) v.findViewById(R.id.listView);
        lView.setAdapter(mAdapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("LISTENER",String.valueOf(id));
                ((ContactsListener) getActivity()).ContactsListener(contactList.get(position).get_id());
            }
        });
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

    public void refreshContact() throws SQLException {
        contactList=mDBHelper.getContacts();
        mAdapter.clear();
        mAdapter.addAll(contactList);
        mAdapter.notifyDataSetChanged();
    }


    public interface ContactsListener {
        public void ContactsListener(int id);
    }
}
