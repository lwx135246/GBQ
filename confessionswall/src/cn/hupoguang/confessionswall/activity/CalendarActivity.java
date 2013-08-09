package cn.hupoguang.confessionswall.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.util.CalendarUtil;

public class CalendarActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private Button[] buttons = new Button[35];
	public static Button btnYear;
	private Button btnBefore;
	private Button btnNext;
	private TextView textMonth;
	private LinearLayout otherView;
	private String[] months = new String[12];
	private String[] yearItems = new String[1];// 添加2013
	private int[] ns = new int[35];

	public int currYear, currMonth, currDay;// 当前选中的时间，非现时

	Time time = new Time("GMT+8");
	private Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
		setContentView(R.layout.calendar);
		builder = new Builder(this);
		initView();
		updateCalendar();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == otherView) {
			finish();
		}

		return false;
	}

	/**
	 * 初始化控件变量
	 */
	public void initView() {
		time.setToNow();// 获得当前时间
		int month_1 = R.string.month_1;
		for (int i = 0; i < months.length; i++) {
			months[i] = getResources().getString(month_1++);
		}// 初始化月份
		for (int i = 0; i < yearItems.length; i++) {
			yearItems[i] = time.year - i + "";
		}// 初始化年份
		textMonth = (TextView) findViewById(R.id.textMonth);
		Typeface typeface = Typeface.createFromAsset(this.getAssets(),
				"font/zaozigongfang.ttf");
		textMonth.setTypeface(typeface);
		textMonth.setText(months[time.month]);// 设置当前月份
		btnYear = (Button) findViewById(R.id.btnYear);
		currYear = time.year;
		btnYear.setText(currYear + "");
		btnYear.setOnClickListener(this);
		btnBefore = (Button) findViewById(R.id.btn_left);
		// btnBefore.setOnTouchListener(this);
		btnBefore.setOnClickListener(this);
		btnNext = (Button) findViewById(R.id.btn_right);
		btnNext.setOnClickListener(this);
		// btnNext.setOnTouchListener(this);
		otherView = (LinearLayout) findViewById(R.id.other);
		otherView.setOnTouchListener(this);
		initButton();// 初始化所有按钮
	}

	/*
	 * 初始化按钮
	 */
	void initButton() {
		// 初始化所有按钮
		int startButtonId = R.id.day11;
		for (int i = 0; i < 35; i++) {
			buttons[i] = (Button) findViewById(startButtonId++);
		}
	}

	/**
	 * 通过年月获取日历页面
	 * 
	 */
	public void updateCalendar() {
		currYear = Integer.parseInt(btnYear.getText().toString());
		System.out.println("YEAR:" + currYear);
		currMonth = CalendarUtil.getMonthIndex(textMonth.getText().toString(),
				months);
		ns = CalendarUtil.getDays(currYear, currMonth);
		int flag = -1;
		for (int i = 0; i < 35; i++) {
			buttons[i].setText(ns[i] + "");

			if (ns[i] > 1 && flag == -1) {
				buttons[i].setOnClickListener(null);
				buttons[i].setTextColor(Color.parseColor("#828282"));
				buttons[i].setBackgroundColor(Color.parseColor("#F5F5F5"));
				continue;
			} else if (ns[i] == 1 && i < 14) {
				flag = 0;
			} else if (ns[i] < 7 && i > 30) {
				flag = -1;
				buttons[i].setOnClickListener(null);
				buttons[i].setTextColor(Color.parseColor("#828282"));
				buttons[i].setBackgroundColor(Color.parseColor("#F5F5F5"));
				continue;
			}
			if (ns[i] >= 1 && flag == 0) {
				buttons[i].setBackgroundColor(Color.parseColor("#F5F5F5"));
				buttons[i].setOnClickListener(this);
				buttons[i].setTextColor(Color.parseColor("#2a2a2a"));
			}
			// 默认选中当前日期
			if (currYear == time.year && currMonth == time.month
					&& (ns[i] + "").equals(time.monthDay + "")) {
				buttons[i].setBackgroundResource(R.drawable.btndate);
			}
		}
	}

	/*
	 * 决定所选的日子是否可以跳转
	 */
	boolean isJump() {
		Intent intent = new Intent();
		intent.putExtra("diffDay", getDiffDay());
		setResult(ViewPagerActivity.CALENDAR_OK, intent);

		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.set(currYear, currMonth, currDay);
		System.out.println(tempCalendar.get(Calendar.MONTH));
		Calendar tempCalendar2 = Calendar.getInstance();
		tempCalendar.set(2013, 7, 1);  //从2013年8月1号开始
		if (tempCalendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance()
				.get(Calendar.DAY_OF_YEAR)
				&& tempCalendar.get(Calendar.YEAR) == Calendar.getInstance()
						.get(Calendar.YEAR)) {
//			finish();
		}  else if(tempCalendar.after(tempCalendar2)){
			Toast.makeText(this, "亲, 请从2013/8/1开始", Toast.LENGTH_SHORT).show();
		} else{
			Toast.makeText(this, "有效", Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	/*
	 * 得到之间的天数
	 */
	int getDiffDay() {
		String str = "";
		str = currYear + "/" + (currMonth + 1) + "/" + currDay;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		int diffDay = 0;
		try {
			Date date1 = ft.parse(str);
			long days = Calendar.getInstance().getTime().getTime()
					- date1.getTime();
			diffDay = (int) (days / 1000 / 60 / 60 / 24);
			System.out.println(diffDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diffDay;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnYear.getId()) {
			System.out.println("-------选中年--------");
			// 选择年
			Window win = builder
					.setItems(yearItems, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							btnYear.setText(yearItems[which]);
							updateCalendar();
						}
					}).show().getWindow();

			WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
			p.width = 250;
			win.setAttributes(p);
		} else if (v.getId() == btnBefore.getId()) {
			System.out.println("-------选中上一个月--------");
			// 选择上一个月
			currMonth = CalendarUtil.getMonthIndex(textMonth.getText()
					.toString(), months);
			if (currMonth == 0) {
				return;
			}
			textMonth.setText(months[--currMonth]);

		} else if (v.getId() == btnNext.getId()) {
			System.out.println("-------选中下一个月--------");
			// 选择下一个月
			currMonth = CalendarUtil.getMonthIndex(textMonth.getText()
					.toString(), months);
			if (currMonth == months.length - 1) {
				return;
			}
			textMonth.setText(months[++currMonth]);
		} else {
			// 单击日子
			System.out.println("-------选中日--------");
			updateCalendar();
			Button btn = (Button) v;
			btn.setBackgroundResource(R.drawable.btndate);
			btn.setTextColor(Color.WHITE);
			currDay = Integer.parseInt(btn.getText() + "");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, currYear);
			calendar.set(Calendar.MONTH, currMonth);
			calendar.set(Calendar.DAY_OF_MONTH, currDay);
			isJump(); // 是否跳转
			return;
		}
		updateCalendar();
	}

}
