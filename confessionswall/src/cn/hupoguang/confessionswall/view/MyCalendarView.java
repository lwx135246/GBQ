package cn.hupoguang.confessionswall.view;

import java.util.Calendar;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.dialog.CalendarDialog;
import cn.hupoguang.confessionswall.util.AnimationUtil;
import cn.hupoguang.confessionswall.util.CalendarUtil;
import cn.hupoguang.confessionswall.util.ConfessionApplication;
import cn.hupoguang.confessionswall.util.DateUtil;

public class MyCalendarView extends ViewGroup implements View.OnClickListener {
	public View view;
	private Button[] buttons = new Button[35];
	public static Button btnYear;
	private Button btnBefore;  
	private Button btnNext; 
	private TextView textMonth;
	private String[] months = new String[12];
	private String[] yearItems = new String[80];// 添加80年的2013-1933
	private int[] ns = new int[35]; 

	public  int currYear, currMonth, currDay;// 当前选中的时间，非现时

	Time time = new Time("GMT+8");
	private Context context;
	private View parentView;
	private View nextView;
	private View[] childViews;
	private View touchView;

	public MyCalendarView(Context context, View touchView,View parentView, View nextView, View... childViews) {
		super(context);
		this.context = context;
		this.touchView = touchView;
		this.parentView = parentView;
		this.nextView = nextView;
		this.childViews = childViews;

		view = LayoutInflater.from(context).inflate(getResources().getLayout(R.layout.calendar), null);
		initView();
		updateCalendar();
	}

	/*
	 * public MyCalendarView(Context context, AttributeSet attrs) {
	 * 
	 * super(context, attrs); this.context = context; view =
	 * LayoutInflater.from(
	 * context).inflate(getResources().getLayout(R.layout.calendar), null);
	 * initView(); updateCalendar(); }
	 */

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
		textMonth = (TextView) view.findViewById(R.id.textMonth);
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/zaozigongfang.ttf");
		textMonth.setTypeface(typeface);
		textMonth.setText(months[time.month]);// 设置当前月份
		btnYear = (Button) view.findViewById(R.id.btnYear);
		currYear = time.year;
		btnYear.setText(currYear + "");
		btnYear.setOnClickListener(this);
		btnBefore = (Button) view.findViewById(R.id.btn_left);
		// btnBefore.setOnTouchListener(this);
		btnBefore.setOnClickListener(this);
		btnNext = (Button) view.findViewById(R.id.btn_right);
		btnNext.setOnClickListener(this);
		// btnNext.setOnTouchListener(this);
		initButton();// 初始化所有按钮
	}

	/*
	 * 初始化按钮
	 */
	void initButton() {
		// 初始化所有按钮
		int startButtonId = R.id.day11;
		for (int i = 0; i < 35; i++) {
			buttons[i] = (Button) view.findViewById(startButtonId++);
			// buttons[i].setBackgroundDrawable(background)
		}
	}

	private Builder builder = new CalendarDialog(this.getContext());

	@Override
	public void onClick(View v) {
		if (v.getId() == btnYear.getId()) {
			System.out.println("-------选中年--------");
			// 选择年
			Window win = builder.setItems(yearItems, new DialogInterface.OnClickListener() {
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
			currMonth = CalendarUtil.getMonthIndex(textMonth.getText().toString(), months);
			if (currMonth == 0) {
				return;
			}
			textMonth.setText(months[--currMonth]);

		} else if (v.getId() == btnNext.getId()) {
			System.out.println("-------选中下一个月--------");
			// 选择下一个月
			currMonth = CalendarUtil.getMonthIndex(textMonth.getText().toString(), months);
			if (currMonth == months.length - 1) {
				return;
			}
			textMonth.setText(months[++currMonth]);
		} else {
			// 单击日子
			System.out.println("-------选中日--------");
			updateCalendar();
			Button btn = (Button) v;
			btn.setBackgroundColor(Color.parseColor(ConfessionApplication.COLOR));
			btn.setTextColor(Color.WHITE);
			currDay = Integer.parseInt(btn.getText() + "");
			System.out.println(getDate());
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, currYear);
			calendar.set(Calendar.MONTH, currMonth);
			calendar.set(Calendar.DAY_OF_MONTH, currDay);
			System.out.println(DateUtil.getYear(calendar) + "/" + DateUtil.getMonth(calendar) + "/" + DateUtil.getDay(calendar) + "/" + DateUtil.getWeek(calendar));
			
//			((MyView) touchView).initDate(DateUtil.getYear(calendar), DateUtil.getMonth(calendar), DateUtil.getDay(calendar), DateUtil.getWeek(calendar));
			AnimationUtil.applyRotation(0, 90, parentView, nextView, false, childViews);
			return;
		}
		updateCalendar();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
	}

	/**
	 * 通过年月获取日历页面
	 * 
	 */
	public void updateCalendar() {
		currYear = Integer.parseInt(btnYear.getText().toString());
		System.out.println("YEAR:"+currYear);
		currMonth = CalendarUtil.getMonthIndex(textMonth.getText().toString(), months);
		ns = CalendarUtil.getDays(currYear, currMonth);
		int flag = -1;
		for (int i = 0; i < 35; i++) {
			buttons[i].setText(ns[i] + "");

			if (ns[i] > 1 && flag == -1) {
				buttons[i].setOnClickListener(null);
				buttons[i].setTextColor(Color.parseColor("#828282"));
				continue;
			} else if (ns[i] == 1 && i < 14) {
				flag = 0;
			} else if (ns[i] < 7 && i > 30) {
				flag = -1;
				buttons[i].setOnClickListener(null);
				buttons[i].setTextColor(Color.parseColor("#828282"));
				continue;
			}
			if (ns[i] >= 1 && flag == 0) {
				buttons[i].setBackgroundColor(Color.parseColor("#dcdcdc"));
				buttons[i].setOnClickListener(this);
				buttons[i].setTextColor(Color.parseColor("#2a2a2a"));
			}
			// 默认选中当前日期
			if (currYear == time.year && currMonth == time.month && (ns[i] + "").equals(time.monthDay + "")) {
				buttons[i].setBackgroundColor(Color.parseColor("#AECDE8"));
			}
		}
	}

	/*
	 * 得到时间
	 */
	String getDate() {
		String str = "";
		str = currYear + "/" + (currMonth + 1) + "/" + currDay;
		return str;
	}

	/*
	 * @Override public boolean onTouch(View v, MotionEvent event) {
	 * 
	 * if(event.getAction() == MotionEvent.ACTION_DOWN){ if (v == btnBefore) {
	 * btnBefore.setBackground(getResources().getDrawable(R.drawable.btn_l2));
	 * // 选择上一个月 currMonth =
	 * CalendarUtil.getMonthIndex(textMonth.getText().toString(), months); if
	 * (currMonth == 0) { return false; }
	 * textMonth.setText(months[--currMonth]);
	 * 
	 * } else if (v == btnNext) {
	 * btnNext.setBackground(getResources().getDrawable(R.drawable.btn_r2)); //
	 * 选择下一个月 currMonth =
	 * CalendarUtil.getMonthIndex(textMonth.getText().toString(), months); if
	 * (currMonth == months.length - 1) { return false; }
	 * textMonth.setText(months[++currMonth]); }
	 * 
	 * }else if(event.getAction() == MotionEvent.ACTION_UP){ if (v == btnBefore)
	 * { btnBefore.setBackground(getResources().getDrawable(R.drawable.btn_l));
	 * }else if (v == btnNext) {
	 * btnNext.setBackground(getResources().getDrawable(R.drawable.btn_r)); } }
	 * return false;
	 * 
	 * }
	 */

}
