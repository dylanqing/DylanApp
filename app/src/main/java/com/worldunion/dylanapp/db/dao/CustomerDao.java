package com.worldunion.dylanapp.db.dao;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.worldunion.dylanapp.bean.CustomerListBean;
import com.worldunion.dylanapp.db.base.BaseDao;
import com.worldunion.dylanapp.utils.CommonUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Dylan
 * @time 2017/2/21 10:27
 * @des 客户列表_数据库操作
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CustomerDao extends BaseDao<CustomerListBean> {

    public CustomerDao(OrmLiteSqliteOpenHelper sqliteOpenHelper) {
        super(sqliteOpenHelper);
    }

    /**
     * 根据关键词查找客户
     *
     * @param key 关键词
     * @return
     */
    public List<CustomerListBean> queryCustomer(String key) {
        if (CommonUtils.isEmpty(key)) {
            return null;
        }
        QueryBuilder<CustomerListBean, Integer> builder = daoOpe.queryBuilder();
        try {
            List<CustomerListBean> queryMobile = builder.where().like("cMobile", key + "%").query();
            List<CustomerListBean> queryName = builder.where().like("cName", key + "%").query();
            queryMobile.addAll(queryName);
            return queryMobile;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
