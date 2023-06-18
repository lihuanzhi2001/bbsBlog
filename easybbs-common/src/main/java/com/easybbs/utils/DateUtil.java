package com.easybbs.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    private static final Object lockObj = new Object();
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }

        return tl.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    // 获取当前日期
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    // 获取7天前的时间
    public static String getPre7Day() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
        return sdf.format(calendar.getTime());

    }

    // 获取近一个月月的时间，并返回一个“yyyy-MM-dd”格式的数组
    public static List<String> getPre30DayList() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        String today = format.format(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        List<String> result = getBetweenDates(mon, today, false);

        return result;
    }

    // 获取近半个月的时间，并返回一个“yyyy-MM-dd”格式数组
    public static List<String> getPre15DayList() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        String today = format.format(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        List<String> result = getBetweenDates(mon, today, false);

        // 半个月
        int size = result.size();
        List<String> newResult = result.subList(size / 2, size);

        return newResult;
    }

    /**
     * 补全给定起止时间区间内的所有日期
     *
     * @param startTime
     * @param endTime
     * @param isIncludeStartTime
     * @return
     */
    private static List<String> getBetweenDates(String startTime, String endTime, boolean isIncludeStartTime) {
        List<String> result = new ArrayList();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);//定义结束日期  可以去当前月也可以手动写日期。
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            if (isIncludeStartTime) {
                result.add(format.format(d1));
            }
            while (dd.getTime().before(d2)) {//判断是否到结束日期
                dd.add(Calendar.DATE, 1);//进行当前日期加1
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format(dd.getTime());
                result.add(str);
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
