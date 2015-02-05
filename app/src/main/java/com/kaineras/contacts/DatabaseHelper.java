package com.kaineras.contacts;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;


/**
 * Created the first version by kaineras on 5/02/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private final static String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private final static String DATABASE_NAME = "contacts.db";
    private final static int DATABASE_VERSION = 1;
    private Dao<Contact,Integer> mContactDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<Contact, Integer> getContactDao() throws SQLException {
        if (mContactDao == null) {
            mContactDao = getDao(Contact.class);
        }
        return mContactDao;
    }

    public void getContacts() throws SQLException {
        List<Contact> contact=getContactDao().queryForAll();
        for(Contact cTemp:contact)
        {
            Log.v("NAME",cTemp.getName());
            Log.v("LASTNAME",cTemp.getLastName());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "Creating database.");
            TableUtils.createTable(connectionSource, Contact.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed to create database.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}