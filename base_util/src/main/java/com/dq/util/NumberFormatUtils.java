package com.dq.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatUtils {

    public static String format(String string) {
        string = string.replaceAll(",", "");
        NumberFormat nf = NumberFormat.getNumberInstance();
        double d;
        String result;
        if (string.contains(" ")) {
            String s1 = string.substring(0, string.lastIndexOf(" ") + 1);
            String s2 = string.substring(string.lastIndexOf(" ") + 1, string.length());
            if (s2.contains(".")) {
                String s3 = s2.substring(0, s2.indexOf("."));
                String s4 = s2.substring(s2.indexOf("."), s2.length());
                if ("".equals(s3)) {
                    result = s1 + s2;
                }else {
                    result = s1 + nf.format(Double.valueOf(s3)) + s4;
                }
            } else if ("".equals(s2)) {
                result = s1;
            } else {
                d = Double.valueOf(s2);
                result = s1 + nf.format(d);
            }
        } else if (string.contains(".")) {
            String s1 = string.substring(0, string.indexOf("."));
            String s2 = string.substring(string.indexOf("."), string.length());
            d = Double.valueOf(s1);
            result = nf.format(d) + s2;
        } else {
            d = Double.valueOf(string);
            result = nf.format(d);
        }

        return result;
    }

    public static String format2(String string) {
        string = string.replaceAll(",", "");
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#,###.#########");
        double d = Double.valueOf(string);

        return df.format(d);
    }


    /**
     * 保留几位有效数字
     *
     * @param oldDouble 要处理的数字
     * @param scale     要保留的位数
     * @return 传回数字
     */
    public static double significand(double oldDouble, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "scale指定的精度为非负值");
        }
        /**
         * RoundingMode：舍入模式
         * UP：远离零方向舍入的舍入模式；
         * DOWN：向零方向舍入的舍入模式；
         * CEILING： 向正无限大方向舍入的舍入模式；
         * FLOOR：向负无限大方向舍入的舍入模式；
         * HALF_DOWN：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入；
         * HALF_UP：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入；
         * HALF_EVEN：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入;(在重复进行一系列计算时,此舍入模式可以将累加错误减到最小)
         * UNNECESSARY：用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入。
         */
        RoundingMode rMode;
        //rMode=RoundingMode.FLOOR;
        //下面这种情况，其实和FLOOR一样的。
        if (oldDouble > 0) {
            rMode = RoundingMode.DOWN;
        } else {
            rMode = RoundingMode.UP;
        }
        //此处的scale表示的是，几位有效位数
        BigDecimal b = new BigDecimal(Double.toString(oldDouble), new MathContext(scale, rMode));
        return b.doubleValue();
    }

}
