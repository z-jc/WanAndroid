package com.dq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author : Z-JC
 * Date : 2020/1/3
 * Description :
 */
public class DateUtil {
    public static String y_m_d_h_m_s = "yyyy_MM_dd_HH_mm_ss";
    public static String ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static String hms = "HH:mm:ss";
    public static String hm = "HH:mm";
    public static String ymd = "yyyy年MM月dd日";

    /**
     * 获取当前时间
     *
     * @param type
     * @return
     */
    public static String getDate(String type, Long curren) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date curDate = new Date(curren);
        String str = formatter.format(curDate);
        return str;
    }

    //时间转换成时间戳
    public static Long getTime(String time){
        SimpleDateFormat format =new SimpleDateFormat(ymd);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取当前时间
     *
     * @param type
     * @return
     */
    public static String getDate(String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }


    /**
     * 获取当前时间毫秒值
     */
    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取今天
     *
     * @return String
     */
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获取昨天
     *
     * @return String
     */
    public static String getYestoday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 获取本月开始日期
     *
     * @return String
     **/
    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + "00:00:00";
    }

    /**
     * 获取本月最后一天
     *
     * @return String
     **/
    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取本周的第一天
     *
     * @return String
     **/
    public static String getWeekStart() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取本周的最后一天
     *
     * @return String
     **/
    public static String getWeekEnd(int index) {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int dayofweek = ca.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        ca.add(Calendar.DATE, 2 - dayofweek);


        Calendar cal = Calendar.getInstance();
        cal.setTime(ca.getTime());
        cal.add(Calendar.DAY_OF_WEEK, index);
        Date weekEndSta = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(weekEndSta);
    }

    /**
     * 获取本年的第一天
     *
     * @return String
     **/
    public static String getYearStart() {
        return new SimpleDateFormat("yyyy").format(new Date()) + "-01-01";
    }

    /**
     * 获取本年的最后一天
     *
     * @return String
     **/
    public static String getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currYearLast = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast) + " 23:59:59";
    }
}
