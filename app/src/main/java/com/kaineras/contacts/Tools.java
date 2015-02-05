package com.kaineras.contacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created the first version by kaineras on 5/02/15.
 */
public class Tools {

    public Tools()
    {

    }
    public void loadFragment(FragmentManager fm,Fragment f,int container,String namestack)
    {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(namestack);
        fragmentTransaction.replace(container, f);
        fragmentTransaction.commit();
    }
}
