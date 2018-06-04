package com.worldunion.dylanapp.unit.changehead;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.module.constant.Constant;
import com.worldunion.dylanapp.utils.FilePathUtils;
import com.worldunion.dylanapp.utils.ImgCtrlUtils;
import com.worldunion.dylanapp.widget.ChangeHeadDialog;
import com.worldunion.dylanapp.widget.CircleImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author Dylan
 * @time 2017/3/29 10:13
 * @des 修改头像从相册和拍照功能的activity
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ChangeHeadActivity extends BaseMvpActivity {

    @BindView(R.id.civHead)
    CircleImageView mCivHead;

    private File mHeadFile;
    private File mOriHeadFile;
    private ChangeHeadDialog mChangeHeadDialog;
    private boolean isPermission;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_change_head;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            /*有权限*/
                            isPermission = true;
                        } else {
                            /*无权限*/
                            isPermission = false;
                        }
                    }
                });
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.civHead})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civHead:
                if (!isPermission) {
                    return;
                }
                /*修改头像*/
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                //获取当前时间，进一步转化为字符串
                /*原图路径*/
                mHeadFile = new File(FilePathUtils.getDefaultImagePath(mContext), fileName + Constant.CUSTOMER_IMAGE_HEAD_NAME);
                /*截取后的路径*/
                mOriHeadFile = new File(FilePathUtils.getDefaultImagePath(mContext), fileName + "_ori" + Constant.CUSTOMER_IMAGE_HEAD_NAME);

                mChangeHeadDialog = new ChangeHeadDialog();
                mChangeHeadDialog.init(this);
                mChangeHeadDialog.show(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*相册*/
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, Constant.REQUESTCODE_CHANGE_HEAD_PHOTO_ZOOM);
                        mChangeHeadDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*拍照*/
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mOriHeadFile));
                        startActivityForResult(cameraIntent, Constant.REQUESTCODE_CHANGE_HEAD_PHOTO);
                        mChangeHeadDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUESTCODE_CHANGE_HEAD_PHOTO:
                    /*拍照*/
                    //设置文件保存路径

                    //startPhotoZoom(Uri.fromFile(mOriHeadFile));
                    startPhotoZoom(data.getData());
                    break;
                case Constant.REQUESTCODE_CHANGE_HEAD_PHOTO_ZOOM:
                    // 读取相册后缩放图片
                    startPhotoZoom(data.getData());
                    break;
                case Constant.REQUESTCODE_CHANGE_HEAD_PHOTO_RESOULT:
                    // 处理结果
                    Bitmap bitmap = BitmapFactory.decodeFile(mHeadFile.getAbsolutePath());
                    mCivHead.setImageBitmap(bitmap);
                    /*保存图片*/
                    //ImgCtrlUtils.save(bitmap, mHeadFile, Bitmap.CompressFormat.JPEG);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 打开裁剪界面
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, Constant.CHANGE_HEAD_IMAGE_DATA_TYPE);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);

        //File imgData = new File(FilePathUtils.getDefaultImagePath(mContext), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_ori" + "customerHead2.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mHeadFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, Constant.REQUESTCODE_CHANGE_HEAD_PHOTO_RESOULT);
    }


    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ChangeHeadActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }
}
