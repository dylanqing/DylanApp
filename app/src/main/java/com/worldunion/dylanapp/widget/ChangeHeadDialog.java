package com.worldunion.dylanapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.utils.CommonUtils;


/**
 * @author Dylan
 * @time 2016/12/15 16:05
 * @des 修改头像对话框
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ChangeHeadDialog {

    private Dialog mDialog;
    /*相册*/
    private TextView mTvAlbum;
    /*拍照*/
    private TextView mTvPhoto;

    public void init(Context context) {

        mDialog = new Dialog(context, R.style.CommonDialog);
        mDialog.show();

        View view = View.inflate(context, R.layout.item_dialog_change_head, null);

        mTvAlbum = (TextView) view.findViewById(R.id.tvAlbum);
        mTvPhoto = (TextView) view.findViewById(R.id.tvPhoto);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int) (CommonUtils.getScreenWidth(context));
        // params.height = (int) (UIUtils.getScreenHeight() * 0.7);

        Window window = mDialog.getWindow();
        window.setContentView(view);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_anim);
        mDialog.hide();
    }

    /**
     * @param album 相册
     * @param photo 拍照
     */
    public void show(View.OnClickListener album, View.OnClickListener photo) {
        if (mDialog.isShowing()) {
            mDialog.hide();
        }
        mDialog.show();
        if (album != null) {
            mTvAlbum.setOnClickListener(album);
        }
        if (photo != null) {
            mTvPhoto.setOnClickListener(photo);
        }
    }

    /**
     * @param album 相册
     */
    public void show(View.OnClickListener album) {
        mTvPhoto.setVisibility(View.GONE);
        if (mDialog.isShowing()) {
            mDialog.hide();
        }
        mDialog.show();
        if (album != null) {
            mTvAlbum.setOnClickListener(album);
        }
    }

    public void hide() {
        if (mDialog.isShowing()) {
            mDialog.hide();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


}
