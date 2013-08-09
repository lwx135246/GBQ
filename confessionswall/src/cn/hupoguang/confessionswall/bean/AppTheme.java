/**
 * AppTheme.java
 * cn.hupoguang.confessionofwall.bean
 * Function： 封装APP主题信息
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.bean;

import java.io.Serializable;

/**
 * ClassName:AppTheme
 * Function: 封装主题信息
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:21:33
 *
 */

public class AppTheme implements Serializable {

	private static final long serialVersionUID = -3573640588702159408L;
	/** 背景图片地址*/
	private String imagePath;
	/** 每日一句中文*/
	private String dailyContext;
	/** 每日一句英文*/
	private String dailyEngContext;
	/** 主题颜色*/
	private String color;
	/** 发布日期*/
	private String publishDate;
	
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDailyContext() {
		return dailyContext;
	}
	public void setDailyContext(String dailyContext) {
		this.dailyContext = dailyContext;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getDailyEngContext() {
		return dailyEngContext;
	}
	public void setDailyEngContext(String dailyEngContext) {
		this.dailyEngContext = dailyEngContext;
	}
	

}

