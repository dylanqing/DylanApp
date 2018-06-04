package com.worldunion.dylanapp.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.dylan.baselib.utils.Cn2Spell;
import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.unit.contact.ContactBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jayden on 17/10/11.
 * 通讯录及通话记录相关工具类
 */

public class ContactUtils {
    private static final String TAG = "ContactUtils";

    public static List<ContactBean> getCRContactUsers(Context context) {
        List<ContactBean> contactList = new ArrayList<>();

        String[] colums = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                , ContactsContract.CommonDataKinds.Email.ADDRESS
        };
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, colums, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取手机号码
                String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //当手机号为空或者没有该字段，跳过循环
                if (TextUtils.isEmpty(phoneNum)) {
                    continue;
                }
                //获取联系人姓名：
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String pinYinFirstLetter = Cn2Spell.getPinYinFirstLetter(name).toUpperCase();

                //获取ID
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String result = "name = " + name + "----phoneNum = " + phoneNum + "---pinYinFirstLetter = " + pinYinFirstLetter;
                //LogUtil.d(TAG, result);
                ContactBean bean = new ContactBean();
                bean.setName(name);
                bean.setPhoneNum(phoneNum == null ? "" : phoneNum.replace(" ", ""));
                contactList.add(bean);
            }
            cursor.close();
        }
        return contactList;
    }

    /**
     * 获取通话记录
     *
     * @param context
     * @return
     */
//    public static List<FormaxCreditProto.CRSaveCustomerInfoRequest.PhoneCallRecord> getCallRecord(Context context) {
//        List<FormaxCreditProto.CRSaveCustomerInfoRequest.PhoneCallRecord> mArrayList = new ArrayList<>();
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
//            ContentResolver cr = context.getContentResolver();
//            Cursor cs = cr.query(CallLog.Calls.CONTENT_URI, //系统方式获取通讯录存储地址
//                    new String[]{
//                            CallLog.Calls.CACHED_NAME,  //姓名
//                            CallLog.Calls.NUMBER,    //号码
//                            CallLog.Calls.TYPE,  //呼入/呼出(2)/未接
//                            CallLog.Calls.DATE,  //拨打时间
//                            CallLog.Calls.DURATION   //通话时长
//                    }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
//
//            int i = 0;
//            if (cs != null && cs.getCount() > 0) {
//                for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
//                    String callName = "";
//                    callName = cs.getString(0);
//                    String callNumber = cs.getString(1);
//                    //通话类型
//                    int callType = Integer.parseInt(cs.getString(2));
//                    String callTypeStr = "";
//                    switch (callType) {
//                        case CallLog.Calls.INCOMING_TYPE:
//                            callTypeStr = "呼入";
//                            break;
//                        case CallLog.Calls.OUTGOING_TYPE:
//                            callTypeStr = "呼出";
//                            break;
//                        case CallLog.Calls.MISSED_TYPE:
//                            callTypeStr = "未接";
//                            break;
//                    }
//                    //拨打时间
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date callDate = new Date(Long.parseLong(cs.getString(3)));
//                    String callDateStr = sdf.format(callDate);
//                    //通话时长
//                    int callDuration = Integer.parseInt(cs.getString(4));
//                    int min = callDuration / 60;
//                    int sec = callDuration % 60;
//                    String callDurationStr = min + "分" + sec + "秒";
//                   /* String callOne = "类型：" + callTypeStr + ", 称呼：" + callName + ", 号码："
//                            + callNumber + ", 通话时长：" + callDurationStr + ", 时间:" + callDateStr
//                            + "\n---------------------\n";*/
//                    FormaxCreditProto.CRSaveCustomerInfoRequest.PhoneCallRecord phoneCallRecord = new FormaxCreditProto.CRSaveCustomerInfoRequest.PhoneCallRecord();
//                    if (!TextUtils.isEmpty(callDateStr)) {
//                        phoneCallRecord.setContactDuration(callDurationStr);
//                    }
//
//                    if (!TextUtils.isEmpty(callName)) {
//                        phoneCallRecord.setContactName(callName);
//                    }
//
//                    if (!TextUtils.isEmpty(callNumber)) {
//                        phoneCallRecord.setContactPhone(callNumber);
//                    }
//
//                    if (!TextUtils.isEmpty(callDateStr)) {
//                        phoneCallRecord.setContactTime(callDateStr);
//                    }
//
//                    i++;
//                    mArrayList.add(phoneCallRecord);
//                }
//                cs.close();
//            }
//        }
//        return mArrayList;
//    }

    //加载本地通讯录
//    public static Collection<CustomerPhone> loadContacts(Context context, String stage) {
//        if (context == null) {
//            return null;
//        }
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
//
//        //(1)从 Contacts 中 获得各通讯录的 id, 遍历每一个id
//        ContentResolver contentResolver = context.getContentResolver();
//        if (contentResolver == null) {
//            return null;
//        }
//
//        try {
//
//            //只需要从Contacts中获取ID，其他的都可以不要，通过查看上面编译后的SQL语句，可以看出将第二个参数
//            //设置成null，默认返回的列非常多，是一种资源浪费。
//            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
//                    new String[]{ContactsContract.Contacts._ID},
//                    null,
//                    null,
//                    null);
//            if (cursor == null) {
//                return null;
//            }
//
//            //获得分组的信息
//            HashMap<String, String> groupMap = getGroupMap(context);
//            HashMap<String, CustomerPhone> idMap = new HashMap<>();
//
//            while (cursor.moveToNext()) {
//                //拿到某一个id
//                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                CustomerPhone customerPhone = new CustomerPhone();
//                customerPhone.stage = stage;
//                customerPhone.userId = String.valueOf(UserInfoUtils.getUid());
//                customerPhone.serialNumber = SerialNumberUtils.getSerialNumber();
//
//                idMap.put(id, customerPhone);
//
//                //根据这个id, 去 Data表中 去查询 其具体的信息
//                Cursor contactInfoCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
//                        new String[]{ContactsContract.Data.CONTACT_ID,
//                                ContactsContract.Data.MIMETYPE,
//                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
//                                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
//                                ContactsContract.CommonDataKinds.Phone.NUMBER,
//                                ContactsContract.CommonDataKinds.Phone.TYPE,
//                                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID},
//                        ContactsContract.Data.CONTACT_ID
//                                + "="
//                                + id,
//                        null,
//                        null);
//                if (contactInfoCursor == null) {
//                    continue;
//                }
//                while (contactInfoCursor.moveToNext()) {
//                    //根据 mimetype 来决定取什么字段
//                    String mimetype = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(
//                            ContactsContract.Data.MIMETYPE));
//
//                    if (TextUtils.equals(mimetype,
//                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
//                        //姓名结构
//                        String
//                                displayName =
//                                contactInfoCursor.getString(contactInfoCursor.getColumnIndexOrThrow(
//                                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
//                        String
//                                givenName =
//                                contactInfoCursor.getString(contactInfoCursor.getColumnIndexOrThrow(
//                                        ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
//                        String
//                                familyName =
//                                contactInfoCursor.getString(contactInfoCursor.getColumnIndexOrThrow(
//                                        ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
////                        Log.e("contact",
////                              "id:" + id
////                              + "\t\tdisplayName:" + displayName
////                              + "\t\tgivenName:" + givenName
////                              + "\t\tfamilyName:" + familyName
////                             );
//
//                        customerPhone.name = displayName;
//                        customerPhone.surname = givenName;
//                        customerPhone.lastName = familyName;
//
//                    } else if (TextUtils.equals(mimetype,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
//                        //电话信息
//                        if (TextUtils.isEmpty(customerPhone.phoneNum)) {
//                            String
//                                    number =
//                                    contactInfoCursor.getString(contactInfoCursor.getColumnIndexOrThrow(
//                                            ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            if (number == null || TextUtils.isEmpty(number.trim())) {
//                                continue;
//                            }
//                            number =
//                                    number.trim()
//                                            .replace(" ", "");
//                            int
//                                    type =
//                                    contactInfoCursor.getInt(contactInfoCursor.getColumnIndexOrThrow(
//                                            ContactsContract.CommonDataKinds.Phone.TYPE));
//                            String phoneTypeName = null;
//                            switch (type) {
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
//                                    phoneTypeName = "custom";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
//                                    phoneTypeName = "home";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
//                                    phoneTypeName = "mobile";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
//                                    phoneTypeName = "work";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
//                                    phoneTypeName = "fax_work";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
//                                    phoneTypeName = "fax_home";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
//                                    phoneTypeName = "main";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
//                                default:
//                                    phoneTypeName = "other";
//                                    break;
//                            }
////                            Log.e("contact",
////                                  "id:" + id
////                                  + "\t\tnumber:" + number
////                                  + "\t\tphonename:" + phoneTypeName
////                                 );
//                            customerPhone.phoneNum = number;
//                            customerPhone.phoneType = phoneTypeName;
//                        }
//                    } else if (TextUtils.equals(mimetype,
//                            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)) {
//                        //群组信息
//                        if (groupMap == null || groupMap.size() <= 0) {
//                            continue;
//                        }
//                        String
//                                groupRowId =
//                                contactInfoCursor.getString(contactInfoCursor.getColumnIndex(
//                                        ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID));
////                        Log.e("contact",
////                              "id:" + id
////                              + "\t\tgroupRowId:" + groupRowId
////                              + "\t\tgroupTitle:" + groupMap.get(groupRowId)
////                             );
//                        if (TextUtils.isEmpty(customerPhone.group)) {
//                            customerPhone.group = groupMap.get(groupRowId);
//                        } else {
//                            customerPhone.group = customerPhone.group + ", " + groupMap.get(
//                                    groupRowId);
//                        }
//                    }
//                }
//                contactInfoCursor.close();
//            }
//            cursor.close();
//            return idMap.values();
//        } catch (Exception e) {
//            BuglyReport.postCatchedException(e);
//            BuglyReport.e("contact", "loadContacts", e);
//            return null;
//        }
//    }

    public static HashMap<String, String> getGroupMap(Context context) {
        if (context == null) {
            return null;
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        try {
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return null;
            }

            //通过groupId来进行查询
            Cursor groupCursor = contentResolver.query(ContactsContract.Groups.CONTENT_URI,
                    new String[]{ContactsContract.Groups._ID,
                            ContactsContract.Groups.TITLE},
                    null,
                    null,
                    null);
            if (groupCursor == null) {
                return null;
            } else {
                HashMap<String, String> groupMap = new HashMap<>();
                while (groupCursor.moveToNext()) {
                    String groupRowId = groupCursor.getString(groupCursor.getColumnIndex(
                            ContactsContract.Groups._ID));
                    String groupTitle = groupCursor.getString(groupCursor.getColumnIndex(
                            ContactsContract.Groups.TITLE));
//                    Log.e("contact",
//                          "groupRowId:" + groupRowId
//                          + "\t\tgroupTitle:" + groupTitle
//                         );
                    groupMap.put(groupRowId, groupTitle);
                }
                groupCursor.close();
                return groupMap;
            }
        } catch (Exception e) {
            return null;
        }
    }

//    public static List<CustomerCallLog> getCustomerCallLogList(Context context, String stage) {
//        if (context == null) {
//            return null;
//        }
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG)
//                != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
//
//        try {
//            ContentResolver cr = context.getContentResolver();
//            Cursor cs = cr.query(CallLog.Calls.CONTENT_URI,
//                    new String[]{CallLog.Calls.NUMBER,
//                            CallLog.Calls.TYPE,
//                            CallLog.Calls.DATE,
//                            CallLog.Calls.DURATION},
//                    null,
//                    null,
//                    CallLog.Calls.DEFAULT_SORT_ORDER);
//            if (cs == null || cs.getCount() <= 0) {
//                return null;
//            }
//
//            List<CustomerCallLog> list = new ArrayList<>();
//
//            for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
//                String number = cs.getString(cs.getColumnIndex(CallLog.Calls.NUMBER));
//                String type = cs.getString(cs.getColumnIndex(CallLog.Calls.TYPE));
//                long date = cs.getLong(cs.getColumnIndex(CallLog.Calls.DATE));
//                long duration = cs.getLong(cs.getColumnIndex(CallLog.Calls.DURATION));
//
////                Log.e("contact",
////                      "number:" + number
////                      + "\t\ttype:" + type
////                      + "\t\tdate:" + date
////                      + "\t\tduration:" + duration
////                     );
//
//                CustomerCallLog callLog = new CustomerCallLog();
//                callLog.stage = stage;
//                callLog.userId = String.valueOf(UserInfoUtils.getUid());
//                callLog.serialNumber = SerialNumberUtils.getSerialNumber();
//
//                callLog.callMaster = number;
//                callLog.callType = type;
//                callLog.callBeginTime = date;
//                callLog.callTime = duration;
//
//                list.add(callLog);
//            }
//            cs.close();
//            return list;
//        } catch (Exception e) {
//            BuglyReport.postCatchedException(e);
//            BuglyReport.e("contact", "getCustomerCallLogList", e);
//            return null;
//        }
//    }

    /**
     * 将emoji表情替换成空串
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;
        }
    }


    /**
     * 添加联系人
     * 数据一个表一个表的添加，每次都调用insert方法
     */
    public static void testAddContacts(final Context context) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int i = 0; i < 1000; i++) {
                    /* 往 raw_contacts 中添加数据，并获取添加的id号*/
                    Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                    ContentResolver resolver = context.getContentResolver();
                    ContentValues values = new ContentValues();
                    long contactId = ContentUris.parseId(resolver.insert(uri, values));

                    /* 往 data 中添加数据（要根据前面获取的id号） */
                    // 添加姓名
                    uri = Uri.parse("content://com.android.contacts/data");
                    values.put("raw_contact_id", contactId);
                    values.put("mimetype", "vnd.android.cursor.item/name");
                    values.put("data2", "国平_"+i);
                    resolver.insert(uri, values);


                    // 添加电话
                    values.clear();
                    values.put("raw_contact_id", contactId);
                    values.put("mimetype", "vnd.android.cursor.item/phone_v2");
                    values.put("data2", "2");
                    values.put("data1", "150990000"+i);
                    resolver.insert(uri, values);

                    KLog.d(TAG,"i = "+i);

                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                });
    }


}
