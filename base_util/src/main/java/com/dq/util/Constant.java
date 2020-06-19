package com.dq.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class Constant {

    private static String PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/";

    public static String getDCIM() {
        return getPath() + "DCIM/";
    }

    public static String getPackageName(Context c) {
        String packagePath = getPath() + SystemUtil.getPackageName(c) + "/";
        File file = new File(packagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return packagePath;
    }

    public static String getPath() {
        return PATH;
    }
}