package com.worldunion.dylanapp.module.constant;

/**
 * @author Dylan
 * @time 2017/2/24 15:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface IConstants {


    /*测试环境*/
    String BASE_URL = "http://192.168.16.68:8300";





    /*根据用户获取未读消息数量*/
    String GET_UNREAD_MESSAGE_COUNT = BASE_URL + "/common/message/getNoReadMessageCount";

    /*登录接口*/
    String LOGIN = BASE_URL + "/login";

}
