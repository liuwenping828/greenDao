package com.wenping.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wenping.greendao.dao.CustomerDao;
import com.wenping.greendao.dao.DaoMaster;
import com.wenping.greendao.dao.NoteDao;
import com.wenping.greendao.dao.OrderDao;


/**
 * 数据库更新
 * Created by Administrator on 2016/3/30.
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG = MySQLiteOpenHelper.class.getSimpleName();

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading schema from version " + oldVersion + " to " + newVersion + " by migrating all tables data");
        //  注意把所新版本的表的xxDao都添加到这里
        MigrationHelper.getInstance().migrate(db,CustomerDao.class,OrderDao.class,NoteDao.class);

    }
}
