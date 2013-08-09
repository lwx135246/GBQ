package cn.hupoguang.confessionswall.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.adapter.MyViewPagerAdapter;
import cn.hupoguang.confessionswall.db.DBManager;
import cn.hupoguang.confessionswall.listener.ToPublishGestureListener;
import cn.hupoguang.confessionswall.service.NetService;
import cn.hupoguang.confessionswall.thread.LoadThemeToViewThread;

import com.umeng.analytics.MobclickAgent;

public class ViewPagerActivity extends Activity implements
		OnPageChangeListener, OnTouchListener {
	public static final int CALENDAR_OK=0;
	private int PAGE = 0;
	private List<View> list = new ArrayList<View>();
	Calendar today = Calendar.getInstance();
	Calendar currDate = Calendar.getInstance();
	private ViewPager viewPager;
	DBManager dbManager;
	int beforePage = PAGE;
	int currPage = PAGE;
	private GestureDetector detector;
	
	
	/* 网络状态监听 */
	Intent service=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.viewpager);
		getDays();
		Calendar tempCal = Calendar.getInstance();
		tempCal.set(Calendar.DAY_OF_MONTH, 1);
		dbManager = new DBManager(this);
		for (int i = 0; i < 3; i++) {
			list.add(loadBaseView(tempCal));
			tempCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		detector = new GestureDetector(this, new ToPublishGestureListener(this));//
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new MyViewPagerAdapter(list));
		viewPager.setOnTouchListener(this);
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(PAGE);// 显示今天的view

		LoadThemeToViewThread loadThemeToViewThread = new LoadThemeToViewThread(
				new View[] { list.get(PAGE % 3) }, currDate, dbManager,
				cacheViewHandle);
		loadThemeToViewThread.context=this;
		cacheViewHandle.post(loadThemeToViewThread);
		loadThemeToViewThread = new LoadThemeToViewThread(views, currDate,
				dbManager, cacheViewHandle);
		cacheViewHandle.post(loadThemeToViewThread);
		/* 网络状态监听 */
		service = new Intent(this, NetService.class);
		startService(service);
	}

	/*
	 * 将统一的数据界面初始化
	 */
	View loadBaseView(Calendar calendar) {
		LayoutInflater inflater = getLayoutInflater();
		View mainView = inflater.inflate(R.layout.main_face, null);
		TextView year = (TextView) mainView.findViewById(R.id.year);
		TextView month = (TextView) mainView.findViewById(R.id.month);
		TextView day = (TextView) mainView.findViewById(R.id.day);
		TextView week = (TextView) mainView.findViewById(R.id.week);
		TextView dateHelp = (TextView) mainView.findViewById(R.id.dateHelp);
		dateHelp.setText(calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 2) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH));
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"font/yuehei.otf");
		month.setTypeface(typeface);
		week.setTypeface(typeface);
		year.setTypeface(typeface);
		typeface = Typeface.createFromAsset(getAssets(), "font/bt.TTF");
		day.setTypeface(typeface);

		ImageView leftAnrrow = (ImageView) mainView
				.findViewById(R.id.left_anrrow);
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.anim_yuan);
		leftAnrrow.setAnimation(animation);
		return mainView;
	}

	void getDays() {
		Calendar start = Calendar.getInstance();
		start.set(2013, 7, 1);
		PAGE = (int) ((Calendar.getInstance().getTime().getTime() - start
				.getTime().getTime()) / (1000 * 60 * 60 * 24));
		beforePage = PAGE;
		currPage = PAGE;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CALENDAR_OK){
			int diffDay=data.getIntExtra("diffDay", 0);
			viewPager.setCurrentItem(PAGE-diffDay);
		}
	}
	
	long previousDate;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = new Date().getTime();
			long result = currentTime-previousDate;
			if(result > 0 && result <= 1000){
				this.finish();
				stopService(service);
			}else{
				Toast.makeText(this, "再按一次将退出告白墙", Toast.LENGTH_SHORT).show();
			}
			previousDate = currentTime;
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	private View[] views;
	private View currView;
	private Handler cacheViewHandle = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				viewPager.setCurrentItem(PAGE);
			} else if (msg.what == 1) {
				Intent intent = new Intent(ViewPagerActivity.this,
						AboutActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.left_in, R.anim.left_out);
			}
			super.handleMessage(msg);
		}

	};

	/**
	 * 0表示闲,1表示滑,2表示加载完成
	 */
	@Override
	public void onPageScrollStateChanged(int index) {
		if (index == 0) {
			if(currPage == 0){
				Calendar tempCurrDate=Calendar.getInstance();
				tempCurrDate.set(2013, 7,1);
				LoadThemeToViewThread loadThemeToViewThread = new LoadThemeToViewThread(
						new View[] { currView }, tempCurrDate, dbManager,
						cacheViewHandle);
				loadThemeToViewThread.context=this;
				cacheViewHandle.post(loadThemeToViewThread);
				return ;}
			LoadThemeToViewThread loadThemeToViewThread = new LoadThemeToViewThread(
					new View[] { currView }, currDate, dbManager,
					cacheViewHandle);
			loadThemeToViewThread.context=this;
			cacheViewHandle.post(loadThemeToViewThread);
			
			loadThemeToViewThread = new LoadThemeToViewThread(views, currDate,
					dbManager, cacheViewHandle);
			cacheViewHandle.post(loadThemeToViewThread);
		}
	}

	@Override
	public void onPageScrolled(int index, float arg1, int arg2) {
		
	}

	/* 不允许在主线程之外加载View,可以用Handle */

	@Override
	public void onPageSelected(int index) {
		currPage = index;
		currView = list.get(index % list.size());
		if (index < 1) {
			return;
		}
		
		if (index > PAGE) {
			viewPager.setCurrentItem(index - 1);
			return;
		}
		/* 每个View的主题加载 */
		
		View beforeSelectView = list.get((index - 1) % list.size());
		View afterSelectView = list.get((index + 1) % list.size());
		views = new View[] { beforeSelectView, afterSelectView };
		currDate.add(Calendar.DAY_OF_YEAR, index - beforePage);
		beforePage = index;
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
