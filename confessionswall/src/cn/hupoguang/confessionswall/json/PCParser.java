/**
 * PCParser.java
 * cn.hupoguang.confessionofwall.json
 * Function： 解析发告白
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.json;

import org.json.JSONException;

/**
 * ClassName:PCParser
 * Function: 解析发告白返回信息
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:07:41
 *
 */

public class PCParser extends BaseParser<String> {

	@Override
	public String parseJSON(String paramString) throws JSONException {
		
		return checkResponse(paramString);
	}

}


