package com.feicui.news.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.EditText;

import com.feicui.news.model.entity.Register;

public class SharedPreferencesUtil {

	public static void saveRegister(Context context,Register register){
		SharedPreferences shared = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		Editor ed = shared.edit();
		ed.putString("explain", register.getExplain());
		ed.putString("token", register.getToken());
		ed.putInt("result", register.getResult());
		ed.commit();
	}
	public static void clearRegister(Context context){
		SharedPreferences shared = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		Editor ed = shared.edit();
		ed.clear();
		ed.commit();
	}
	public static Register getRegister(Context context){
		SharedPreferences shared = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		String explain = shared.getString("explain", "");
		String token = shared.getString("token", "");
		int result = shared.getInt("result", 0);
		Register register = new Register(result,token,explain);
		return register;
		
		
	}
	
}
