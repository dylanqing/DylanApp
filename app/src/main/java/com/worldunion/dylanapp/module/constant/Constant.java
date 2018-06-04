package com.worldunion.dylanapp.module.constant;

/**
 * @author Dylan
 * @time 2017/3/29 10:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface Constant {

    int REQUESTCODE_LOAD_CONTACTS = 0;
    int REQUESTCODE_LOAD_CONTACTS2 = 1;



    /*修改头像*/
    // 拍照
    int REQUESTCODE_CHANGE_HEAD_PHOTO = 1;
    // 缩放
    int REQUESTCODE_CHANGE_HEAD_PHOTO_ZOOM = 2;
    // 结果
    int REQUESTCODE_CHANGE_HEAD_PHOTO_RESOULT = 3;

    String CHANGE_HEAD_IMAGE_DATA_TYPE = "image/*";
    //头像文件名称
    String IMAGE_HEAD_NAME = "userHead.jpg";
    //头像文件名称
    String CUSTOMER_IMAGE_HEAD_NAME = "_customerHead.jpg";



}
