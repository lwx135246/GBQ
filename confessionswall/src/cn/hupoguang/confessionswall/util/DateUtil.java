/**
 * DateUtil.java
 * cn.hupoguang.confessionofwall.util
 * Function锟斤拷 锟斤拷锟斤拷锟斤拷锟斤拷
 *
 * date 锟斤拷   2013-7-10
 * author锟斤拷锟斤拷锟斤拷锟斤拷
 * Copyright (c) 2013, 锟斤拷锟斤拷锟�All Rights Reserved.
 */

package cn.hupoguang.confessionswall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * ClassName:DateUtil 
 * Function: 日期处理函数
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-10 上午11:16:16
 * 
 */

public class DateUtil {

	
	/**
	 * parse2Date:(将字符串转换成日期)
	 * @param src
	 * @param pattern
	 * @return
	 * @author   李文响
	 * @date 2013-7-16  下午4:13:35
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parse2Date(String src,String pattern) {
		Date date = null;
		try {
			SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(pattern);
			date = simpleDateFormat.parse(src);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	
	/**
	 * formate2Str:(将日期转换成字符串)
	 * @param date
	 * @param pattern
	 * @return
	 * @author   李文响
	 * @date 2013-7-16  下午4:13:58
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formate2Str(Date date,String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	
	public static String getDateString(Calendar c){
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year+"-"+(month > 10 ? month : "0"+month)+"-"+(day > 10 ? day : "0"+day);
	}
	
	/**
	 * getYear:(获取年份)
	 * @return
	 * @author   李文响
	 * @date 2013-7-17  下午2:23:04
	 */
	public static String getYear(Calendar calendar){
		return calendar.get(Calendar.YEAR)+"";
	}
	
	/**
	 * getMonth:(获取月份)
	 * @return
	 * @author   李文响
	 * @date 2013-7-17  下午2:23:20
	 */
	public static String getMonth( Calendar calendar){
		int month = calendar.get(Calendar.MONTH)+1;
		String result = "";
		switch (month) {
		case 1:
			result = "JAN";
			break;
		case 2:
			result = "FEB";
			break;
		case 3:
			result = "MAR";
			break;
		case 4:
			result = "APR";
			break;
		case 5:
			result = "MAY";
			break;
		case 6:
			result = "JUN";
			break;
		case 7:
			result = "JUL";
			break;
		case 8:
			result = "AUG";
			break;
		case 9:
			result = "SEP";
			break;
		case 10:
			result = "OCT";
			break;
		case 11:
			result = "NOV";
			break;
		case 12:
			result = "DEC";
			break;
		}
		
		return result;
	}
	
	/**
	 * getDay:(获取日)
	 * @return
	 * @author   李文响
	 * @date 2013-7-17  下午2:23:33
	 */
	public static String getDay(Calendar calendar){
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String result = day < 10 ? "0"+day : day+"";
		return result;
	}
	
	/**
	 * getWeek:(获取周)
	 * @return
	 * @author   李文响
	 * @date 2013-7-17  下午2:23:42
	 */
	public static String getWeek(Calendar calendar){
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		String result = "";
		
		switch(week){
		case 1:
			result = "Sunday";
			break;
		case 2:
			result = "Monday";
			break;
		case 3:
			result = "Tuesday";
			break;
		case 4:
			result = "Wednesday";
			break;
		case 5:
			result = "Thursday";
			break;
		case 6:
			result = "Friday";
			break;
		case 7:
			result = "Saturday";
			break;
		}
		return result;
	}
	

}
