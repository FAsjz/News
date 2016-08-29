package com.feicui.news.ui;


import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.MainActivity;



import com.feicui.news.NoNetConnectionActivity;
import com.feicui.news.R;
import com.feicui.news.UserActivity;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.LoginManager;
import com.feicui.news.model.dao.RegistManager;
import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.entity.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentLogin extends Fragment {
	private EditText name, pwd;
	private TextView forgetPwd;
	private Button regist, login;
    private String s1;
	private String s2;
	private ProgressDialog dialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, null);

		name = (EditText) view.findViewById(R.id.editText_nickname);
		pwd = (EditText) view.findViewById(R.id.editText_pwd);
		regist = (Button) view.findViewById(R.id.button_register);
		forgetPwd = (TextView) view.findViewById(R.id.textview_forgetPass);
		login = (Button) view.findViewById(R.id.button_login);
		
		
		
		regist.setOnClickListener(listener);
		forgetPwd.setOnClickListener(listener);
		login.setOnClickListener(listener);
		return view;
	}

	OnClickListener listener = new OnClickListener() {

		
		
		@Override
		public void onClick(View v) {
			
			s1 = name.getText().toString();
			s2 = pwd.getText().toString();
			switch (v.getId()) {
			case R.id.button_register:

				((MainActivity)getActivity()).showRegisterContent();
				break;
			case R.id.button_login:
				if (s1.length() == 0) {
					ToastUtil.ToastShow(getActivity(), "用户名不能为空");
					return;
				}
				if (s2.length() == 0) {
					ToastUtil.ToastShow(getActivity(), "密码不能为空");
					return;
				}
				if(s2.length() < 6 || s2.length() > 16 ){ 
					ToastUtil.ToastShow(getActivity(), "密码长度错误" );
					return ; 
					}
				if (SystemUtil.isNetConn(getActivity()) == true) {
					dialog = ProgressDialog.show(getActivity(), null, "正在登录...");
					LoginManager.loadLogin(getActivity(), loginlistener, errorListener, s1,s2);
				}else {
					startActivity(new Intent(getActivity(),NoNetConnectionActivity.class));
				}
//				Register register =SharedPreferencesUtil.getRegister(getActivity());
				break;
			case R.id.textview_forgetPass:

				((MainActivity)getActivity()).showForgetPassContent();
				break;

			}
		}
	};
	Listener<String> loginlistener = new Listener<String>(){

		@Override
		public void onResponse(String response) {
			
			LogUtil.d("Registerlogin111", response);
			BaseEntity<Register> baseEntity = (BaseEntity<Register>) GsonParse.getRegist(response);
			dialog.dismiss();
			if (baseEntity.getStatus() == -1) {
				ToastUtil.ToastShow(getActivity(), baseEntity.getMessage());
			}
			Register register = baseEntity.getData();
			if (register == null) {
				return;
			}
			LogUtil.d("Registerlogin22", register.toString());
			switch (register.getResult()) {
			case 0:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				Intent it = new Intent(getActivity(),UserActivity.class);
				it.putExtra("login", true);
				SharedPreferences shared = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor ed = shared.edit();
				ed.putString("username", s1);
				ed.putString("password", s2);
				ed.putString("token", register.getToken());
				ed.commit();
				
				getActivity().startActivity(it);
				getActivity().finish();   
				break;
			case -1:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;
			case -2:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;
			case -3:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;

			
			}
			
		}
		
	};
	ErrorListener errorListener = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(getActivity(), "网络连接异常");
			LogUtil.d("FragmentLogin", "网络连接异常");
			dialog.dismiss();
		}
		
	};
	
}
