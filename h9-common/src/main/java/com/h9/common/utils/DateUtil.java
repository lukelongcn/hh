package com.h9.common.utils;



import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 * @author shadow.liu
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @date 2016年6月24日 下午9:55:41
 */
public class DateUtil {

    public enum FormatType {
        GBK_SECOND("yyyy年MM月dd日 HH时mm分ss秒"),
        GBK_MINUTE("yyyy年MM月dd日 HH时mm分"),
        GBK_HOUR("yyyy年MM月dd日 HH时"),
        GBK_DAY("yyyy年MM月dd日"),
        GBK_MONTH("yyyy年MM月"),
        GBK_YEAR("yyyy年"),
        NON_SEPARATOR_DAY("yyyyMMdd"),
        SECOND("yyyy-MM-dd HH:mm:ss"),
        SECOND2("yyyyMMddHHmmss"),
        MINUTE("yyyy-MM-dd HH:mm"),
        HOUR("yyyy-MM-dd HH"),
        DAY("yyyy-MM-dd"),
        MONTH("yyyy-MM"),
        YEAR("yyyy");


        private String format;

        private FormatType(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }


    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param date
     * @param formatType
     * @return
     */
    public static final String formatDate(Date date, FormatType formatType) {
        if (date == null)
            return StringUtils.EMPTY;
        return new SimpleDateFormat(formatType.format).format(date);

    }


    public static String getSpaceTime(Date begin, Date end) {
        long between = 0;
        try {
            // 得到两者的毫秒数
            between = (end.getTime() - begin.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);

        StringBuffer result = new StringBuffer();

        if (min <= 60 && hour == 0 && day == 0) {
            result.append(min);
            result.append("分钟前");
            return result.toString();
        }
        if (day == 0 && hour > 0) {
            result.append(hour);
            result.append("小时前");
            return result.toString();
        }
        if (begin.before(getCurrentYearStartTime())) {
            return DateUtil.formatDate(begin, FormatType.MINUTE);
        }
        LocalDate localDate = DateUtil.trans(begin);
        return localDate.format(DateTimeFormatter.ofPattern("MM-dd"));

    }

    public static LocalDate trans(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }


    public static Date formatDate(String date,FormatType formatType){
        if(StringUtils.isNotBlank(date)) {
            try {
                return new SimpleDateFormat(formatType.format).parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static boolean isValidDate(String str,FormatType formatType) {
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat(formatType.format);
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }


    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date date, String formatStr) {
        if (date == null)
            return StringUtils.EMPTY;
        return new SimpleDateFormat(formatStr).format(date);

    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分ss秒'格式字串
     *
     * @param dateStr
     * @return
     */
    public static final Date parser(String dateStr, String formatter) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final long getInterval(Date begin, Date end) {
        return end.getTime() - begin.getTime();
    }


    /**
     * 实现日期加一天或一月的方法
     *
     * @param date   开始时间
     * @param number 增加几天/几月
     *               cd.add(Calendar.MONTH, number);//增加一个月
     *               cd.add(Calendar.DATE, number);//增加一天
     * @return
     */
    public static Date getDate(Date date, int number, int filed) {
        try {
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            cd.add(filed, number);
            return cd.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得当前时间
     *
     * @return 当前日期（Date）
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 取得昨天此时的时间
     *
     * @return 昨天日期（Date）
     */
    public static Date getYesterdayDate() {
        return new Date(getCurTimeMillis() - 0x5265c00L);
    }

    /**
     * 取得去过i天的时间
     *
     * @param i 过去时间天数
     * @return 昨天日期（Date）
     */
    public static Date getPastdayDate(int i) {
        return new Date(getCurTimeMillis() - 0x5265c00L * i);
    }

    /**
     * 取得当前时间的长整型表示
     *
     * @return 当前时间（long）
     */
    public static long getCurTimeMillis() {
        return System.currentTimeMillis();
    }



    // 获得昨天0点时间
    public static Date getYesterdaymorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesMorning().getTime() - 3600 * 24 * 1000);
        return cal.getTime();
    }

    // 获得当天近7天时间
    public static Date getWeekFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesMorning().getTime() - 3600 * 24 * 1000 * 7);
        return cal.getTime();
    }

    // 获得当天0点时间
    public static Date getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /***
     * 获得当天24点时间
      */

    public static Date getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得当天0点时间
    public static Date getTimesMorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    // 获得当天24点时间
    public static Date getTimesNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    public static Date getLastMonthStartMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMonthmorning());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取最近几个月的开始时间
     *
     * @param month
     * @return
     */
    public static Date getLastSomeMonthStartMorning(int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMonthmorning());
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }


    public static Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }

    public static Date getCurrentYearEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public static Date getLastYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }

//    public static void main(String[] args) {
//		String dateStr = "2015-1-1";
//		String dateStr2 = "2015-1-2";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date date2 = format.parse(dateStr2);
//			Date date = format.parse(dateStr);
//
//			System.out.println("两个日期的差距：" + differentDays(date2, date));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println("当天24点时间：" + getTimesNight().toLocaleString());
//		System.out.println("当前时间：" + new Date().toLocaleString());
//		System.out.println("当天0点时间：" + getTimesMorning().toLocaleString());
//		System.out.println("昨天0点时间：" + getYesterdaymorning().toLocaleString());
//		System.out.println("近7天时间：" + getWeekFromNow().toLocaleString());
//		System.out.println("本周周一0点时间：" + getTimesWeekmorning().toLocaleString());
//		System.out.println("本周周日24点时间：" + getTimesWeeknight().toLocaleString());
//		System.out.println("本月初0点时间：" + getTimesMonthmorning().toLocaleString());
//		System.out.println("本月未24点时间：" + getTimesMonthnight().toLocaleString());
//		System.out.println("上月初0点时间：" + getLastMonthStartMorning().toLocaleString());
//		System.out.println("本季度开始点时间：" + getCurrentQuarterStartTime().toLocaleString());
//		System.out.println("本季度结束点时间：" + getCurrentQuarterEndTime().toLocaleString());
//		System.out.println("本年开始点时间：" + getCurrentYearStartTime().toLocaleString());
//		System.out.println("本年结束点时间：" + getCurrentYearEndTime().toLocaleString());
//		System.out.println("上年开始点时间：" + getLastYearStartTime().toLocaleString());
//		System.out.println("某月开始点时间：" + getTimesMonthmorning(parser("2016-02-05 15:00:45", "yyyy-MM-dd HH:mm:ss")).toLocaleString());
//		System.out.println("某月未24点时间：" + getTimesMonthnight(parser("2016-02-05 15:00:45", "yyyy-MM-dd HH:mm:ss")).toLocaleString());
//
//        String endDate = getEndDate(new Date(), parser("2016-09-06 18:55:55", "yyyy-MM-dd HH:mm:ss"));
//        System.out.println("endDate = [" + endDate + "]");
//    }


    public static String getEndDate(Date begin, Date end) {
        long between = 0;
        try {
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
				- min * 60 * 1000 - s * 1000);

        StringBuffer result = new StringBuffer();
        if (day != 0) {
            result.append(day);
            result.append("天");
        }
        if (hour != 0) {
            result.append(hour);
            result.append("小时");
        }
        if (min != 0) {
            result.append(min);
            result.append("分");
        }
		if (s != 0) {
			result.append(s);
			result.append("s");
		}
		if (ms != 0) {
			result.append(ms);
			result.append("毫秒");
		}

        return result.toString();
    }

    @SuppressWarnings("Duplicates")
    public static Date getDayOfMax(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    public static Date getDayOfMix(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);

        return calendar.getTime();
    }

    /*****
     * 获取时间的某一个部分 i
     * @param date
     * @param type 要获取的类型 @see #AM_PM #HOUR_OF_DAY
     * @return
     */
    public static int get(Date date,int type){
        Calendar calendar = Calendar.getInstance();
        if(type == Calendar.DAY_OF_WEEK){
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
        }
        calendar.setTime(date);
        return calendar.get(type);
    }
    /**
    *   @Author:George
    *   @Description:
    *   @Date:2017/11/7 20:45
    *	@param date
    *	@param days 天数，正为加，负为减
    *   @return java.util.Date
    */
    public static Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

}
