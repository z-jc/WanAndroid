package com.dq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2018/8/7.
 */
public class SPUtil {

    public static String User = "user-config";
    public static SPUtil mInstance = null;
    public static SharedPreferences mSharedPreferences;

    public static SPUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SPUtil.class) {
                if (mInstance == null) {
                    mInstance = new SPUtil();
                    mSharedPreferences = context.getSharedPreferences(User, Context.MODE_PRIVATE);
                }
            }
        }
        return mInstance;
    }

    /**
     * 写入字符串型数据
     */
    public static void setString(String key, String value) {
        synchronized (SPUtil.class) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * 获取字符串型数据
     */
    public static String getString(String key) {
        synchronized (SPUtil.class) {
            if(key.contains("audio_type")){
                return mSharedPreferences.getString(key, "us");
            }
            return mSharedPreferences.getString(key, "");
        }
    }

    /**
     * 写入整型数据
     */
    public static void setInteger(String key, int value) {
        synchronized (SPUtil.class) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    /**
     * 获取整型数据
     */
    public static Integer getInteger(String key) {
        synchronized (SPUtil.class) {
            return mSharedPreferences.getInt(key, 0);
        }
    }

    /**
     * 写入布尔型数据
     */
    public static void setBoolean(String key, boolean value) {
        synchronized (SPUtil.class) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    /**
     * 获取布尔型数据
     */
    public static boolean getBoolean(String key) {
        synchronized (SPUtil.class) {
            return mSharedPreferences.getBoolean(key, false);
        }
    }

    /**
     * 写入长整型数据
     */
    public static void setLong(String key, long value) {
        synchronized (SPUtil.class) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    /**
     * 获取长整型数据
     */
    public static long getLong(String key) {
        synchronized (SPUtil.class) {
            return mSharedPreferences.getLong(key, 0);
        }
    }

    public static boolean contains(String key) {
        if (mSharedPreferences.contains(key)) {
            return true;
        }
        return false;
    }

    /**
     * 保存实体类
     */
    public static <T> void setSerialization(String key, T t) {
        synchronized (SPUtil.class) {
            ByteArrayOutputStream bos;
            ObjectOutputStream oos = null;
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(t);
                byte[] bytes = bos.toByteArray();
                String ObjStr = Base64.encodeToString(bytes, Base64.DEFAULT);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(key, ObjStr);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (oos != null) {
                    try {
                        oos.flush();
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取实体类
     *
     * @param key
     * @return
     */
    public static <T extends Object> T getSerialization(String key) {
        synchronized (SPUtil.class) {
            byte[] bytes = Base64.decode(mSharedPreferences.getString(key, ""), Base64.DEFAULT);
            ByteArrayInputStream bis;
            ObjectInputStream ois = null;
            T obj = null;
            try {
                bis = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bis);
                obj = (T) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return obj;
        }
    }
}