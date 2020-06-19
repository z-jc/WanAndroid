package com.dq.util.file;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dq.util.ILog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * FileName: SaveImageUtils
 * Author: admin
 * Date: 2020/5/22 17:29
 * Description: 图片本地保存
 */
public class SaveImageUtils {

    private static String TAG = SaveImageUtils.class.getSimpleName();
    private static Uri uri;
    public static String IMAGE_NAME = "iv_share";
    public static int i = 0;

    /**
     * @param imageHttpUrl
     * @param context
     * @return
     */
    public static Uri saveHttpToImage(String imageHttpUrl, final Context context, String savePath) {
        Bitmap bitmap = getImageInputStream(imageHttpUrl);
        if (bitmap == null) {
            Log.e(TAG, "保存失败   bitmap == null");
            return null;
        }
        File appDir = new File(savePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = "";
        fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        uri = Uri.fromFile(file);
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
        return uri;
    }

    /**
     * @param bitmap
     * @param context
     * @return
     */
    public static Uri saveBitmapToImage(Bitmap bitmap, final Context context, String savePath) {
        if (bitmap == null) {
            Log.e(TAG, "保存失败   bitmap == null");
            return null;
        }
        File appDir = new File(savePath + "/");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            ILog.e("保存失败:" + e.toString());
            return null;
        }
        uri = Uri.fromFile(file);
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
        return uri;
    }

    /**
     * 获取网络图片
     *
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap getImageInputStream(final String imageurl) {
        URL url;
        HttpURLConnection connection = null;
        Bitmap bitmap = null;
        try {
            url = new URL(imageurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e(TAG, "e:" + e.toString());
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * @param bitmap
     * @param context
     * @return
     */
    public static String saveBitmapImage(Bitmap bitmap, final Context context, String savePath) {
        String path = "";
        if (bitmap == null) {
            Log.e("SaveImageUtil", "保存失败   bitmap == null");
            return null;
        }
        File appDir = new File(savePath);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e(TAG, "保存失败:" + e.toString());
            return null;
        }
        uri = Uri.fromFile(file);
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
//        Log.e(TAG, "下载结束:" + path);
        return path;
    }

    //根据网络图片url路径保存到本地
    public static final File saveImageToSdCard(Context context, String image) {
        boolean success = false;
        File file = null;
        try {
            file = createStableImageFile(context);
            Bitmap bitmap = null;
            URL url = new URL(image);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = null;
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            FileOutputStream outStream;
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            return file;
        } else {
            return null;
        }
    }

    //创建本地保存路径
    public static File createStableImageFile(Context context) throws IOException {
        i++;
        String imageFileName = IMAGE_NAME + i + ".jpg";
        File storageDir = context.getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }

    /**
     * 将网络图片转为Bitmap
     *
     * @param imgHttpPath
     * @param handler
     */
    public static void getHttpImageToBitmap(final String imgHttpPath, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                Bitmap bitmap = null;
                try {
                    imageurl = new URL(imgHttpPath);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();

                    Message msg = handler.obtainMessage();
                    msg.what = 101;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}