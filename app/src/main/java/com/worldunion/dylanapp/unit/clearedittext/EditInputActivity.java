package com.worldunion.dylanapp.unit.clearedittext;

import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.utils.EditInputFilterEmoji;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Dylan
 * @time 2017/3/29 15:12
 * @des 带删除按钮的edittext
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class EditInputActivity extends BaseMvpActivity {

    @BindView(R.id.etPwd)
    EditText mEtPwd;
    @BindView(R.id.ivPwdVisible)
    ImageView mIvPwdVisible;
    /*是否密码可见*/
    private boolean pwdVisible = false;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_editinput;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        /*禁止输入emoji表情*/
        mEtPwd.setFilters(new InputFilter[]{new EditInputFilterEmoji()});
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.ivPwdVisible})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPwdVisible:
                /*密码可见*/
                if (pwdVisible) {
                    /*设置EditText文本为可见的*/
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtPwd.setSelection(mEtPwd.getText().length());//光标置末
                } else {
                    //设置EditText文本为隐藏的
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtPwd.setSelection(mEtPwd.getText().length());//光标置末
                }
                mIvPwdVisible.setImageResource(pwdVisible ? R.drawable.regist_pwd_visible : R.drawable.regist_pwd_invisible);
                pwdVisible = !pwdVisible;
                break;
        }
    }

}
