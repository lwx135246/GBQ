/**
 * MyPagerAdapter.java
 * cn.hupoguang.confessionswall.adapter
 * Function： TODO
 *
 * date ：   2013-7-24
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
 */

package cn.hupoguang.confessionswall.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.directionalviewpager.DirectionalViewPager;

/**
 * ClassName:MyPagerAdapter Function: TODO ADD FUNCTION
 * 
 * @author 李文响
 * @version 1.0
 * @Date 2013-7-24 上午9:45:06
 * 
 */

public class MyPagerAdapter extends PagerAdapter {

	public List<DirectionalViewPager> mListViews;

	public MyPagerAdapter(List<DirectionalViewPager> mListViews) {
		this.mListViews = mListViews;
	}

	public void addPager(DirectionalViewPager dv){
		mListViews.add(dv);
		notifyDataSetChanged();
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {

		((ViewPager) arg0).removeView(mListViews.get(arg1));

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#finishUpdate(android.view.View)
	 */
	@Override
	public void finishUpdate(View arg0) {

		// TODO Auto-generated method stub
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return mListViews.size();

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.View,
	 *      int)
	 */
	@Override
	public Object instantiateItem(View arg0, int arg1) {

		// TODO Auto-generated method stub
		((ViewPager) arg0).addView(mListViews.get(arg1), 0);
		return mListViews.get(arg1);

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
	 *      java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		// TODO Auto-generated method stub
		return arg0 == (arg1);

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#restoreState(android.os.Parcelable,
	 *      java.lang.ClassLoader)
	 */
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#saveState()
	 */
	@Override
	public Parcelable saveState() {

		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#startUpdate(android.view.View)
	 */
	@Override
	public void startUpdate(View arg0) {

	}

	
	 /**
     * 设置新闻的条数，在NewsDetailsContentFragment设置adpater后调用
     * @param list
     */
  /*  public void setNewsId(ArrayList<String> list) {
        idlList = list;
        positionValue = new boolean[idlList.size()];
        eachItemPicNum = new int[idlList.size()];
        for (int i = 0; i < positionValue.length; i++) {
            positionValue[i] = false;
            eachItemPicNum[i] = 0;
        }
        for (int i = 0; i < idlList.size(); i++) {
            views.add(setView());
        }
        shareStr = new String[views.size()];
        shareImg = new String[views.size()];
        notifyDataSetChanged();
    }*/
    
    
/*	public void setPrimaryItem(View container, int position, Object object) {
		// 把这个position赋值到一个全局变量，通过这个就会知道滑动到哪个页面了
		setPosition(position);
		// 保证每条新闻都只请求一次联网，执行过后就不在执行getNewsData方法了
		if (positionValue[position] == false) {
			// 这个方法就是联网获取数据
			getNewsData(categoryId, position);
				Log.v("guo", "联网请求............." + itemPosttion);
			positionValue[position] = true;
		}
	}*/

}
