package com.wenping.greendao.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.wenping.greendao.bean.Customer;
import com.wenping.greendao.bean.Note;
import com.wenping.greendao.bean.Order;
import com.wenping.greendao.bean.TreeEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noteDaoConfig;
    private final DaoConfig customerDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig treeEntityDaoConfig;

    private final NoteDao noteDao;
    private final CustomerDao customerDao;
    private final OrderDao orderDao;
    private final TreeEntityDao treeEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noteDaoConfig = daoConfigMap.get(NoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);

        customerDaoConfig = daoConfigMap.get(CustomerDao.class).clone();
        customerDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        treeEntityDaoConfig = daoConfigMap.get(TreeEntityDao.class).clone();
        treeEntityDaoConfig.initIdentityScope(type);

        noteDao = new NoteDao(noteDaoConfig, this);
        customerDao = new CustomerDao(customerDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        treeEntityDao = new TreeEntityDao(treeEntityDaoConfig, this);

        registerDao(Note.class, noteDao);
        registerDao(Customer.class, customerDao);
        registerDao(Order.class, orderDao);
        registerDao(TreeEntity.class, treeEntityDao);
    }
    
    public void clear() {
        noteDaoConfig.getIdentityScope().clear();
        customerDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
        treeEntityDaoConfig.getIdentityScope().clear();
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public TreeEntityDao getTreeEntityDao() {
        return treeEntityDao;
    }

}
