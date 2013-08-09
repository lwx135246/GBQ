package cn.hupoguang.confessionswall.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import cn.hupoguang.confessionswall.animation.Rotate3dAnimation;

public class AnimationUtil {

	public static void applyRotation(float start, float end, final View parentView, final View nextView,final boolean flag,final View ... childView ) {
		final float centerX = parentView.getWidth() / 2.0f;
		final float centerY = parentView.getHeight() / 2.0f/2;
		Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX, centerY, 460.0f, true);
		rotation.setDuration(200);
		rotation.setInterpolator(new LinearInterpolator());//new AccelerateInterpolator()
		rotation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				parentView.post(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < childView.length; i++) {
							childView[i].setVisibility(View.GONE);
						}
						nextView.setVisibility(View.VISIBLE);
						nextView.requestFocus();
						Rotate3dAnimation rotatiomAnimation = null;
						if (flag) {
							rotatiomAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, 460.0f, false);
						} else {
							rotatiomAnimation = new Rotate3dAnimation(-90, 0, centerX, centerY, 460.0f, false);
						}

						rotatiomAnimation.setDuration(200);
						rotatiomAnimation.setInterpolator(new LinearInterpolator());//new DecelerateInterpolator()
						parentView.startAnimation(rotatiomAnimation);
					}
				});
			} 

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationStart(Animation arg0) {
			}
		});
		parentView.startAnimation(rotation);
	}
}
