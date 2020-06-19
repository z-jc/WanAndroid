package com.dq.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * FileName: ShareUtil
 * Author: admin
 * Date: 2020/5/21 14:41
 * Description:原生分享
 */
public class ShareUtil {

    /**
     * 分享文字
     *
     * @param a             上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     */
    public static void shareTxt(Activity a, String activityTitle, String msgTitle, String msgText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain"); // 纯文本
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 原生分享图片
     *
     * @param file     本地图片文件
     * @param activity
     */
    public static void shareImage(File file, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(activity, getAppPackageName(activity) + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setType("image/*");
        Intent chooser = Intent.createChooser(intent, "");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(chooser, 101);
        }
    }

    /**
     * 原生分享视频
     *
     * @param file     本地视频文件
     * @param activity
     */
    public static void shareVideo(File file, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(activity, getAppPackageName(activity) + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setType("video/*");
        Intent chooser = Intent.createChooser(intent, "");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(chooser, 101);
        }
    }

    private static String getAppPackageName(Context context) {
        String packageName = "";
        try {
            packageName = context.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }
}