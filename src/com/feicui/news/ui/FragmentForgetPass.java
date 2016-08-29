package com.feicui.news.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.MainActivity;
import com.feicui.news.NoNetConnectionActivity;
import com.feicui.news.R;
import com.feicui.news.UserActivity;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.TextUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.ForgetPassManager;
import com.feicui.news.model.dao.RegistManager;
import com.feicui.news.model.entity.ForgetPass;
import com.feicui.news.model.entity.Register;

public class FragmentForgetPass extends Fragment {
	private EditText et;
	private Button bt;
	private ProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_forgetpass, null);
		et = (EditText) view.findViewById(R.id.edit_email);
		bt = (Button) view.findViewById(R.id.btn_commit);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s = et.getText().toString();
				if (s == null || s == "") {
					ToastUtil.ToastShow(getActivity(), "邮箱不能为空");
				}
				if (SystemUtil.isNetConn(getActivity()) == true) {
					dialog = ProgressDialog.show(getActivity(), null, "正在获取数据...");
					ForgetPassManager.loadForgetPass(getActivity(), getPasslistener, errorListener, s);
				}else {
					startActivity(new Intent(getActivity(),NoNetConnectionActivity.class));
				}
				
				
			}
		});
		
		return view;
	}
	Listener<String> getPasslistener = new Listener<String>(){

		@Override
		public void onResponse(String response) {
			ForgetPass forgetPass = (ForgetPass) GsonParse.getForgetPass(response);
			if (forgetPass == null) {
				return;
			}
//			LogUtil.d("forgetpass", forgetPass.toString());
			dialog.dismiss();
			switch (forgetPass.getResult()) {
			case 0:
				ToastUtil.ToastShow(getActivity(), forgetPass.getExplain());
				((MainActivity) getActivity()).showLoginContent();
				break;
			case -1:
				ToastUtil.ToastShow(getActivity(), forgetPass.getExplain());
				break;
			case -2:
				ToastUtil.ToastShow(getActivity(), forgetPass.getExplain());
				break;
			
			}
			
		}
		
	};
	ErrorListener errorListener = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(getActivity(), "网络连接异常");
			LogUtil.d("FragmentForgetPass", "网络连接异常");
			dialog.dismiss();
		}
		
	};
}
