package com.worldunion.dylanapp.unit.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dylan.baselib.utils.log.KLog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.module.constant.Constant;
import com.worldunion.dylanapp.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.worldunion.dylanapp.unit.contact.ContactActivity.CHOOSED_CONTACT;

/**
 * @author Dylan
 * @time 2017/3/29 10:19
 * @des 跳转系统选择联系人并返回数据
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class LoadContactActivity extends BaseMvpActivity {


    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    private boolean isPermission;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_loadcontact;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            /*有权限*/
                            isPermission = true;
                        } else {
                            /*无权限*/
                            Toast.makeText(LoadContactActivity.this, "请开启读取联系人权限", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", AppUtils.getAppPackageName(), null));
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void getData() {

    }


    @OnClick({R.id.titleBar, R.id.btnClick,R.id.btnCustomClick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClick:
                /*从联系人*/
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(intent, Constant.REQUESTCODE_LOAD_CONTACTS);
                break;
            case R.id.btnCustomClick:
                /*从自定义通讯里导入*/
                showChooseContactPage(Constant.REQUESTCODE_LOAD_CONTACTS2);
                break;
            default:

                break;
        }
    }


    private void showChooseContactPage(final int requestCode) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(LoadContactActivity.this, ContactActivity.class);
                            startActivityForResult(intent, requestCode);
                        } else {
                            ToastUtils.showShort("请允许应用访问您的通讯录");
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUESTCODE_LOAD_CONTACTS:
                    /*从联系人返回*/
                    if (resultCode != Activity.RESULT_OK) {
                        return;
                    }
                    if (CommonUtils.isNotEmpty(data)) {
                        Uri contactData = data.getData();
                        KLog.d("uri = " + contactData.toString());
                        Cursor cursor = managedQuery(contactData, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            // 取得联系人ID
                            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            if (hasPhone > 0) {
                            /*根据id筛选*/
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + " = " + contactId,
                                        null, null);
                                while (phones.moveToNext()) {
                                    String phoneNumber = phones.getString(phones
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    String phoneName = phones.getString(phones
                                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                    phoneNumber = phoneNumber.replace(" ", "");
                                    phoneNumber = phoneNumber.replace("+86", "");
                                    if (phoneNumber.length() != 11) {
                                        showToast(R.string.customer_report_loaderror);
                                        return;
                                    }
                                    mTvName.setText(phoneName);
                                    mTvPhone.setText(phoneNumber);
                                }
                                if (!phones.isClosed()) {
                                    phones.close();
                                }
                            }
                        }
                    }
                    break;
                case Constant.REQUESTCODE_LOAD_CONTACTS2:
                    /*从自定义通讯*/
                    ContactBean bean = (ContactBean) data.getSerializableExtra(CHOOSED_CONTACT);
                    if (bean != null) {
                        String name = bean.getName() == null ? "" : bean.getName();
                        String phone = bean.getPhoneNum() == null ? "" : bean.getPhoneNum().replace(" ", "").replace("+", "").replace("-", "");
                        if (!TextUtils.isEmpty(phone) && phone.indexOf("86") == 0) {
                            phone = bean.getPhoneNum().substring(2);
                        }
                        if (phone.length() > 11) {
                            ToastUtils.showShort("您输入的手机号码格式有误");
                            phone = "";
                        }
                        mTvName.setText(name);
                        mTvPhone.setText(phone);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
