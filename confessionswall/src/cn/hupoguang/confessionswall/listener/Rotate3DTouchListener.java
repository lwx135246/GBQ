package cn.hupoguang.confessionswall.listener;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hupoguang.confessionswall.R;
import cn.hupoguang.confessionswall.bean.Confession;
import cn.hupoguang.confessionswall.controller.ConfessionController;
import cn.hupoguang.confessionswall.util.AnimationUtil;
import cn.hupoguang.confessionswall.util.NetUtil;

public class Rotate3DTouchListener implements OnTouchListener {

	private View[] childViews; // Rotate的旋转页
	private View mainView;
	private int width;
	private int contentIndex = -1;// 第几条内容
	List<Confession> listConfession;

	private RelativeLayout mainDateLayout;// 色块的主页面
	private FrameLayout bottomLayout;
	private RelativeLayout gbLayout;
	private RelativeLayout gbNextLayout;
	private View middleSentence1;
	private View middleSentence2;
	/** to 某人 */
	private TextView toTv;
	/** 来自于某人 */
	private TextView fromTv;
	/** 告白内容 */
	private TextView gbTv;
	private TextView gbNextTv;

	String dateStr;
	private Context context;

	public Rotate3DTouchListener(Context context, View mainView, String Date,
			int width) {
		this.context = context;
		this.mainView = mainView;
		this.width = width;
		this.dateStr = Date;
		initView();
	}

	void initView() {
		middleSentence1 = mainView.findViewById(R.id.middle_sentence_1);
		middleSentence2 = mainView.findViewById(R.id.middle_setence_2);
		toTv = (TextView) mainView.findViewById(R.id.to_tv);
		fromTv = (TextView) mainView.findViewById(R.id.from_tv);
		mainDateLayout = (RelativeLayout) mainView
				.findViewById(R.id.main_date_layout);
		/* 说明只保存动画绘图缓存 */
		mainDateLayout
				.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);

		bottomLayout = (FrameLayout) mainView.findViewById(R.id.bottom_layout);
		gbLayout = (RelativeLayout) mainView.findViewById(R.id.gb_layout);
		gbTv = (TextView) mainView.findViewById(R.id.gb_tv);
		gbNextTv = (TextView) mainView.findViewById(R.id.gb_next_tv);
		gbNextLayout = (RelativeLayout) mainView
				.findViewById(R.id.gb_next_layout);
		childViews = new View[] { mainDateLayout, gbLayout, gbNextLayout };
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (!NetUtil.hashNetWork(context)) {
			return false;
		}
		float x = event.getX();
		boolean flag = x < width / 2; // 判断左右,true为左
		switch (view.getId()) {
		case R.id.left_anrrow:

			listConfession = getConfessionsByDate(dateStr, 1);
			if (listConfession.size() < 1) {
				// Toast.makeText(context, "亲,今天没有人告白",
				// Toast.LENGTH_SHORT).show();
				return false;
			}
			updateConfession(true, gbLayout);
			middleSentence1.setVisibility(View.GONE);
			middleSentence2.setVisibility(View.VISIBLE);
			AnimationUtil.applyRotation(0, 90, bottomLayout, gbLayout, false,
					childViews);
			break;
		case R.id.gb_layout:
			if (!updateConfession(!flag, gbNextLayout)) {
				return false;
			}
			if (flag) {
				AnimationUtil.applyRotation(0, -90, bottomLayout, gbNextLayout,
						flag, childViews);
			} else {
				AnimationUtil.applyRotation(0, 90, bottomLayout, gbNextLayout,
						flag, childViews);
			}
			break;
		case R.id.gb_next_layout:
			if (!updateConfession(!flag, gbLayout)) {
				return false;
			}
			if (flag) { // 点左半边向左转
				AnimationUtil.applyRotation(0, -90, bottomLayout, gbLayout,
						flag, childViews);
			} else {
				AnimationUtil.applyRotation(0, 90, bottomLayout, gbLayout,
						flag, childViews);
			}
			break;
		}
		return false;
	}

	List<Confession> getConfessionsByDate(String currDate, int page) {
		Map<String, Object> map;
		ConfessionController controller = new ConfessionController();
		map = controller.viewConfessions(currDate, 1);
		List<Confession> listConfession = null;
		if (null == map.get("r4")) {
			return null;
		}
		listConfession = (List<Confession>) map.get("r4");
		return listConfession;
	}

	/**
	 * updateConfession:(获取告白) flag:true 下一个,false上一个
	 * 
	 * @author 李文响
	 * @date 2013-7-18 下午3:32:12 return boolean true没有超出集合,false超出
	 */
	public boolean updateConfession(boolean flag, View view) {
		if (flag) {
			contentIndex++;
		} else {
			contentIndex--;
		}
		if (contentIndex < 0 || contentIndex > listConfession.size() - 1) {
			middleSentence1.setVisibility(View.VISIBLE);
			middleSentence2.setVisibility(View.GONE);
			AnimationUtil.applyRotation(0, -90, bottomLayout, mainDateLayout,
					flag, childViews);
			contentIndex = -1;
			return false;
		}
		Confession co = listConfession.get(contentIndex);
		toTv.setText(co.getPublishUserName());
		fromTv.setText(co.getReceiveUserName());
		if (view.getId() == gbLayout.getId()) {
			gbTv.setText(co.getConfessionContent());
		} else {
			gbNextTv.setText(co.getConfessionContent());
		}
		return true;
	}
}
