package com.kaineras.contacts;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListContacFragment extends Fragment {


    private DatabaseHelper mDBHelper = null;

    public ListContacFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDBHelper=getDBHelper();
        try {
            mDBHelper.getContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_list_contac, container, false);
    }

    private DatabaseHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }


}
