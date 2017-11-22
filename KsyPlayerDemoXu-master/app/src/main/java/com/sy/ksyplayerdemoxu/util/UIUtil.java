package com.sy.ksyplayerdemoxu.util;
import android.util.Log;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: yt.com.kmbikecycling.utils.UIUtil.java
 * @author: xutailian
 * @date: 2016-10-14 11:54
 * @describe
 */
public class UIUtil {
    /**
     * 字符串转换到时间格式
     *
     * @param dateStr
     *            需要转换的字符串
     * @param formatStr
     *            需要格式的目标字符串 举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException
     *             转换异常
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        //yyyy-MM-dd HH:mm:ss

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }
    /**
     * 把时间转换成指定格式
     *
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getMyTime(String format, String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format((time));
        return str;
    }

    /**
     * 转换时间格式
     * @param timeMs
     * @return
     */
    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00:00";
        }
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d",minutes, seconds).toString();
        }
    }
    /**
     * 转换时间格式(分钟，秒，毫秒)
     * @param timeMs
     * @return
     */
    public static String stringForTimeTwo(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00:00";
        }
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        long diff =timeMs%1000;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d:%02d",minutes, seconds,diff).toString();
        }
    }
    /**
     * 转换时间格式(分钟，秒，毫秒)
     * @param timeMs
     * @return
     */
    public static String stringForTimeThree(double timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00:00";
        }
        int totalSeconds =(int) (timeMs / 1000);
        int seconds = (int)(totalSeconds % 60);
        int minutes =(int) ((totalSeconds / 60) % 60);
        int hours = (int)(totalSeconds / 3600);
        double diff =timeMs%1000;
        Log.e("时间--","使用时间="+hours+":"+minutes+":"+diff);
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d:%02d",hours,minutes, seconds).toString();
        }
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 转换时间格式
     * @param
     * @return
     */
    public static long  fromDateStringToLong(String inVal) { //此方法计算时间毫秒
        Date date = null;   //定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = inputFormat.parse(inVal); //将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();   //返回毫秒数
    }
    public static  String dqsj() {  //此方法用于获得当前系统时间（格式类型2007-11-6 15:10:58）
        Date date = new Date();  //实例化日期类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
//        String today = date.toLocaleString(); //获取当前时间
//        System.out.println("获得当前系统时间 "+today);  //显示
        return str;  //返回当前时间
    }
//    String dqsj = dqsj();   //获得String dqsj = dqsj();   //获得当前系统时间
//    mss df = new mss();  //实例化方法
//
//    long startT=df.fromDateStringToLong("2005-03-03 14:51:23"); //定义上机时间
//    long endT=df.fromDateStringToLong("2004-03-03 13:50:23");  //定义下机时间
//
//    long ss=(startT-endT)/(1000); //共计秒数
//    int MM = (int)ss/60;   //共计分钟数
//    int hh=(int)ss/3600;  //共计小时数
//    int dd=(int)hh/24;   //共计天数
//
//    System.out.println("共"+dd+"天 准确时间是："+hh+" 小时 "+MM+" 分钟"+ss+" 秒 共计："+ss*1000+" 毫秒");
    /**
     * b保留两位小数（返回字符串）
     * @param f1
     * @return
     */
    public static  String reserveDecimals(double f1){
        DecimalFormat df = new DecimalFormat("0.00");
//        return String.valueOf(df.format((f1 * 100)));
        return String.valueOf(df.format((f1)));
    }


    public static  String CurrentDay() {  //此方法用于获得当前系统时间（格式类型2007-11-6 15:10:58）
        Date date = new Date();  //实例化日期类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
//        String today = date.toLocaleString(); //获取当前时间
//        System.out.println("获得当前系统时间 "+today);  //显示
        return str;  //返回当前时间
    }
    /**
     * 拼接显示时间格式（）
     * @param startime
     * @param endtime
     * @return
     */
    public static String SplitDataString(String startime,String endtime){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = sdf.parse(startime);
            dateEnd = sdf.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(dateStart);
        int yearStart = calendar2.get(Calendar.YEAR);
        int monthStart = calendar2.get(Calendar.MONTH)+1;
        int DayStart = calendar2.get(Calendar.DATE);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(dateEnd);
        int yearEnd = calendar3.get(Calendar.YEAR);
        int monthEnd = calendar3.get(Calendar.MONTH)+1;
        int DayEnd = calendar3.get(Calendar.DATE);
        String newTime = ""+monthStart+"/"+DayStart+"-"+monthEnd+"/"+DayEnd;
        return newTime;
    }
    public  static String StringToTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        int hour = calendar2.get(Calendar.HOUR_OF_DAY);
        int min = calendar2.get(Calendar.MINUTE);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH) + 1;
        int Day = calendar2.get(Calendar.DATE);
        StringBuilder mFormatBuilder = new StringBuilder();
         Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
         String timeMin= mFormatter.format("%02d",min).toString();
//        String timeDay= mFormatter.format("%02d",Day).toString();
//        String timeMonth= mFormatter.format("%02d",month).toString();
//        String timeHour= mFormatter.format("%02d",hour).toString();
        String timeTo =""+year+"-"+month+"-"+Day+" "+hour+":"+timeMin;
        return timeTo;
    }
    public static String StringTwo(int time){
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        String timeMin= mFormatter.format("%02d",time).toString();
        return  timeMin;
    }

    public static String DateToString(Date time){
        Calendar calStart=Calendar.getInstance();
        calStart.setTime(time);
        int month = calStart.get(Calendar.MONTH)+1;
        String dateTime = ""+calStart.get(Calendar.YEAR)+"-"+month+"-"
                +calStart.get(Calendar.DAY_OF_MONTH)+" "+calStart.get(Calendar.HOUR_OF_DAY)+":"+calStart.get(Calendar.MINUTE);
        return dateTime;
    }
}
