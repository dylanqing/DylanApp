package com.worldunion.dylanapp.unit.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dylan.baselib.utils.Cn2Spell;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.utils.ContactUtils;
import com.worldunion.dylanapp.widget.CommonDialog;
import com.worldunion.dylanapp.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dylan on 2017/10/10.
 * 联系人页面
 */

public class ContactActivity extends BaseMvpActivity {
    public static final String CHOOSED_CONTACT = "choosed_contact";

    @BindView(R.id.lvContact)
    ListView mLvContact;
    @BindView(R.id.tvSideBarBg)
    TextView mTvSideBarBg;
    @BindView(R.id.sideBar)
    SideBar mSideBar;

    private List<ContactBean> mContactList = new ArrayList<>();
    private ContactAdapter mAdapter;
    private CommonDialog mCommonDialog;

    private int mResultCode;


    @Override
    public void initPresenter() {

    }

    @Override
    public void initListener() {
        mLvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = mContactList.get(position).getName();
                String phoneNum = mContactList.get(position).getPhoneNum();
                //Toast.makeText(ContactActivity.this, "name = " + name + "\n" + "phoneNum = " + phoneNum, Toast.LENGTH_SHORT).show();
               /* ContactChoosedEvent choosedEvent = new ContactChoosedEvent();
                choosedEvent.choosedContact = mContactList.get(position);
                choosedEvent.contactList = mContactList;
                FormaxEventBus.g().post(choosedEvent);*/
                LogUtils.d(TAG, "name = " + name + "-------phoneNum = " + phoneNum);
                Intent intent = new Intent();
                intent.putExtra(CHOOSED_CONTACT, mContactList.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void init() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            /*有权限*/
                            dealWithData();
                        } else {
                            /*无权限*/
                            mCommonDialog = new CommonDialog.Builder()
                                    .setDescStr("我知道了")
                                    .build(ContactActivity.this);
                            mCommonDialog.show(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCommonDialog.dismiss();
                                    ContactActivity.this.finish();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCommonDialog.dismiss();
                                }
                            });
                        }
                    }
                });
    }

    private void dealWithData() {
        getContacts();

        /*侧边栏点击监听*/
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int item = mAdapter.getPositionForSection(s.charAt(0));
                if (item != -1) {
                    mLvContact.setSelection(item);
                }
            }
        });
        mSideBar.setTextView(mTvSideBarBg);
    }

    @Override
    protected void getData() {
    }

    /**
     * 查询数据库中手机联系人信息
     */
    private void getContacts() {
        showLoading();
        Observable.create(new ObservableOnSubscribe<List<ContactBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ContactBean>> e) throws Exception {
                String[] colums = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        , ContactsContract.CommonDataKinds.Email.ADDRESS
                };
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, colums, null, null, null);
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
                        bean.setName(ContactUtils.filterEmoji(name));
                        bean.setPhoneNum(phoneNum);
                        if (pinYinFirstLetter.equals("")) {
                            bean.setFirstLetter("#");
                        } else {
                            bean.setFirstLetter(pinYinFirstLetter);
                        }
                        mContactList.add(bean);
                    }
                    cursor.close();
                }
                Collections.sort(mContactList, new NameComperator());
                e.onNext(mContactList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ContactBean>>() {
                    @Override
                    public void accept(List<ContactBean> list) throws Exception {
                        if (mContactList.size() == 0) {
                            mCommonDialog = new CommonDialog.Builder()
                                    .setDescStr("系统未检测到您的通讯录，请确认手机系统设置已对永道e贷APP授权访问通讯录")
                                    .build(ContactActivity.this);
                            mCommonDialog.show(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCommonDialog.dismiss();
                                    ContactActivity.this.finish();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCommonDialog.dismiss();
                                }
                            });
                        }
                        mAdapter = new ContactAdapter(ContactActivity.this, mContactList);
                        mLvContact.setAdapter(mAdapter);
                        hideLoading();
                    }
                });
    }

    /**
     * 排序规则
     */
    public class NameComperator implements Comparator<ContactBean> {
        @Override
        public int compare(ContactBean lhs, ContactBean rhs) {
            if (lhs.getFirstLetter() == null || rhs.getFirstLetter() == null) {
                return 0;
            } else {
                if (rhs.getFirstLetter().equals("#")) {
                    return -1;
                } else if (lhs.getFirstLetter().equals("#")) {
                    return 1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        }
    }
}
