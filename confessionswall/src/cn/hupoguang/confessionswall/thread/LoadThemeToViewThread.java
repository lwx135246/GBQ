package cn.hupoguang.confessionswall.thread;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.bean.AppTheme;
import cn.hupoguang.confessionswall.controller.ConfessionController;
import cn.hupoguang.confessionswall.db.DBManager;
import cn.hupoguang.confessionswall.listener.Rotate3DTouchListener;
import cn.hupoguang.confessionswall.listener.ToCalendarClickListener;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.DateUtil;
import cn.hupoguang.confessionswall.util.ImageUtil;
import cn.hupoguang.confessionswall.util.ThreadPoolManager;

/**
 * 缓冲View
 * 
 * @author wang
 * 
 */
public class LoadThemeToViewThread implements Runnable {
	private View[] selectViews;
	private Calendar calendar;
//	private static final int LOAD_COUNT = 3;// 缓冲3个页面

	/* 控件 */
	private TextView year;
	private TextView month;
	private TextView day;
	private TextView week;
	RelativeLayout themeLayout;
	/* 告白内容 */
	private ImageView leftAnrrow;
	private RelativeLayout mainDateLayout;// 色块的主页面
	private RelativeLayout gbLayout;
	private RelativeLayout gbNextLayout;
	RelativeLayout contentLayout;
	RelativeLayout visableView;
	
	ImageView imgJumpDate;
	TextView sentence;
	TextView englishSentence;
	TextView helpDate;
	
	private View middleSentence1;//每日一句话的条幅
	private View middleSentence2;//告白者的条幅
	ImageView imgHome;
	private DBManager dbManager;
	Handler cacheViewHandle;
	/* 旋转 */
	Rotate3DTouchListener rotateListener=null;

	public Context context;
	
	public LoadThemeToViewThread(View[] selectView, Calendar calendar,
			DBManager dbManager,Handler cacheViewHandle) {
		this.selectViews = selectView;
		this.calendar = (Calendar) calendar.clone();
		this.dbManager = dbManager;
		this.cacheViewHandle=cacheViewHandle;
	}

	/*
	 * 实例化组件
	 */
	void initView(View selectView) {
		year = (TextView) selectView.findViewById(R.id.year);
		month = (TextView) selectView.findViewById(R.id.month);
		day = (TextView) selectView.findViewById(R.id.day);
		week = (TextView) selectView.findViewById(R.id.week);
		helpDate = (TextView) selectView.findViewById(R.id.dateHelp);
		imgHome = (ImageView) selectView.findViewById(R.id.imgHome);
		themeLayout = (RelativeLayout) selectView
				.findViewById(R.id.theme_layout);
		contentLayout = (RelativeLayout) selectView
				.findViewById(R.id.content_layout);
		leftAnrrow = (ImageView) selectView.findViewById(R.id.left_anrrow);
		mainDateLayout = (RelativeLayout) selectView
				.findViewById(R.id.main_date_layout);
		gbLayout = (RelativeLayout) selectView.findViewById(R.id.gb_layout);
		gbNextLayout = (RelativeLayout) selectView
				.findViewById(R.id.gb_next_layout);
		imgJumpDate=(ImageView) selectView.findViewById(R.id.calader);
		sentence = (TextView) selectView.findViewById(R.id.sentence_tv);
		middleSentence1 = selectView.findViewById(R.id.middle_sentence_1);
		middleSentence2 = selectView.findViewById(R.id.middle_setence_2);
		englishSentence = (TextView) selectView
				.findViewById(R.id.english_sentence);
		/* 最后一页 隐藏 */
		visableView = (RelativeLayout) selectView.findViewById(R.id.nextView);
	}

	@Override
	public void run() {
		System.out.println("------开启缓冲线程-------");
		
		for (int i = 0; i < selectViews.length; i++) {
			initView(selectViews[i]);// 实例化
			rotateListener=new Rotate3DTouchListener(context,selectViews[i], DateUtil.formate2Str(calendar.getTime(), "yyyy-MM-dd"), 240);
			if (selectViews.length == 1) {
				setCalendar(calendar); // 缓冲日期
				loadTextTheme(calendar);
				leftAnrrow.setOnTouchListener(rotateListener);
				gbLayout.setOnTouchListener(rotateListener);
				gbNextLayout.setOnTouchListener(rotateListener);
				return;
			}
			if (i == 0) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			} else {
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}
			setCalendar(calendar); // 缓冲日期
			leftAnrrow.setOnTouchListener(rotateListener);
			gbLayout.setOnTouchListener(rotateListener);
			gbNextLayout.setOnTouchListener(rotateListener);
			themeLayout.setBackgroundResource(R.drawable.defalutimage);
		}

	}

	/**
	 * 设置每一个View的日期
	 * 
	 * @param mainView
	 * @param calendar
	 */
	void setCalendar(Calendar calendar) {
		year.setText(DateUtil.getYear(calendar));
		month.setText(DateUtil.getMonth(calendar));
		day.setText(DateUtil.getDay(calendar));
		week.setText(DateUtil.getWeek(calendar));

		// home与About键切换
		if (calendar.after(Calendar.getInstance())) {
			visableView.setVisibility(View.GONE);
		} else {
			visableView.setVisibility(View.VISIBLE);
		}
		if (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(
				Calendar.DAY_OF_YEAR)
				&& calendar.get(Calendar.YEAR) == Calendar.getInstance().get(
						Calendar.YEAR)) {
			leftAnrrow.setAlpha(255); //右下角的小点
			imgHome.setImageResource(R.drawable.about);
			imgHome.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					Message message=new Message();
					message.what=1;
					cacheViewHandle.sendMessage(message);
					return false;
				}
			});
		} else {
			leftAnrrow.setAlpha(0);//右下角的小点隐藏
			imgHome.setImageResource(R.drawable.home_icon);
			imgHome.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					Message message=new Message();
					message.what=0;
					cacheViewHandle.sendMessage(message);
					return false;
				}
			});
		}
		setJumpDate();
		reRotateStart();
	}
	
	/*
	 * 恢复成旋转前的日期主页
	 */
	void reRotateStart(){
		middleSentence1.setVisibility(View.VISIBLE);
		middleSentence2.setVisibility(View.GONE);
		mainDateLayout.setVisibility(View.VISIBLE);
		gbLayout.setVisibility(View.GONE);
		gbNextLayout.setVisibility(View.GONE);
	}
	
	/*
	 * 日历按键
	 */
	public void setJumpDate(){
		ToCalendarClickListener toCalendarClickListener=new ToCalendarClickListener(context);
		
		imgJumpDate.setOnClickListener(toCalendarClickListener);
	}

	/**
	 * setTheme:(设置主题)
	 * 
	 * @param drawable
	 * @param chineseText
	 *            一句话中文
	 * @param englishText
	 *            一句话英文
	 * @author 李文响
	 * @date 2013-7-17 下午4:54:28
	 */
	public void setThemeText(AppTheme theme) {
		sentence.setText(theme == null ? "" : theme.getDailyContext());
		englishSentence
				.setText(theme == null ? "" : theme.getDailyEngContext());
		contentLayout.setBackgroundColor(Color.parseColor(theme.getColor()));
		gbLayout.setBackgroundColor(Color.parseColor(theme.getColor()));
		gbNextLayout.setBackgroundColor(Color.parseColor(theme.getColor()));
	}

	/**
	 * setTheme:(设置主题背景)
	 * 
	 * @param drawable
	 * @param chineseText
	 *            一句话中文
	 * @param englishText
	 *            一句话英文
	 * @author 李文响
	 * @date 2013-7-17 下午4:54:28
	 */
	public void setThemeImg(View mainView, Drawable drawable) {

	}

	/**
	 * 从本地数据库读取数据
	 */
	void loadTextTheme(Calendar calendar) {
		String date = DateUtil.formate2Str(calendar.getTime(), "yyyy-MM-dd");
		AppTheme theme = dbManager.getThemeByDate(date);
		BitmapDrawable bd = null;

		if (theme != null) {
			Bitmap bitMap = ImageUtil
					.getImageFromLocal(ConfessionApplication.THEME_PATH
							+ theme.getImagePath());
			if (bitMap != null) {
				bd = new BitmapDrawable(bitMap);
			} else {
				 GetImageFromWebCallable getimageCallable = new
				 GetImageFromWebCallable(
				 ConfessionApplication.IMAG_URL + theme.getImagePath());
				 Future<Object> future = ThreadPoolManager.getInstance()
				 .addCall(getimageCallable);
				 try {
				 bd = (BitmapDrawable) future.get();
				 } catch (InterruptedException e) {
				 e.printStackTrace();
				 } catch (ExecutionException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }
				
				 bd = ImageUtil
				 .loadImageFromServer(ConfessionApplication.IMAG_URL
				 + theme.getImagePath());
			}
		} else {
			ConfessionController controller = new ConfessionController();
			// 本地没有主题, 从服务端获得主题
			Map<String, Object> themeMap = controller.getAppTheme(date);
			if (null != themeMap) {
				theme = ((List<AppTheme>) themeMap.get("r3")).get(0);
				theme.setPublishDate(date);
				// 更新主题信息
				// 将新获得的主题存入本地数据库
				dbManager.insertTheme(theme);
				 bd = ImageUtil
				 .loadImageFromServer(ConfessionApplication.IMAG_URL
				 + theme.getImagePath());
			}
		}
		setThemeText(theme);
		if (bd != null) {
			themeLayout.setBackgroundDrawable(bd);
		}else{
			System.out.println("没有图片");
		}
	}
	

}
