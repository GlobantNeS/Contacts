package com.kaineras.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
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
    private Dao<Contact, Integer> mContactDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<Contact, Integer> getContactDao() throws SQLException {
        if (mContactDao == null) {
            mContactDao = getDao(Contact.class);
        }
        return mContactDao;
    }

    public List<Contact> getContacts() throws SQLException {
        /*for(Contact cTemp:getContactDao().queryForAll())
            Log.v("Query_Res",cTemp.getName());*/
        return getContactDao().queryForAll();
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

    public Contact createContactFromData(String name, String lastname, String nickname, byte[] ima) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setLastName(lastname);
        contact.setNickName(nickname);
        contact.setImage(ima);
        return contact;
    }

    public void saveContact(Contact contact) {
        try {
            Dao<Contact, Integer> dao = getContactDao();
            dao.create(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(Contact contact) {
        try {
            Dao<Contact, Integer> dao = getContactDao();
            dao.delete(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(Contact contact)
    {
        try {
            Dao<Contact,Integer> dao = getContactDao();
            dao.update(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getContact(int id) throws SQLException {
        Contact contact;
        mContactDao=getContactDao();
        QueryBuilder queryBuilder = mContactDao.queryBuilder();
        queryBuilder.setWhere(queryBuilder.where().eq("_id",id));
        contact = mContactDao.queryForFirst(queryBuilder.prepare());
        return contact;
    }

}