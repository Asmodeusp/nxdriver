package com.saimawzc.freight.weight.utils.serves.floatview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.DriverMainActivity;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.lang.reflect.Field;


public class AssistTouchViewLayout extends LinearLayout {

	public Context context;

	//记录小悬浮窗的宽度
	public static int viewWidth;

	//记录小悬浮窗的高度
	public static int viewHeight;

	//记录系统状态栏的高度
	 private static int statusBarHeight;

	//用于更新小悬浮窗的位置
	private WindowManager windowManager;

	//小悬浮窗的参数
	private WindowManager.LayoutParams mParams;

	//记录当前手指位置在屏幕上的横坐标值
	private float xInScreen;

	//记录当前手指位置在屏幕上的纵坐标值
	private float yInScreen;

	//记录手指按下时在屏幕上的横坐标的值
	private float xDownInScreen;

	//记录手指按下时在屏幕上的纵坐标的值
	private float yDownInScreen;

	//记录手指按下时在小悬浮窗的View上的横坐标的值
	private float xInView;

	//记录手指按下时在小悬浮窗的View上的纵坐标的值
	private float yInView;

	public AssistTouchViewLayout(Context context) {
		super(context);
		this.context=context;
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_window_assist, this);
		View view = findViewById(R.id.small_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		TextView percentView = (TextView) findViewById(R.id.percent);
		percentView.setText(AssistMenuWindowManager.getUsedPercentValue(context));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			// 手指移动的时候更新小悬浮窗的位置
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
				//openBigWindow();
				Bundle bundle=new Bundle();
				//bundle.putString("from","mycar");

				UserInfoDto userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
				if(userInfoDto!=null){
					if(userInfoDto.getRole()==2){
						startUserActivity(context, MainActivity.class,bundle);
					} else if(userInfoDto.getRole()==3){
						startUserActivity(context, DriverMainActivity.class,bundle);
					}
				}else {
					startUserActivity(context, DriverMainActivity.class,bundle);
				}

			}
			break;
		default:
			break;
		}
		return true;
	}
	public  void startUserActivity(Context context , Class cls,Bundle bundle){
		Intent intent = new Intent();
		intent.setClass(context, cls);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
		context.startActivity(intent);
	}
	/**
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
	 * 
	 * @param params
	 *            小悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}

	/**
	 * 打开大悬浮窗，同时关闭小悬浮窗。
	 */
	private void openBigWindow() {
		AssistMenuWindowManager.createBigWindow(getContext());
		AssistMenuWindowManager.removeSmallWindow(getContext());
	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

}
