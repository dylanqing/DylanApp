package com.worldunion.dylanapp.bean;

import java.io.Serializable;

/**
 * @author Dylan
 * @time 2017/3/20 10:04
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class UserBean implements Serializable{

    private static final long serialVersionUID = 6934410159989090450L;

    /*Token*/
    private String deviceToken;
    /*是否内部员工*/
    private int isInner;
    /*手机号码*/
    private String mobile;
    /*角色名称*/
    private String roleName;
    /*用户ID*/
    private long userId;
    /*用户名*/
    private String userName;
    /*用户类型*/
    private String userType;
    /*用户uuid*/
    private String userUUID;
    /*头像URL*/
    private String iconUrl;
    /*门店Code*/
    private String storeCode;
    /*门店名称*/
    private String storeName;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public int getIsInner() {
        return isInner;
    }

    public void setIsInner(int isInner) {
        this.isInner = isInner;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }


}
