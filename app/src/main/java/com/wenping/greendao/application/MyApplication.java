package com.wenping.greendao.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.wenping.greendao.constant.Constant;
import com.wenping.greendao.dao.CustomerDao;
import com.wenping.greendao.dao.DaoMaster;
import com.wenping.greendao.dao.DaoSession;
import com.wenping.greendao.helper.MySQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/5.
 */
public class MyApplication extends Application {

    private DaoSession mDaoSession;

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        SharedPreferences sp = getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (sp.getBoolean(Constant.FIRST_LAUNCHER,true)){

            mDaoSession = getDaoSession();
            // 通过 DaoSession 获取实体类dao,对数据进行增删改查
            //Todo : insert query delete update
            CustomerDao customerDao = mDaoSession.getCustomerDao();
            // eg:
            /*Customer customer = new Customer();
            customerDao.insert(customer);
            customerDao.delete(customer);
            customer.update();
            customerDao.delete(customer);*/

            sp.edit().putBoolean(Constant.FIRST_LAUNCHER,false);
        }
    }

    /*
     * GreenDao相关
     */
    public synchronized DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initDaoSession();
        }
        return mDaoSession;
    }

    private void initDaoSession() {
        // 相当于得到数据库帮助对象，用于便捷获取db
        // 这里会自动执行upgrade的逻辑.backup all table→del all table→create all new table→restore data
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "greendao.db", null);
        // 得到可写的数据库操作对象
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获得Master实例,相当于给database包装工具
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取类似于缓存管理器,提供各表的DAO类
        mDaoSession = daoMaster.newSession();
    }
}
