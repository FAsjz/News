package com.feicui.news.common;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelePhonyManagerUtil {

	private static TelephonyManager tm;
	public static String getImei(Context context){
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
	} 
}
