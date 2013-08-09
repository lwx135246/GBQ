package cn.hupoguang.confessionswall.util;

import java.util.Calendar;

/**
 * 小日历的排序
 * 
 * @author WangChangwei
 * 
 */
public class CalendarUtil {
	/*
	 * 根据文字获取当前月份的序号
	 */
	public static int getMonthIndex(String monthText, String[] months) {
		for (int i = 0; i < months.length; i++) {
			if (months[i].equals(monthText)) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * 得到日历序列
	 */
	public static int[] getDays(int year, int month) {
		int[] numbers = new int[35];
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);
		if (c.get(Calendar.DAY_OF_WEEK) > 1) {
			c.add(Calendar.DAY_OF_MONTH, -(c.get(Calendar.DAY_OF_WEEK) - 2));
		}
		for (int i = 0; i < 35; i++) {
			numbers[i] = c.get(Calendar.DAY_OF_MONTH);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return numbers;
	}
}
