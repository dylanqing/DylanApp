package com.worldunion.dylanapp.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.worldunion.dylanapp.R;

import java.sql.SQLException;

/**
 * Database helper class used to manage the creation and upgrading of your database.
 * This class also usually provide the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "agency.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            String[] tb = mContext.getResources().getStringArray(R.array.db_table);
            for (int i = 0; i < tb.length; i++) {
                Class clazz = Class.forName(tb[i]);
                TableUtils.createTable(connectionSource, clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            //创建array里面配置的表
            String[] tb = mContext.getResources().getStringArray(R.array.db_table);
            for (int i = 0; i < tb.length; i++) {
                Class clazz = Class.forName(tb[i]);
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        mContext = null;
    }
}
