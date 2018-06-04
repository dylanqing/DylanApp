package com.worldunion.dylanapp.unit.qrscan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dylan.baselib.utils.log.KLog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxing.activity.CaptureFragment;
import com.zxing.activity.CodeUtils;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by wangxiaoqing on 2017/11/8.
 * 二维码扫描页面
 */

public class QRScanActivity extends BaseMvpActivity {

    @BindView(R.id.flContainer)
    FrameLayout mFlContainer;
    private CaptureFragment captureFragment;

    private boolean isOpen;
    private String TAG = "QRScanActivity";
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    public static final int REQUEST_LOGIN = 115;

    //    private ForeLifeDialog mForeLifeDialog;
    private String mCode;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qrscan;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.item_qr_scan_view);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, captureFragment).commit();

        initPermission();
    }

    /**
     * 请求权限
     */
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        if (result) {
                            /*有权限*/
                            //isPermission = true;
                        } else {
                            /*无权限*/
                            Toast.makeText(QRScanActivity.this, "请开启相机和存储空间权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void getData() {

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            KLog.d(TAG, "result = " + result);
            showToast(result);
            QRScanActivity.launch(QRScanActivity.this);
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(QRScanActivity.this, "扫描失败，请重新扫描", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 打开相册
     */
    @OnClick(R.id.tvAlbum)
    public void onTvAlbumClicked() {
        /*相册*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * 手电筒
     */
    @OnClick(R.id.tvLight)
    public void onTvLight() {
        /*相册*/
        if (!isOpen) {
            CodeUtils.isLightEnable(true);
            isOpen = true;
        } else {
            CodeUtils.isLightEnable(false);
            isOpen = false;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE:
                    /*从相册返回*/
                if (data != null) {
                    Uri uri = data.getData();
                    try {
                        CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                            @Override
                            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                Toast.makeText(QRScanActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onAnalyzeFailed() {
                                Toast.makeText(QRScanActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_LOGIN:
                break;
        }
    }

    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, QRScanActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }
}
