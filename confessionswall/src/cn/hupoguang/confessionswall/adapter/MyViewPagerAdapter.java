/**
 * MyViewPagerAdapter.java
 * cn.hupoguang.confessionofwall.adapter
 * Function： viewPager 适配器
 *
 * date ：   2013-7-15
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
 */

package cn.hupoguang.confessionswall.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ClassName:MyViewPagerAdapter Function: 适配器
 * 
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-15 下午2:31:15
 * 
 */

public class MyViewPagerAdapter extends PagerAdapter {

	private List<View> mListViews;

	public MyViewPagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	@Override
	public void destroyItem(View view, int index, Object obj) {
//		((ViewPager) view).removeView(mListViews.get(index%mListViews.size()));
	}

	@Override
	public void finishUpdate(View view) {

	}

	@Override
	public int getCount() {
		return null == mListViews ? 0 : Integer.MAX_VALUE;
	}

	@Override
	public Object instantiateItem(View view, int index) {
		 try{   
			 ((ViewPager) view).addView(mListViews.get(index%mListViews.size()));
             }catch (Exception e) {   
                 // TODO: handle exception   
             }  
		return mListViews.get(index%mListViews.size());
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

}
