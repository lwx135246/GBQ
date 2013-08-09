/**
 * PublishConfession.java
 * cn.hupoguang.confessionofwall.bean
 * Function： 封装告白数据
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.bean;

import java.io.Serializable;

/**
 * ClassName:PublishConfession
 * Function: 封装告白信息
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:08:49
 *
 */

public class Confession implements Serializable {

	private static final long serialVersionUID = 706882188531226497L;
	
	/** 告白Id*/
	private String confessionId;
	/** 发布人名称*/
	private String publishUserName;
	/** 接收人名称*/
	private String receiveUserName;
	/** 告白内容*/
	private String confessionContent;
	/** 告白日期*/
	private String confessionDate;
	/** 告白时间*/
	private String confessionTime;
	/** 被赞次数*/
	private String praiseCount;
	
	
	public String getConfessionId() {
		return confessionId;
	}
	public void setConfessionId(String confessionId) {
		this.confessionId = confessionId;
	}
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getReceiveUserName() {
		return receiveUserName;
	}
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	public String getConfessionContent() {
		return confessionContent;
	}
	public void setConfessionContent(String confessionContent) {
		this.confessionContent = confessionContent;
	}
	
	public String getConfessionDate() {
		return confessionDate;
	}
	public void setConfessionDate(String confessionDate) {
		this.confessionDate = confessionDate;
	}
	public String getConfessionTime() {
		return confessionTime;
	}
	public void setConfessionTime(String confessionTime) {
		this.confessionTime = confessionTime;
	}
	public String getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	
	
}

