package com.worldunion.dylanapp.module.scheme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.unit.blur.BlurActivity;
import com.worldunion.dylanapp.unit.changehead.ChangeHeadActivity;
import com.worldunion.dylanapp.unit.okhttp.OkHttpActivity;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 跳转能力功能集合类
 */
public class BaseIntentUtil {

    /**
     * 使用跳转的时候
     * dylan://changeHead?name=dylan&phone=137
     */
    public static final String SCHEME_MAST = "dylan";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final String SCHEME_WWW = "www";
    public static final String SCHEME_TPMAST = "innerdylan";// 内部使用的scheme
    public static final String SCHEME_FORMAXLIFE = "dylan";


    // ------------------------ 程序内的山寨协议常量定义开始 --------------
    interface Host {
        /*选择头像*/
        String CHANGE_HEAD = "changeHead";
        /*身份证识别*/
        String IDCARAD_SCAN = "idcardscan";
        /*扫一扫页面*/
        String QR_SCAN = "scanQRCode";
        /*搜索页面*/
        String SEARCH = "search";
        /*H5页面*/
        String PAGE_WEBVIEW = "webview";

        String OKHTTP_ACTIVITY = "okhttpActivity";


        /*高斯模糊*/
        String GAUSSIAN_BLUR = "gaussian_blur";
    }

    private static ArrayList<String> hostMap = new ArrayList<>();

    static {
        genHostMap(); //自动将上述定义添加到map中，避免漏加后无法处理协议
    }
    // ------------------------ 程序内的山寨协议常量定义结束 --------------

    public static final String KEY_ACTION_URL = "com.formax.ACTION_URL";

    protected static boolean onMast(Context context, Uri uri, Bundle extras) {

        try {

            String host = uri.getHost();
            if (Host.CHANGE_HEAD.equals(host)) {
                return onChangeHead(context, uri);
            } else if (Host.QR_SCAN.equals(host)) {
                return onQRScan(context, uri);
            } else if (Host.OKHTTP_ACTIVITY.equals(host)) {
                return onOkHttpActivity(context);
            } else if (Host.GAUSSIAN_BLUR.equals(host)) {
                return onGaussianBlur(context, uri);
            }
            return false;
        } catch (Exception e) {
            KLog.e("Exception", "printStackTrace()--->", e);
            return false;
        }
    }

    private static boolean onOkHttpActivity(Context context) {
        OkHttpActivity.launch(context);
        return true;
    }

    private static boolean onQRScan(Context context, Uri uri) {
        return true;
    }


    /**
     * 修改头像
     *
     * @param context
     * @param uri
     * @return
     */
    private static boolean onChangeHead(final Context context, final Uri uri) {
        ChangeHeadActivity.launch(context);
        return true;
    }

    /**
     * 高斯模糊
     *
     * @param context
     * @param uri
     * @return
     */
    private static boolean onGaussianBlur(final Context context, final Uri uri) {
        BlurActivity.launch(context);
        return true;
    }


    private static boolean onFinish(Context context, Uri uri) {
        if (context != null && context instanceof Activity) {
            ((Activity) context).finish();
            return true;
        } else {
            return false;
        }
    }


//    public static void reStartApp(String url) {
//        Intent it = AppUtils.getPackageManager()
//                .getLaunchIntentForPackage(AppUtils.getApplication().getBaseContext().getPackageName());
//        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        it.putExtra(IntentConstant.KEY_URL, url);
//        it.putExtra(IntentConstant.KEY_REBOOT, true);
//        AppUtils.getApplication().startActivity(it);
//    }

    protected static Uri buildUri(String scheme, String host, HashMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append(scheme);
        sb.append("://");
        sb.append(host);
        if (map != null && map.size() > 0) {
            sb.append("?");
            Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
            Map.Entry<String, String> entry;
            while (iter.hasNext()) {
                entry = iter.next();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue()));
                sb.append("&");
            }
        }
        if (sb.toString().endsWith("&")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return Uri.parse(sb.toString());
    }

    protected static boolean onHttp(Context context, Uri uri, Bundle extras) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", uri.toString());
        return onWebView(context, buildUri(SCHEME_MAST, Host.PAGE_WEBVIEW, map), extras);
    }

    /**
     * 是否有activity可以处理
     */
    protected static boolean hasAbility(Context context, Intent intent) {

        /* 下方这个逻辑用来判断从外部跳转时，mast下的host是否能解析成功 */
        Uri uri = intent.getData();

        if (uri != null) {
            String scheme = uri.getScheme();

            if (scheme != null && scheme.equals(SCHEME_MAST)) {
                String host = uri.getHost();
                return (isHostAvailable(host));
            }
        }

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        return list != null && list.size() > 0;
    }

    /**
     * host是否是版本里能正常解析成Activity的判断
     */
    protected static boolean isHostAvailable(String host) {
        return hostMap.contains(host);
    }


    public static void genHostMap() {
        try {
            Class<?> baseIntentClass = Class.forName(BaseIntentUtil.Host.class.getName());
            Field[] fields = baseIntentClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(baseIntentClass);

                    if (value instanceof String) {
                        hostMap.add((String) value);
                    }
                } catch (Exception e) {
                    KLog.e("Exception", "printStackTrace()--->", e);
                }
            }

        } catch (ClassNotFoundException e) {
            KLog.e("Exception", "printStackTrace()--->", e);
        }

    }


    protected static boolean onWebView(Context context, Uri uri, Bundle extras) {
//        String url = uri.getQueryParameter("url");
//        if (url != null && !url.startsWith("http")) {
//            IntentUtil.innerForward(context, url);
//            return true;
//        }
//
//        Set<String> params = uri.getQueryParameterNames();
//        if (params.size() > 0) {
//            url = URLDecoder.decode(uri.toString().replace("formaxcredit://webview?url=", ""));
//        }
//        if (TextUtils.isEmpty(url)) {
//            return false;
//        }
//        HTML5Utils.startH5Activity(context, new WebUrlCommon(url), extras);
        return true;
    }

}
