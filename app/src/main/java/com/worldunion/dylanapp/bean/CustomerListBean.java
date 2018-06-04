package com.worldunion.dylanapp.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author Dylan
 * @time 2016/12/13 15:24
 * @des 客户列表数据
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
@DatabaseTable(tableName = "tb_customer")
public class CustomerListBean implements Serializable {

    private static final long serialVersionUID = -7642444947575749634L;

    @DatabaseField(generatedId = true, columnName = "s_id")
    private int s_id;

    /*客户id*/
    @DatabaseField(columnName = "customerId")
    private long customerId;

    /*客户名称*/
    @DatabaseField(columnName = "cName")
    private String cName;

    /*客户手机*/
    @DatabaseField(columnName = "cMobile")
    private String cMobile;

    /*客户名称首字母*/
    @DatabaseField(columnName = "nameFirstChar")
    private String nameFirstChar;

    /*是否是新客户(0:新客户，1：不是新客户)*/
    @DatabaseField(columnName = "newCustomer")
    private int newCustomer;

    /*头像URL*/
    @DatabaseField(columnName = "headImgUrl")
    private String headImgUrl;

    /*自定义字段，新客户标志*/
    @DatabaseField(columnName = "isNew")
    private boolean isNew;

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getCMobile() {
        return cMobile;
    }

    public void setCMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    public String getNameFirstChar() {
        return nameFirstChar;
    }

    public void setNameFirstChar(String nameFirstChar) {
        this.nameFirstChar = nameFirstChar;
    }

    public int getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(int newCustomer) {
        this.newCustomer = newCustomer;
    }

    @Override
    public String toString() {
        return "CustomerListBean{" +
                "customerId=" + customerId +
                ", cName='" + cName + '\'' +
                ", cMobile='" + cMobile + '\'' +
                ", nameFirstChar='" + nameFirstChar + '\'' +
                ", newCustomer=" + newCustomer +
                ", isNew=" + isNew +
                '}';
    }
}
