/**
 * DBManager.java
 * cn.hupoguang.confessionofwall.db
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/


package cn.hupoguang.confessionswall.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.hupoguang.confessionswall.bean.AppTheme;
import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.util.ConfessionApplication;

/**
 * ClassName:DBManager
 * Function: TODO ADD FUNCTION
 *
 * @author   李文响
 * @version  1.0
 * @Date	2013-7-11	下午4:10:46
 *
 */

public class DBManager {

	private DBHelper helper;  
    private SQLiteDatabase db;
    
    public DBManager(Context context) {  
        helper = new DBHelper(context);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);   
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里   
        db = helper.getWritableDatabase();  
    } 
    
    
	/**
	 * getAllPraises:(查询所有赞信息)
	 * @return List<Confession>
	 * @author   李文响
	 * @date 2013-7-11  下午3:47:33
	 */
	public List<Confession> getAllPraises(){
		Cursor c = db.rawQuery("SELECT * FROM "+ConfessionApplication.PRAISE_DB,null);
		Confession rc = null;
		List<Confession> list = new ArrayList<Confession>();
		while(c.moveToNext()){
			rc = new Confession();
			rc.setPublishUserName(c.getString(c.getColumnIndex("likeUserName")));//赞用户名
			rc.setConfessionId(c.getString(c.getColumnIndex("confessionId")));//被赞告白墙ID
			list.add(rc);
		}
		c.close();
		return list;
	}
	
	
	/**
	 * getPraiseById:(根据id查询赞信息)
	 * @param confessionId 被赞告白ID
	 * @return Confession
	 * @author   李文响
	 * @date 2013-7-11  下午4:03:11
	 */
	public Confession getPraiseById(String confessionId){
		Confession rc = null;
		Cursor c = db.rawQuery("SELECT * FROM "+ConfessionApplication.PRAISE_DB+" where confessionId = ?",new String[]{confessionId});
		if(c.moveToNext()){
			rc = new Confession();
			rc.setPublishUserName(c.getString(c.getColumnIndex("likeUserName")));//赞用户名
			rc.setConfessionId(c.getString(c.getColumnIndex("confessionId")));//被赞告白墙ID
		}
		c.close();
		return rc;
	}
	
	
    /**
     * insertPraise:(插入一条赞信息)
     * @param confession 
     * @author   李文响
     * @date 2013-7-11  下午4:28:23
     */
    public void insertPraise(Confession confession){
		 ContentValues cv = new ContentValues();  
	     cv.put("likeUserName", confession.getPublishUserName());  //赞用户名
	     cv.put("confessionId", confession.getConfessionId());  //被赞告白墙ID
	     //插入ContentValues中的数据   
	     try{
	    	 db.insert(ConfessionApplication.PRAISE_DB, null, cv);
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	}
    
    
    /**
     * insertThemes:(批量插入主题信息)
     * @param themes
     * @author   李文响
     * @date 2013-7-11  下午5:19:34
     */
    public void insertThemes(List<AppTheme> themes){
            db.beginTransaction();  //开始事务   
            try {  
                for (AppTheme theme : themes) {  
                    db.execSQL("INSERT INTO "+ConfessionApplication.THEMES_DB+" VALUES(?, ?, ?, ?,?)", new Object[]{theme.getImagePath(), theme.getDailyContext(),theme.getDailyEngContext(),theme.getPublishDate(),theme.getColor()});  
                }  
                db.setTransactionSuccessful();  //设置事务成功完成   
            } finally {  
                db.endTransaction();    //结束事务   
            }  
    }
    
    
    /**
     * insertTheme:(插入一个主题信息)
     * @param theme 主题
     * @author   李文响
     * @date 2013-7-17  下午3:52:29
     */
    public void insertTheme(AppTheme theme){
    	 ContentValues cv = new ContentValues();  
	     cv.put("imagePath", theme.getImagePath());//图片路径
	     cv.put("dailyContext", theme.getDailyContext());  //每日一句中文
	     cv.put("dailyEngContext", theme.getDailyEngContext());//每日一句英文
	     cv.put("publishDate", theme.getPublishDate());  //发布日期
	     cv.put("color", theme.getColor());  //主题颜色

	     //插入ContentValues中的数据   
	     try{
	    	 db.insert(ConfessionApplication.THEMES_DB, null, cv);
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
}
    
    
    /**
     * getThemes:(获取所有主题)
     * @return List<AppTheme>
     * @author   李文响
     * @date 2013-7-11  下午5:27:14
     */
    public List<AppTheme> getThemes(){
    	List<AppTheme> list = new ArrayList<AppTheme>();
    	AppTheme rc = null;
		Cursor c = db.rawQuery("SELECT * FROM "+ConfessionApplication.THEMES_DB,null);
		while(c.moveToNext()){
			rc = new AppTheme();
			rc.setImagePath(c.getString(c.getColumnIndex("imagePath")));
			rc.setDailyContext(c.getString(c.getColumnIndex("dailyContext")));
			rc.setDailyEngContext(c.getString(c.getColumnIndex("dailyEngContext")));
			rc.setPublishDate(c.getString(c.getColumnIndex("publishDate")));
			rc.setColor(c.getString(c.getColumnIndex("color")));
			list.add(rc);
		}
		c.close();
		return list;
    }
    
	/**
	 * getThemeByDate:(根据日期获得主题)
	 * @param publishDate 主题发布日期
	 * @return AppTheme
	 * @author   李文响
	 * @date 2013-7-17  下午3:25:30
	 */
	public AppTheme getThemeByDate(String publishDate){
		AppTheme theme = null;
		Cursor c = db.rawQuery("SELECT * FROM "+ConfessionApplication.THEMES_DB+" where publishDate = ?",new String[]{publishDate});
		if(c.moveToNext()){
			theme = new AppTheme();
			theme.setColor(c.getString(c.getColumnIndex("color")));
			theme.setDailyContext(c.getString(c.getColumnIndex("dailyContext")));
			theme.setDailyEngContext(c.getString(c.getColumnIndex("dailyEngContext")));
			theme.setImagePath(c.getString(c.getColumnIndex("imagePath")));
			theme.setPublishDate(c.getString(c.getColumnIndex("publishDate")));
		}
		c.close();
		return theme;
	}
	
	
	/**
	 * 删除本地消息
	 */
	public void delNativeMsg22(String id){
		try{
			db.delete(ConfessionApplication.PRAISE_DB, "_id=?",new String[]{id});
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
    /**
     * closeDB:(释放数据库资源，在整个应用关闭时执行)
     * @author   李文响
     * @date 2013-7-11  下午4:17:46
     */
    public void closeDB(){
		try {
			db.close();
		} catch (Exception e) {
			System.out.println("DB关闭异常");
		}
	}
}

