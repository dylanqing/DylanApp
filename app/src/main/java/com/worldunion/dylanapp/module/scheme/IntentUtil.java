package com.worldunion.dylanapp.module.scheme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;


/**
 * 完整的跳转链接支持能力。这个类基本上只是针对BaseIntentUtils的代理功能，只是增加了三种跳转能力。如果有涉及到跳转能力的支持的修改，请修改BaseIntentUtils类。这点很重要
 */
public class IntentUtil {


    public static final void forward(Context context, String url, Bundle extra) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        forward(context, uri, extra);
    }

    public static final void innerForward(Context context, Bundle bundle, String scheme) {
        if (TextUtils.isEmpty(scheme)) {
            return;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(BaseIntentUtil.KEY_ACTION_URL, scheme);
        IntentUtil.innerForward(context, scheme, bundle);
    }

    public static final void innerForward(Context context, String url, Bundle extra) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (extra == null) {
            extra = new Bundle();
        }
        Uri uri = Uri.parse(url);
        forward(context, uri, extra);
    }

    public static final void forward(Context context, String url) {
        forward(context, url, null);
    }

    public static final void innerForward(Context context, String url) {
        innerForward(context, url, null);
    }


    public static final void forward(Context context, Uri uri, Bundle extra) {

        uri = onWWW(uri);
        String scheme = uri.getScheme();
        if (scheme == null) {
            return;
        }
        //增加一个跳转链接的标记
        if (extra == null) {
            extra = new Bundle();
        }

        if (isSchemeAbility(scheme)) {
            onMast(context, uri, extra);
        } else if (scheme.equals(BaseIntentUtil.SCHEME_HTTP) || scheme.equals(BaseIntentUtil.SCHEME_HTTPS)) {
            if (uri.toString().contains("/life-appwake/download")) {
                //jumpToJrqOrLife(context, uri, extra);
            } else {
                boolean isHttp = onHttp(context, uri, extra);
            }

        }
    }

    public static final void forward(Context context, Uri uri) {
        if (uri == null) {
            return;
        }
        forward(context, uri, null);
    }


    private static boolean onMast(Context context, Uri uri, Bundle extras) {
        return BaseIntentUtil.onMast(context, uri, extras);
    }

    private static boolean onHttp(Context context, Uri uri, Bundle extras) {
        return BaseIntentUtil.onHttp(context, uri, extras);
    }

    private static Uri onWWW(Uri uri) {
        if (uri.toString().startsWith(BaseIntentUtil.SCHEME_WWW)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(BaseIntentUtil.SCHEME_HTTP).append(":").append("//").append(uri.toString());
            uri = Uri.parse(stringBuilder.toString());
        }
        return uri;
    }

    /**
     * 判断是否支持内部scheme或http
     */
    public static boolean hasAbility(String schemeUrl) {
        if (TextUtils.isEmpty(schemeUrl)) {
            return false;
        }
        Uri uri = Uri.parse(schemeUrl);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (isSchemeAbility(scheme)) {
            return BaseIntentUtil.isHostAvailable(host);
        } else {
            return false;
        }
    }

    public static boolean hasAbilityWithHttp(String schemeUrl) {
        return hasAbility(schemeUrl) || (!TextUtils.isEmpty(schemeUrl) && (schemeUrl.startsWith(BaseIntentUtil.SCHEME_HTTP) || schemeUrl.startsWith(BaseIntentUtil.SCHEME_HTTPS)));
    }

    public static boolean isSchemeAbility(String scheme) {
        return (BaseIntentUtil.SCHEME_MAST.equals(scheme) || BaseIntentUtil.SCHEME_TPMAST.equals(scheme) || BaseIntentUtil.SCHEME_FORMAXLIFE.equals(scheme));
    }

//    private static void jumpToJrqOrLife(Context context, Uri uri, Bundle extra) {
//        String url = uri.toString();
//        String tar_host = uri.getQueryParameter("tar_host");
//        String jumpScheme = uri.getQueryParameter("scheme");
//        String jsonParams = uri.getQueryParameter("param");
//
//        if (TextUtils.isEmpty(tar_host) || TextUtils.isEmpty(jumpScheme))
//            return;
//
//        String pageSchemaParams = tar_host + "://" + jumpScheme + "?";
//
//        if (!TextUtils.isEmpty(jsonParams)) {
//            try {
//                JSONObject jsonObject = JSON.parseObject(jsonParams);
//                Set<String> keySet = jsonObject.keySet();
//                Iterator<String> it = keySet.iterator();
//                while (it.hasNext()) {
//                    String key = it.next();
//                    if (!"tar_host".equals(key)) {
//                        Object value = jsonObject.get(key);
//                        pageSchemaParams = pageSchemaParams + key + "=" + value.toString();
//                    }
//                    if (it.hasNext())
//                        pageSchemaParams = pageSchemaParams + "&";
//                }
//            } catch (Exception e) {
//                LogUtil.d(IntentUtil.class.getSimpleName(), "json格式转换出错" + e);
//                return;
//            }
//        }
//
//        if (tar_host.equals("formax")) {
//            if (AppUtils.hasInstalledApp(AppUtils.PACKAGE_NAME_JRQ)) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pageSchemaParams));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                try {
//                    context.startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    //打开失败,跳转到桥页引导下载
//                    url = url + "&is_install_app=" + (AppUtils.hasInstalledApp(AppUtils.PACKAGE_NAME_JRQ) ? "1" : "0") + "&is_app=1";
//                    boolean isHttp = onHttp(context, Uri.parse(url), extra);
//                }
//            } else {
//                url = url + "&is_install_app=" + (AppUtils.hasInstalledApp(AppUtils.PACKAGE_NAME_JRQ) ? "1" : "0") + "&is_app=1";
//                boolean isHttp = onHttp(context, Uri.parse(url), extra);
//            }
//            return;
//        }
//
//        if (tar_host.equals("formaxlife")) {
//            url = pageSchemaParams;
//            boolean isMast = onMast(context, Uri.parse(url), extra);
//        }
//    }
}
