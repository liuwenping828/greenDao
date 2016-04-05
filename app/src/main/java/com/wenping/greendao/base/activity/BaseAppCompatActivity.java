package com.wenping.greendao.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 *  Activity base
 *  function: EventBus 注册初始化
 *  采用
 * Created by Administrator on 2016/4/5.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /** Screen information */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**采用弱引用activity 执行handler处理 Message*/
    public MyHandler mHandler;


    /**
     * judge reisterEvent or not
     * @return
     */
    protected boolean isRegisterEvent() {
        return false;
    }

    /**
     * get bundle data
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {}

    /**
     * 获得主界面的layout的id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化特别的界面.如果定制的Head等等就会用到.
     */
    protected void initSpecialView() {
    }

    /**
     * 初始化界面
     */
    protected abstract void initViews();

    /**
     * get Handler mes to deal the job
     * @param msg
     */
    public void handlerMessage(Message msg) {}

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        mHandler = new MyHandler(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initSpecialView();
        initViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEvent()){
            // EventBus 注册
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEvent()){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * startActivity
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     * @param clazz
     */
    public void startActivityAndFinish(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     * @param clazz
     * @param bundle
     */
    public void startActivityAndFinish(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     * @param clazz
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void startActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 关于Handle 对象处理Message 内存泄漏解决
     * 详情请查看 http://www.jianshu.com/p/63aead89f3b9
     */
    public static class MyHandler extends Handler{

        private final WeakReference<BaseAppCompatActivity> mActivity;

        public MyHandler(BaseAppCompatActivity activity) {
            this.mActivity = new WeakReference<BaseAppCompatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseAppCompatActivity activity = mActivity.get();
            if (null != activity){
                // do you think something
                activity.handlerMessage(msg);
            }
        }
    }

}
