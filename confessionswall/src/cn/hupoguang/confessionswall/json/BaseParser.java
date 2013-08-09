/**
 * BaseParser.java
 * cn.hupoguang.confessionofwall.parser.json
 * Function： json解析
 *
 * date ：   2013-7-10
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * ClassName:BaseParser2
 * Function: json解析抽象基类
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-10	下午3:03:37
 *
 */

public abstract class BaseParser<T> {
	
    /**
     * parseJSON:(解析方法)
     * @param paramString 待解析的json字符串
     * @return T
     * @throws JSONException
     * @author   李文响
     * @date 2013-7-10  下午3:05:30
     */
    abstract T parseJSON(String paramString) throws JSONException;
    
	 /**
	 * checkResponse:(检测返回结果)
	 * @param paramString
	 * @return
	 * @throws JSONException
	 * @author   李文响
	 * @date 2013-7-10  下午3:35:28
	 */
	public String checkResponse(String paramString) throws JSONException{
		if(paramString==null){ 
			return null;
		}else{
			JSONObject jsonObject = new JSONObject(paramString); 
			String result = jsonObject.getString("r0");
			if(result!=null && !result.equals("")){
				Log.e("BaseParser", " statusCode == "+result);
				return result;
			}else{
				return null;
			}
		}
	 }

}

