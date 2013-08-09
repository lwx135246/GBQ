package cn.hupoguang.confessionswall.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.controller.ConfessionController;
import cn.hupoguang.confessionswall.listener.ToViewpagerGestureListener;
import cn.hupoguang.confessionswall.util.DateUtil;
import cn.hupoguang.confessionswall.util.ToastUtil;

import com.umeng.analytics.MobclickAgent;

public class PublishActivtiy extends Activity implements OnTouchListener {
	/************************** 发布页面 ************************************/
	/** 发送图标 */
	private ImageView sendImg;
	
	/** 发送图标 */
	private ImageView back;
	
	
	/** to某人 */
	private EditText toEdit;
	/** from某人 */
	private EditText fromEdit;
	/** 发送的内容 */
	private EditText contentEdit;
	/** 剩余字数 */
	private TextView number;
	/** 总字数 */
	private ConfessionController controller;
	private ToastUtil tostUtil;
	private GestureDetector detector;
	ToastUtil toastUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_confession);
		detector = new GestureDetector(this, new ToViewpagerGestureListener(this));//
		initView();
		toastUtil = new ToastUtil(this);
	}

	@Override
	protected void onResume() {
		InputMethodManager m = (InputMethodManager) toEdit.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		super.onResume();
		MobclickAgent.onResume(this);
	}

	/*
	 * 加载视图
	 */
	private void initView() {
		sendImg = (ImageView) findViewById(R.id.send_img);
		back = (ImageView) findViewById(R.id.imgBackToMain);
		toEdit = (EditText) findViewById(R.id.to_et);
		fromEdit = (EditText) findViewById(R.id.from_et);
		contentEdit = (EditText) findViewById(R.id.content_et);
		number = (TextView) findViewById(R.id.leave_number);
		controller = new ConfessionController();
		tostUtil = new ToastUtil(this);
		sendImg.setOnTouchListener(this);
		back.setOnTouchListener(this);
		contentEdit.addTextChangedListener(tw);
	}

	private TextWatcher tw = new TextWatcher() {

		private CharSequence temp;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			temp = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			number.setText(140-temp.length() + "");
		}

	};

	
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v == back){
			back.setImageResource(R.drawable.backtomain2);
			this.finish();
			overridePendingTransition(R.anim.push_up_in,
					R.anim.push_up_out);
			return true;
		}else if(v == sendImg){
			Confession conffession = new Confession();
			String from = fromEdit.getText().toString();
			String to = toEdit.getText().toString();
			String content = contentEdit.getText().toString();
			if (isEmpty(to, "To不能为空!")) {
				return false;
			}
			if (isEmpty(from, "From不能为空!")) {
				return false;
			}
			if (isEmpty(content, "内容不能为空!")) {
				return false;
			}
			sendImg.setImageResource(R.drawable.fasong2);
			conffession.setPublishUserName(from);
			conffession.setReceiveUserName(to);
			conffession.setConfessionContent(content);
			conffession
					.setConfessionTime(DateUtil.formate2Str(new Date(), "HH:mm"));
			String result = controller.publishConfession(conffession);
			
			if (null == result) {
				tostUtil.showToast("发送告白失败！", Toast.LENGTH_LONG);
			} else {
				tostUtil.showToast("发送告白成功！", Toast.LENGTH_LONG);
				this.finish();
				overridePendingTransition(R.anim.push_up_in,
						R.anim.push_up_out);
			}
		}else{
			System.out.println("hao");
			 InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  
             imm.hideSoftInputFromWindow(fromEdit.getWindowToken(), 0);
		}
		
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	private boolean isEmpty(String text, String name) {
		if (text.equals("") || text.length() == 0) {
			toastUtil.showToast(name, Toast.LENGTH_SHORT);
			return true;
		}
		return false;
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
